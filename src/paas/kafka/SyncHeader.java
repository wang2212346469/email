package paas.kafka;

/**
 * PaaS Kafka 消息中的同步消息头，描述批次元数据。
 * Sync message header in PaaS Kafka messages describing batch metadata.
 *
 * <p>支持驼峰命名（camelCase）和下划线命名（snake_case）两种字段名风格，
 * 通过静态工厂方法分别解析。
 * Supports both camelCase and snake_case field name styles via static factory methods.</p>
 */
public class SyncHeader {

    /** 负载类型。 Payload type. */
    private PayloadType payloadType;

    /** 事件类型（UPSERT/DELETE）。 Event type (UPSERT/DELETE). */
    private EventType eventType;

    /** 本次全量同步中的实体总数。 Total entity count in this full sync. */
    private int totalCount;

    /** 当前批次索引（从 0 开始）。 Current batch index (0-based). */
    private int batchIndex;

    /** 总批次数。 Total batch count. */
    private int totalBatches;

    /** 无参构造函数。 No-args constructor. */
    public SyncHeader() {}

    /**
     * 全参构造函数。 All-args constructor.
     *
     * @param payloadType  负载类型 / payload type
     * @param eventType    事件类型 / event type
     * @param totalCount   实体总数 / total entity count
     * @param batchIndex   当前批次索引 / current batch index
     * @param totalBatches 总批次数 / total batch count
     */
    public SyncHeader(PayloadType payloadType, EventType eventType,
                      int totalCount, int batchIndex, int totalBatches) {
        this.payloadType = payloadType;
        this.eventType = eventType;
        this.totalCount = totalCount;
        this.batchIndex = batchIndex;
        this.totalBatches = totalBatches;
    }

    /**
     * 从驼峰命名风格的 JSON 字符串创建 SyncHeader。
     * Creates a SyncHeader from a camelCase JSON string.
     *
     * @param payloadTypeStr  payloadType 字段值 / payloadType field value
     * @param eventTypeStr    eventType 字段值 / eventType field value
     * @param totalCount      totalCount 字段值 / totalCount field value
     * @param batchIndex      batchIndex 字段值 / batchIndex field value
     * @param totalBatches    totalBatches 字段值 / totalBatches field value
     * @return 构建好的 SyncHeader 实例 / constructed SyncHeader instance
     */
    public static SyncHeader ofCamelCase(String payloadTypeStr, String eventTypeStr,
                                         int totalCount, int batchIndex, int totalBatches) {
        return new SyncHeader(
                PayloadType.valueOf(payloadTypeStr.toUpperCase()),
                EventType.valueOf(eventTypeStr.toUpperCase()),
                totalCount, batchIndex, totalBatches);
    }

    /**
     * 从下划线命名风格的字段值创建 SyncHeader。
     * Creates a SyncHeader from snake_case field values.
     *
     * @param payload_type   payload_type 字段值 / payload_type field value
     * @param event_type     event_type 字段值 / event_type field value
     * @param total_count    total_count 字段值 / total_count field value
     * @param batch_index    batch_index 字段值 / batch_index field value
     * @param total_batches  total_batches 字段值 / total_batches field value
     * @return 构建好的 SyncHeader 实例 / constructed SyncHeader instance
     */
    public static SyncHeader ofSnakeCase(String payload_type, String event_type,
                                          int total_count, int batch_index, int total_batches) {
        return new SyncHeader(
                PayloadType.valueOf(payload_type.toUpperCase()),
                EventType.valueOf(event_type.toUpperCase()),
                total_count, batch_index, total_batches);
    }

    public PayloadType getPayloadType() { return payloadType; }
    public void setPayloadType(PayloadType payloadType) { this.payloadType = payloadType; }

    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }

    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    public int getBatchIndex() { return batchIndex; }
    public void setBatchIndex(int batchIndex) { this.batchIndex = batchIndex; }

    public int getTotalBatches() { return totalBatches; }
    public void setTotalBatches(int totalBatches) { this.totalBatches = totalBatches; }

    @Override
    public String toString() {
        return "SyncHeader{payloadType=" + payloadType + ", eventType=" + eventType
                + ", totalCount=" + totalCount + ", batchIndex=" + batchIndex
                + ", totalBatches=" + totalBatches + "}";
    }
}
