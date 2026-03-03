package paas.schedule;

import paas.kafka.JsonUtils;
import paas.kafka.KafkaConsumerAdapter;
import paas.kafka.KafkaTopics;
import paas.kafka.ReserveResponse;
import paas.kafka.ReserveResponse.ServerReserveResult;
import paas.kafka.ReserveResultStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预留响应消费者，订阅 paas-reserve-response 主题，
 * 缓存各任务的预留操作结果，供调度器异步查询。
 *
 * Reserve response consumer subscribing to the paas-reserve-response topic,
 * caching reserve operation results per task for asynchronous query by the scheduler.
 */
public class ReserveResponseConsumer extends KafkaConsumerAdapter {

    /**
     * 任务 ID → 预留响应 的内存缓存。
     * In-memory cache of task ID to reserve response.
     */
    private final Map<String, ReserveResponse> reserveResults = new HashMap<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESERVE_RESPONSE;
    }

    /**
     * 解析 ReserveResponse 并存入缓存。
     * Parses a ReserveResponse and stores it in the cache.
     */
    @Override
    protected void processMessage(String key, String payload) {
        ReserveResponse response = parseResponse(payload);
        if (response == null || response.getTaskId() == null) {
            System.err.println("[ReserveResponseConsumer] Invalid response, skipping");
            return;
        }
        reserveResults.put(response.getTaskId(), response);
        System.out.println("[ReserveResponseConsumer] Stored result for taskId=" + response.getTaskId());
    }

    /**
     * 获取指定任务 ID 的预留操作结果，未收到响应时返回 null。
     * Gets the reserve operation result for a task ID, returns null if not yet received.
     *
     * @param taskId 任务 ID / task ID
     * @return 预留响应，或 null / reserve response, or null
     */
    public ReserveResponse getResult(String taskId) {
        return reserveResults.get(taskId);
    }

    /**
     * 返回所有预留结果缓存的不可变视图。
     * Returns an unmodifiable view of all cached reserve results.
     */
    public Map<String, ReserveResponse> getAllResults() {
        return Collections.unmodifiableMap(reserveResults);
    }

    // -------------------------------------------------------------------------
    // Private helpers / 私有辅助方法
    // -------------------------------------------------------------------------

    /**
     * 从 JSON 字符串解析 ReserveResponse（仅解析 taskId，results 为空列表占位）。
     * Parses a ReserveResponse from JSON (only taskId; results left as empty list placeholder).
     */
    private ReserveResponse parseResponse(String json) {
        if (json == null || json.isEmpty()) return null;
        String taskId = JsonUtils.extractField(json, "taskId", "task_id");
        List<ServerReserveResult> results = new ArrayList<>();
        // Simple result parsing: look for repeated "serverId"/"server_id" and "status" pairs
        int searchFrom = 0;
        while (true) {
            String srvPattern = "\"serverId\"";
            int srvIdx = json.indexOf(srvPattern, searchFrom);
            if (srvIdx < 0) {
                srvPattern = "\"server_id\"";
                srvIdx = json.indexOf(srvPattern, searchFrom);
            }
            if (srvIdx < 0) break;
            String serverId = JsonUtils.extractByKey(json.substring(srvIdx), srvPattern.replace("\"", ""));
            if (serverId == null) { searchFrom = srvIdx + 1; continue; }
            // Find the nearest "status" after serverId
            int statusIdx = json.indexOf("\"status\"", srvIdx);
            String statusStr = statusIdx >= 0 ? JsonUtils.extractByKey(json.substring(statusIdx), "status") : null;
            ReserveResultStatus status = ReserveResultStatus.FAILED;
            if (statusStr != null) {
                try { status = ReserveResultStatus.valueOf(statusStr.toUpperCase()); }
                catch (IllegalArgumentException ignored) {}
            }
            results.add(new ServerReserveResult(serverId, status));
            searchFrom = srvIdx + srvPattern.length();
        }
        return new ReserveResponse(taskId, results);
    }
}
