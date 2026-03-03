package paas.load;

import paas.kafka.JsonUtils;
import paas.kafka.KafkaConsumerAdapter;
import paas.kafka.KafkaTopics;
import paas.kafka.TenantLoadReport;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 租户负载消费者，订阅 paas-tenant-load 主题，
 * 缓存各租户最新的负载上报数据。
 *
 * Tenant load consumer subscribing to the paas-tenant-load topic,
 * caching the latest load report for each tenant.
 */
public class TenantLoadConsumer extends KafkaConsumerAdapter {

    /**
     * 租户 ID → 最新负载报告 的内存缓存。
     * In-memory cache of tenant ID to latest load report.
     */
    private final Map<String, TenantLoadReport> latestLoadReports = new HashMap<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_TENANT_LOAD;
    }

    /**
     * 解析 TenantLoadReport 并更新缓存，同时调用持久化存根。
     * Parses TenantLoadReport, updates cache, and invokes the persistence stub.
     */
    @Override
    protected void processMessage(String key, String payload) {
        TenantLoadReport report = parseReport(payload);
        if (report == null || report.getTenantId() == null) {
            System.err.println("[TenantLoadConsumer] Invalid load report, skipping");
            return;
        }
        latestLoadReports.put(report.getTenantId(), report);
        persistLoadMetric(report);
        System.out.println("[TenantLoadConsumer] Updated load for tenant=" + report.getTenantId()
                + " score=" + report.getCalculatedLoadScore());
    }

    /**
     * 获取指定租户的综合负载评分，无数据时返回 0.0。
     * Returns the overall load score for a tenant; returns 0.0 if no data exists.
     *
     * @param tenantId 租户 ID / tenant ID
     * @return 负载评分 / load score
     */
    public double getLoadScore(String tenantId) {
        TenantLoadReport report = latestLoadReports.get(tenantId);
        return report == null ? 0.0 : report.getCalculatedLoadScore();
    }

    /**
     * 获取指定租户的最新负载报告，无数据时返回 null。
     * Returns the latest load report for a tenant, or null if none exists.
     *
     * @param tenantId 租户 ID / tenant ID
     * @return 负载报告 / load report
     */
    public TenantLoadReport getLatestReport(String tenantId) {
        return latestLoadReports.get(tenantId);
    }

    /**
     * 返回所有负载报告缓存的不可变视图。
     * Returns an unmodifiable view of all cached load reports.
     *
     * @return 负载报告缓存 / load report cache
     */
    public Map<String, TenantLoadReport> getAllReports() {
        return Collections.unmodifiableMap(latestLoadReports);
    }

    /**
     * 持久化负载指标（存根实现，仅打印到控制台）。
     * Persists load metrics (stub implementation: prints to console only).
     *
     * @param report 待持久化的负载报告 / load report to persist
     */
    public void persistLoadMetric(TenantLoadReport report) {
        System.out.println("[TenantLoadConsumer] persistLoadMetric: tenantId=" + report.getTenantId()
                + ", loadScore=" + report.getCalculatedLoadScore()
                + ", timestamp=" + report.getTimestamp());
    }

    // -------------------------------------------------------------------------
    // Private helpers / 私有辅助方法
    // -------------------------------------------------------------------------

    /**
     * 从 JSON 字符串解析 TenantLoadReport（简单字段解析，不含嵌套列表）。
     * Parses a TenantLoadReport from a JSON string (simple fields only, no nested lists).
     */
    private TenantLoadReport parseReport(String json) {
        if (json == null || json.isEmpty()) return null;
        TenantLoadReport report = new TenantLoadReport();
        report.setTenantId(JsonUtils.extractField(json, "tenantId", "tenant_id"));
        report.setCalculatedLoadScore(
                JsonUtils.extractDouble(json, "calculatedLoadScore", "calculated_load_score", 0.0));
        report.setTimestamp(JsonUtils.extractLong(json, "timestamp", "timestamp", 0L));
        return report;
    }
}
