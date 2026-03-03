package paas.kafka;

/**
 * Kafka 消息负载类型枚举，对应 PaaS 同步的六类实体。
 * Kafka message payload type enum corresponding to the six entity types synced from PaaS.
 */
public enum PayloadType {
    TENANT,
    GAME,
    NODE,
    DATA_CENTER,
    GPU_MODEL,
    GPU_RESOURCE
}
