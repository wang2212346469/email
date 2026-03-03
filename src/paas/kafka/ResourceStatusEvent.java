package paas.kafka;

/**
 * PaaS 资源健康状态事件，来自 paas-resource-status 主题（60s 心跳）。
 * PaaS resource health status event from the paas-resource-status topic (60s heartbeat).
 */
public class ResourceStatusEvent {

    /** 服务器 ID（Kafka 分区键）。 Server ID (Kafka partition key). */
    private String serverId;

    /** PaaS 上报的原始状态字符串。 Raw status string reported by PaaS. */
    private String status;

    /** 事件时间戳（Unix 毫秒）。 Event timestamp in Unix milliseconds. */
    private long timestamp;

    /** 无参构造函数。 No-args constructor. */
    public ResourceStatusEvent() {}

    /**
     * 全参构造函数。 All-args constructor.
     *
     * @param serverId  服务器 ID / server ID
     * @param status    状态字符串 / status string
     * @param timestamp 时间戳（ms）/ timestamp in ms
     */
    public ResourceStatusEvent(String serverId, String status, long timestamp) {
        this.serverId = serverId;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getServerId() { return serverId; }
    public void setServerId(String serverId) { this.serverId = serverId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "ResourceStatusEvent{serverId='" + serverId + "', status='" + status
                + "', timestamp=" + timestamp + "}";
    }
}
