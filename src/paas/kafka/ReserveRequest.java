package paas.kafka;

import java.util.List;

/**
 * 批量资源预留请求消息，发送到 gpuscheduler-paas-reserve 主题。
 * Batch resource reserve request message sent to the gpuscheduler-paas-reserve topic.
 */
public class ReserveRequest {

    /** 任务 ID（Kafka 分区键）。 Task ID (Kafka partition key). */
    private String taskId;

    /** 指定预留的服务器 ID 列表（与 selectionCriteria 二选一）。
     *  Explicit server ID list to reserve (mutually exclusive with selectionCriteria). */
    private List<String> serverIds;

    /** 按条件筛选预留的参数（与 serverIds 二选一）。
     *  Criteria-based selection parameters (mutually exclusive with serverIds). */
    private SelectionCriteria selectionCriteria;

    /** 请求发起时间（Unix 毫秒）。 Request initiation time in Unix milliseconds. */
    private long requestTime;

    // -------------------------------------------------------------------------
    // Inner class / 内部类
    // -------------------------------------------------------------------------

    /**
     * GPU 资源筛选条件。 GPU resource selection criteria.
     */
    public static class SelectionCriteria {
        /** 目标节点 ID（可为 null 表示不限）。 Target node ID (null means no constraint). */
        private String nodeId;
        /** 目标数据中心 ID（可为 null 表示不限）。 Target data center ID (null means no constraint). */
        private String dataCenterId;
        /** 要求的 GPU 型号 ID（可为 null 表示不限）。 Required GPU model ID (null means no constraint). */
        private String gpuModelId;
        /** 需要预留的 GPU 数量。 Number of GPU units to reserve. */
        private int count;

        public SelectionCriteria() {}

        public SelectionCriteria(String nodeId, String dataCenterId, String gpuModelId, int count) {
            this.nodeId = nodeId;
            this.dataCenterId = dataCenterId;
            this.gpuModelId = gpuModelId;
            this.count = count;
        }

        public String getNodeId() { return nodeId; }
        public void setNodeId(String nodeId) { this.nodeId = nodeId; }

        public String getDataCenterId() { return dataCenterId; }
        public void setDataCenterId(String dataCenterId) { this.dataCenterId = dataCenterId; }

        public String getGpuModelId() { return gpuModelId; }
        public void setGpuModelId(String gpuModelId) { this.gpuModelId = gpuModelId; }

        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }

        @Override
        public String toString() {
            return "SelectionCriteria{nodeId='" + nodeId + "', dataCenterId='" + dataCenterId
                    + "', gpuModelId='" + gpuModelId + "', count=" + count + "}";
        }
    }

    // -------------------------------------------------------------------------
    // Constructors / 构造函数
    // -------------------------------------------------------------------------

    /** 无参构造函数。 No-args constructor. */
    public ReserveRequest() {}

    /**
     * 全参构造函数。 All-args constructor.
     *
     * @param taskId            任务 ID / task ID
     * @param serverIds         服务器 ID 列表 / server ID list
     * @param selectionCriteria 筛选条件 / selection criteria
     * @param requestTime       请求时间（ms）/ request time in ms
     */
    public ReserveRequest(String taskId, List<String> serverIds,
                           SelectionCriteria selectionCriteria, long requestTime) {
        this.taskId = taskId;
        this.serverIds = serverIds;
        this.selectionCriteria = selectionCriteria;
        this.requestTime = requestTime;
    }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public List<String> getServerIds() { return serverIds; }
    public void setServerIds(List<String> serverIds) { this.serverIds = serverIds; }

    public SelectionCriteria getSelectionCriteria() { return selectionCriteria; }
    public void setSelectionCriteria(SelectionCriteria selectionCriteria) { this.selectionCriteria = selectionCriteria; }

    public long getRequestTime() { return requestTime; }
    public void setRequestTime(long requestTime) { this.requestTime = requestTime; }

    @Override
    public String toString() {
        return "ReserveRequest{taskId='" + taskId + "', serverIds=" + serverIds
                + ", selectionCriteria=" + selectionCriteria + ", requestTime=" + requestTime + "}";
    }
}
