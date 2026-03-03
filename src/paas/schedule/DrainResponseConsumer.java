package paas.schedule;

import paas.kafka.DrainResponse;
import paas.kafka.DrainResponse.ServerDrainResult;
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
        String taskId = extractField(json, "taskId", "task_id");
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
            String serverId = extractByKey(json.substring(srvIdx), srvPattern.replace("\"", ""));
            if (serverId == null) { searchFrom = srvIdx + 1; continue; }
            int successIdx = json.indexOf("\"success\"", srvIdx);
            boolean success = false;
            if (successIdx >= 0) {
                String successStr = extractByKey(json.substring(successIdx), "success");
                success = "true".equalsIgnoreCase(successStr);
            }
            int msgIdx = json.indexOf("\"message\"", srvIdx);
            String message = msgIdx >= 0 ? extractByKey(json.substring(msgIdx), "message") : "";
            results.add(new ServerDrainResult(serverId, success, message));
            searchFrom = srvIdx + srvPattern.length();
        }
        return new DrainResponse(taskId, results);
    }

    private static String extractField(String json, String camelKey, String snakeKey) {
        String val = extractByKey(json, camelKey);
        return val != null ? val : extractByKey(json, snakeKey);
    }

    private static String extractByKey(String json, String key) {
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) return null;
        int colonIdx = json.indexOf(':', idx + pattern.length());
        if (colonIdx < 0) return null;
        int start = colonIdx + 1;
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) start++;
        if (start >= json.length()) return null;
        if (json.charAt(start) == '"') {
            int end = json.indexOf('"', start + 1);
            return end < 0 ? null : json.substring(start + 1, end);
        } else {
            int end = start;
            while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') end++;
            return json.substring(start, end).trim();
        }
    }
}
