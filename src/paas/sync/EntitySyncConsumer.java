package paas.sync;

import paas.kafka.EventType;
import paas.kafka.KafkaConsumerAdapter;
import paas.kafka.PayloadType;
import paas.kafka.SyncHeader;

/**
 * 实体同步消费者抽象基类，负责解析消息头并按事件类型路由。
 * Abstract base class for entity sync consumers, responsible for parsing the message header
 * and routing by event type.
 */
public abstract class EntitySyncConsumer extends KafkaConsumerAdapter {

    /**
     * 处理 UPSERT 事件（新增或更新实体）。
     * Handles UPSERT events (insert or update entity).
     *
     * @param payloadJson 实体 JSON 字符串 / entity JSON string
     */
    protected abstract void handleUpsert(String payloadJson);

    /**
     * 处理 DELETE 事件（删除实体）。
     * Handles DELETE events (delete entity).
     *
     * @param payloadJson 实体 JSON 字符串 / entity JSON string
     */
    protected abstract void handleDelete(String payloadJson);

    /**
     * 从完整消息体中解析同步消息头。
     * 支持 camelCase 和 snake_case 两种字段命名风格。
     * Parses the SyncHeader from the full message payload.
     * Supports both camelCase and snake_case field naming styles.
     *
     * @param payload 完整的 Kafka 消息体 / full Kafka message payload
     * @return 解析得到的 SyncHeader，解析失败时返回默认值 / parsed SyncHeader, defaults on failure
     */
    protected SyncHeader parseSyncHeader(String payload) {
        try {
            String payloadType = extractField(payload, "payloadType", "payload_type");
            String eventType   = extractField(payload, "eventType",   "event_type");
            int totalCount   = parseIntField(payload, "totalCount",   "total_count",   0);
            int batchIndex   = parseIntField(payload, "batchIndex",   "batch_index",   0);
            int totalBatches = parseIntField(payload, "totalBatches", "total_batches", 1);

            if (payloadType == null || payloadType.isEmpty()) {
                payloadType = PayloadType.GPU_RESOURCE.name();
            }
            if (eventType == null || eventType.isEmpty()) {
                eventType = EventType.UPSERT.name();
            }
            return SyncHeader.ofCamelCase(payloadType, eventType, totalCount, batchIndex, totalBatches);
        } catch (Exception e) {
            System.err.println("[EntitySyncConsumer] Failed to parse SyncHeader: " + e.getMessage());
            return new SyncHeader(PayloadType.GPU_RESOURCE, EventType.UPSERT, 0, 0, 1);
        }
    }

    /**
     * 根据消息头路由至对应的处理方法，并将消息体中的 "data" 部分传递给处理方法。
     * Routes to the appropriate handler based on the message header, passing the "data" field.
     */
    @Override
    protected void processMessage(String key, String payload) throws Exception {
        SyncHeader header = parseSyncHeader(payload);
        String dataJson = extractField(payload, "data", "data");
        if (dataJson == null || dataJson.isEmpty()) {
            dataJson = payload;
        }

        if (EventType.DELETE == header.getEventType()) {
            handleDelete(dataJson);
        } else {
            handleUpsert(dataJson);
        }
    }

    // -------------------------------------------------------------------------
    // Helper methods / 辅助方法
    // -------------------------------------------------------------------------

    /**
     * 从简单 JSON 字符串中按字段名（支持两种命名）提取字符串值。
     * Extracts a string value from a simple JSON string by field name (supports two naming styles).
     *
     * @param json       原始 JSON 字符串 / raw JSON string
     * @param camelKey   驼峰命名键名 / camelCase key name
     * @param snakeKey   下划线命名键名 / snake_case key name
     * @return 字段值，未找到时返回 null / field value, null if not found
     */
    protected static String extractField(String json, String camelKey, String snakeKey) {
        String val = extractByKey(json, camelKey);
        if (val == null) {
            val = extractByKey(json, snakeKey);
        }
        return val;
    }

    /**
     * 从简单 JSON 中按单个键名提取字符串值（不支持嵌套）。
     * Extracts a string value from a simple JSON by a single key name (no nested support).
     */
    private static String extractByKey(String json, String key) {
        if (json == null || key == null) return null;
        // Match "key":"value" or "key": "value"
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) return null;
        int colonIdx = json.indexOf(':', idx + pattern.length());
        if (colonIdx < 0) return null;
        int start = colonIdx + 1;
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }
        if (start >= json.length()) return null;
        if (json.charAt(start) == '"') {
            int end = json.indexOf('"', start + 1);
            if (end < 0) return null;
            return json.substring(start + 1, end);
        } else {
            // Number or boolean
            int end = start;
            while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') {
                end++;
            }
            return json.substring(start, end).trim();
        }
    }

    /**
     * 从简单 JSON 中按字段名提取整型值（支持两种命名）。
     * Extracts an integer value from a simple JSON by field name (supports two naming styles).
     */
    protected static int parseIntField(String json, String camelKey, String snakeKey, int defaultVal) {
        String val = extractField(json, camelKey, snakeKey);
        if (val == null || val.isEmpty()) return defaultVal;
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
