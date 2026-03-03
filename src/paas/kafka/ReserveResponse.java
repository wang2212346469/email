package paas.kafka;

import java.util.List;

/**
 * 批量资源预留响应消息，来自 paas-reserve-response 主题。
 * Batch resource reserve response message from the paas-reserve-response topic.
 */
public class ReserveResponse {

    /** 任务 ID（与请求对应）。 Task ID (matches the corresponding request). */
    private String taskId;

    /** 各服务器的预留结果列表。 Per-server reserve result list. */
    private List<ServerReserveResult> results;

    // -------------------------------------------------------------------------
    // Inner class / 内部类
    // -------------------------------------------------------------------------

    /**
     * 单台服务器的预留结果。 Reserve result for a single server.
     */
    public static class ServerReserveResult {
        /** 服务器 ID。 Server ID. */
        private String serverId;
        /** 预留结果状态。 Reserve result status. */
        private ReserveResultStatus status;

        public ServerReserveResult() {}

        public ServerReserveResult(String serverId, ReserveResultStatus status) {
            this.serverId = serverId;
            this.status = status;
        }

        public String getServerId() { return serverId; }
        public void setServerId(String serverId) { this.serverId = serverId; }

        public ReserveResultStatus getStatus() { return status; }
        public void setStatus(ReserveResultStatus status) { this.status = status; }

        @Override
        public String toString() {
            return "ServerReserveResult{serverId='" + serverId + "', status=" + status + "}";
        }
    }

    // -------------------------------------------------------------------------
    // Constructors / 构造函数
    // -------------------------------------------------------------------------

    /** 无参构造函数。 No-args constructor. */
    public ReserveResponse() {}

    /**
     * 全参构造函数。 All-args constructor.
     *
     * @param taskId  任务 ID / task ID
     * @param results 预留结果列表 / reserve result list
     */
    public ReserveResponse(String taskId, List<ServerReserveResult> results) {
        this.taskId = taskId;
        this.results = results;
    }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public List<ServerReserveResult> getResults() { return results; }
    public void setResults(List<ServerReserveResult> results) { this.results = results; }

    @Override
    public String toString() {
        return "ReserveResponse{taskId='" + taskId + "', results=" + results + "}";
    }
}
