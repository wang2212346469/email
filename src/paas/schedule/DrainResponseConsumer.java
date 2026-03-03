package paas.schedule;

import paas.kafka.DrainResponse;
import paas.kafka.DrainResponse.ServerDrainResult;
import paas.kafka.JsonUtils;
import paas.kafka.KafkaConsumerAdapter;
import paas.kafka.KafkaTopics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 腾挪响应消费者，订阅 paas-drain-response 主题，
 * 缓存各任务的腾挪操作结果，供调度器异步查询。
 *
 * Drain response consumer subscribing to the paas-drain-response topic,
 * caching drain operation results per task for asynchronous query by the scheduler.
 */
public class DrainResponseConsumer extends KafkaConsumerAdapter {

    /**
     * 任务 ID → 腾挪响应 的内存缓存。
     * In-memory cache of task ID to drain response.
     */
    private final Map<String, DrainResponse> drainResults = new HashMap<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_DRAIN_RESPONSE;
    }

    /**
     * 解析 DrainResponse 并存入缓存。
     * Parses a DrainResponse and stores it in the cache.
     */
    @Override
    protected void processMessage(String key, String payload) {
        DrainResponse response = parseResponse(payload);
        if (response == null || response.getTaskId() == null) {
            System.err.println("[DrainResponseConsumer] Invalid response, skipping");
            return;
        }
        drainResults.put(response.getTaskId(), response);
        System.out.println("[DrainResponseConsumer] Stored result for taskId=" + response.getTaskId());
    }

    /**
     * 获取指定任务 ID 的腾挪操作结果，未收到响应时返回 null。
     * Gets the drain operation result for a task ID, returns null if not yet received.
     *
     * @param taskId 任务 ID / task ID
     * @return 腾挪响应，或 null / drain response, or null
     */
    public DrainResponse getResult(String taskId) {
        return drainResults.get(taskId);
    }

    /**
     * 返回所有腾挪结果缓存的不可变视图。
     * Returns an unmodifiable view of all cached drain results.
     */
    public Map<String, DrainResponse> getAllResults() {
        return Collections.unmodifiableMap(drainResults);
    }

    // -------------------------------------------------------------------------
    // Private helpers / 私有辅助方法
    // -------------------------------------------------------------------------

    /**
     * 从 JSON 字符串解析 DrainResponse。
     * Parses a DrainResponse from a JSON string.
     */
    private DrainResponse parseResponse(String json) {
        if (json == null || json.isEmpty()) return null;
        String taskId = JsonUtils.extractField(json, "taskId", "task_id");
        List<ServerDrainResult> results = new ArrayList<>();

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
            int successIdx = json.indexOf("\"success\"", srvIdx);
            boolean success = false;
            if (successIdx >= 0) {
                String successStr = JsonUtils.extractByKey(json.substring(successIdx), "success");
                success = "true".equalsIgnoreCase(successStr);
            }
            int msgIdx = json.indexOf("\"message\"", srvIdx);
            String message = msgIdx >= 0 ? JsonUtils.extractByKey(json.substring(msgIdx), "message") : "";
            results.add(new ServerDrainResult(serverId, success, message));
            searchFrom = srvIdx + srvPattern.length();
        }
        return new DrainResponse(taskId, results);
    }
}
