package paas.kafka;

import java.util.List;

/**
 * 批量取消预留请求消息，与 ReserveRequest 使用同一主题 gpuscheduler-paas-reserve。
 * Batch cancel-reserve request message, sent on the same topic as ReserveRequest.
 */
public class BatchCancelReserveRequest {

    /** 任务 ID（Kafka 分区键）。 Task ID (Kafka partition key). */
    private String taskId;

    /** 需要取消预留的服务器 ID 列表。 List of server IDs whose reservation should be cancelled. */
    private List<String> serverIds;

    /** 请求发起时间（Unix 毫秒）。 Request initiation time in Unix milliseconds. */
    private long requestTime;

    /** 无参构造函数。 No-args constructor. */
    public BatchCancelReserveRequest() {}

    /**
     * 全参构造函数。 All-args constructor.
     *
     * @param taskId      任务 ID / task ID
     * @param serverIds   服务器 ID 列表 / server ID list
     * @param requestTime 请求时间（ms）/ request time in ms
     */
    public BatchCancelReserveRequest(String taskId, List<String> serverIds, long requestTime) {
        this.taskId = taskId;
        this.serverIds = serverIds;
        this.requestTime = requestTime;
    }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public List<String> getServerIds() { return serverIds; }
    public void setServerIds(List<String> serverIds) { this.serverIds = serverIds; }

    public long getRequestTime() { return requestTime; }
    public void setRequestTime(long requestTime) { this.requestTime = requestTime; }

    @Override
    public String toString() {
        return "BatchCancelReserveRequest{taskId='" + taskId + "', serverIds=" + serverIds
                + ", requestTime=" + requestTime + "}";
    }
}
