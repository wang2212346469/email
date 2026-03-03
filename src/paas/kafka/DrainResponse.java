package paas.kafka;

import java.util.List;

/**
 * 批量资源腾挪响应消息，来自 paas-drain-response 主题。
 * Batch resource drain response message from the paas-drain-response topic.
 */
public class DrainResponse {

    /** 任务 ID（与请求对应）。 Task ID (matches the corresponding request). */
    private String taskId;

    /** 各服务器的腾挪结果列表。 Per-server drain result list. */
    private List<ServerDrainResult> results;

    // -------------------------------------------------------------------------
    // Inner class / 内部类
    // -------------------------------------------------------------------------

    /**
     * 单台服务器的腾挪结果。 Drain result for a single server.
     */
    public static class ServerDrainResult {
        /** 服务器 ID。 Server ID. */
        private String serverId;
        /** 是否成功。 Whether drain succeeded. */
        private boolean success;
        /** 失败原因或附加信息。 Failure reason or additional info. */
        private String message;

        public ServerDrainResult() {}

        public ServerDrainResult(String serverId, boolean success, String message) {
            this.serverId = serverId;
            this.success = success;
            this.message = message;
        }

        public String getServerId() { return serverId; }
        public void setServerId(String serverId) { this.serverId = serverId; }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        @Override
        public String toString() {
            return "ServerDrainResult{serverId='" + serverId + "', success=" + success
                    + ", message='" + message + "'}";
        }
    }

    // -------------------------------------------------------------------------
    // Constructors / 构造函数
    // -------------------------------------------------------------------------

    /** 无参构造函数。 No-args constructor. */
    public DrainResponse() {}

    /**
     * 全参构造函数。 All-args constructor.
     *
     * @param taskId  任务 ID / task ID
     * @param results 腾挪结果列表 / drain result list
     */
    public DrainResponse(String taskId, List<ServerDrainResult> results) {
        this.taskId = taskId;
        this.results = results;
    }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public List<ServerDrainResult> getResults() { return results; }
    public void setResults(List<ServerDrainResult> results) { this.results = results; }

    @Override
    public String toString() {
        return "DrainResponse{taskId='" + taskId + "', results=" + results + "}";
    }
}
