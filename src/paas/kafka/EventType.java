package paas.kafka;

/**
 * 实体同步事件类型枚举。
 * Entity sync event type enum.
 */
public enum EventType {
    /** 新增或更新实体。 Insert or update entity. */
    UPSERT,
    /** 删除实体。 Delete entity. */
    DELETE
}
