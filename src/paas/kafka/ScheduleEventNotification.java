package paas.kafka;

/**
 * 调度事件通知消息，发送到 gpuscheduler-schedule-event 主题。
 * Schedule event notification message sent to the gpuscheduler-schedule-event topic.
 */
public class ScheduleEventNotification {

    /** 服务器 ID（Kafka 分区键）。 Server ID (Kafka partition key). */
    private String serverId;

    /** 调度事件类型（LEND/RECLAIM/TRANSFER）。 Schedule event type (LEND/RECLAIM/TRANSFER). */
    private ScheduleEventType eventType;

    /** 触发本次调度的任务 ID。 Task ID that triggered this scheduling event. */
    private String sourceTaskId;

    /** 目标租户 ID（LEND/TRANSFER 场景）。 Target tenant ID (for LEND/TRANSFER events). */
    private String targetTenantId;

    /** 事件时间戳（Unix 毫秒）。 Event timestamp in Unix milliseconds. */
    private long timestamp;

    /** 无参构造函数。 No-args constructor. */
    public ScheduleEventNotification() {}

    /**
     * 全参构造函数。 All-args constructor.
     *
     * @param serverId       服务器 ID / server ID
     * @param eventType      事件类型 / event type
     * @param sourceTaskId   来源任务 ID / source task ID
     * @param targetTenantId 目标租户 ID / target tenant ID
     * @param timestamp      时间戳（ms）/ timestamp in ms
     */
    public ScheduleEventNotification(String serverId, ScheduleEventType eventType,
                                      String sourceTaskId, String targetTenantId, long timestamp) {
        this.serverId = serverId;
        this.eventType = eventType;
        this.sourceTaskId = sourceTaskId;
        this.targetTenantId = targetTenantId;
        this.timestamp = timestamp;
    }

    public String getServerId() { return serverId; }
    public void setServerId(String serverId) { this.serverId = serverId; }

    public ScheduleEventType getEventType() { return eventType; }
    public void setEventType(ScheduleEventType eventType) { this.eventType = eventType; }

    public String getSourceTaskId() { return sourceTaskId; }
    public void setSourceTaskId(String sourceTaskId) { this.sourceTaskId = sourceTaskId; }

    public String getTargetTenantId() { return targetTenantId; }
    public void setTargetTenantId(String targetTenantId) { this.targetTenantId = targetTenantId; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "ScheduleEventNotification{serverId='" + serverId + "', eventType=" + eventType
                + ", sourceTaskId='" + sourceTaskId + "', targetTenantId='" + targetTenantId
                + "', timestamp=" + timestamp + "}";
    }
}
