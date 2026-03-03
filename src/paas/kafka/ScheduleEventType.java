package paas.kafka;

/**
 * 调度事件类型枚举，用于调度器向 PaaS 发送调度事件通知。
 * Schedule event type enum used by the scheduler to notify PaaS of scheduling events.
 */
public enum ScheduleEventType {
    /** 将 GPU 资源借出给目标租户。 Lend GPU resource to target tenant. */
    LEND,
    /** 从租户回收 GPU 资源。 Reclaim GPU resource from tenant. */
    RECLAIM,
    /** 在租户间迁移 GPU 资源。 Transfer GPU resource between tenants. */
    TRANSFER
}
