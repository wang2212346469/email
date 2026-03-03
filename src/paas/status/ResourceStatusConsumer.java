package paas.status;

import paas.kafka.HealthStatus;
import paas.kafka.JsonUtils;
import paas.kafka.KafkaConsumerAdapter;
import paas.kafka.KafkaTopics;
import paas.kafka.ResourceStatusEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 资源健康状态消费者，订阅 paas-resource-status 主题，
 * 维护各 GPU 服务器的最新健康状态。
 *
 * Resource health status consumer subscribing to the paas-resource-status topic,
 * maintaining the latest health status for each GPU server.
 */
public class ResourceStatusConsumer extends KafkaConsumerAdapter {

    /**
     * 服务器 ID → 健康状态 的内存映射。
     * In-memory map of server ID to health status.
     */
    private final Map<String, HealthStatus> resourceHealthMap = new HashMap<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_STATUS;
    }

    /**
     * 解析 ResourceStatusEvent 并更新内存健康状态表。
     * Parses a ResourceStatusEvent and updates the in-memory health status map.
     */
    @Override
    protected void processMessage(String key, String payload) {
        ResourceStatusEvent event = parseEvent(payload);
        if (event == null || event.getServerId() == null) {
            System.err.println("[ResourceStatusConsumer] Invalid status event, skipping");
            return;
        }
        HealthStatus health = HealthStatus.fromPaasStatus(event.getStatus());
        resourceHealthMap.put(event.getServerId(), health);
        System.out.println("[ResourceStatusConsumer] Updated " + event.getServerId() + " -> " + health);
    }

    /**
     * 获取指定服务器的当前健康状态。未知服务器返回 UNKNOWN。
     * Gets the current health status of the specified server. Returns UNKNOWN for unknown servers.
     *
     * @param serverId 服务器 ID / server ID
     * @return 健康状态 / health status
     */
    public HealthStatus getHealthStatus(String serverId) {
        return resourceHealthMap.getOrDefault(serverId, HealthStatus.UNKNOWN);
    }

    /**
     * 判断指定服务器是否可参与调度（HEALTHY 或 DEGRADED 状态）。
     * Determines whether a server is schedulable (HEALTHY or DEGRADED status).
     *
     * @param serverId 服务器 ID / server ID
     * @return 是否可调度 / whether schedulable
     */
    public boolean isSchedulable(String serverId) {
        HealthStatus status = getHealthStatus(serverId);
        return status == HealthStatus.HEALTHY || status == HealthStatus.DEGRADED;
    }

    /**
     * 返回所有健康状态映射的不可变视图。
     * Returns an unmodifiable view of all health status mappings.
     *
     * @return 健康状态映射 / health status map
     */
    public Map<String, HealthStatus> getResourceHealthMap() {
        return Collections.unmodifiableMap(resourceHealthMap);
    }

    // -------------------------------------------------------------------------
    // Private helpers / 私有辅助方法
    // -------------------------------------------------------------------------

    /**
     * 从 JSON 字符串解析 ResourceStatusEvent。
     * Parses a ResourceStatusEvent from a JSON string.
     */
    private ResourceStatusEvent parseEvent(String json) {
        if (json == null || json.isEmpty()) return null;
        String serverId  = JsonUtils.extractField(json, "serverId",  "server_id");
        String status    = JsonUtils.extractField(json, "status",    "status");
        long timestamp   = JsonUtils.extractLong(json, "timestamp", "timestamp", 0L);
        return new ResourceStatusEvent(serverId, status, timestamp);
    }
}
