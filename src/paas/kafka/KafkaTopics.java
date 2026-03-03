package paas.kafka;

/**
 * PaaS 集成模块使用的所有 Kafka 主题名称常量。
 * All Kafka topic name constants used by the PaaS integration module.
 */
public final class KafkaTopics {

    private KafkaTopics() {}

    // ---- PaaS → Scheduler ----

    /** 全量实体同步主题（10 分钟一次）。 Full entity sync topic (10-minute cycle). */
    public static final String PAAS_RESOURCE_SYNC_FULL = "paas-resource-sync-full";

    /** 增量实体变更主题。 Incremental entity change topic. */
    public static final String PAAS_RESOURCE_SYNC_CHANGE = "paas-resource-sync-change";

    /** 资源健康状态主题（60s 心跳）。 Resource health status topic (60s heartbeat). */
    public static final String PAAS_RESOURCE_STATUS = "paas-resource-status";

    /** 租户负载上报主题。 Tenant load reporting topic. */
    public static final String PAAS_TENANT_LOAD = "paas-tenant-load";

    /** 预留操作结果回调主题。 Reserve operation result callback topic. */
    public static final String PAAS_RESERVE_RESPONSE = "paas-reserve-response";

    /** 腾挪操作结果回调主题。 Drain operation result callback topic. */
    public static final String PAAS_DRAIN_RESPONSE = "paas-drain-response";

    // ---- Scheduler → PaaS ----

    /** 调度事件通知主题。 Schedule event notification topic. */
    public static final String GPUSCHEDULER_SCHEDULE_EVENT = "gpuscheduler-schedule-event";

    /** 批量预留请求主题。 Batch reserve request topic. */
    public static final String GPUSCHEDULER_PAAS_RESERVE = "gpuscheduler-paas-reserve";

    /** 批量腾挪请求主题。 Batch drain request topic. */
    public static final String GPUSCHEDULER_PAAS_DRAIN = "gpuscheduler-paas-drain";
}
