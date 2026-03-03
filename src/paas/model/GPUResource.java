package paas.model;

/**
 * 表示 PaaS 平台中的 GPU 物理资源实体。
 * Represents a GPU physical resource entity in the PaaS platform.
 */
public class GPUResource {

    private String serverId;
    private String sn;
    private String tenantId;
    private String nodeId;
    private String modelId;
    private String status;
    private String poolId;
    /** GPU 卡所在宿主机 IP 地址。 IP address of the host where the GPU card resides. */
    private String ipAddress;
    /** 单机 GPU 卡数量。 Number of GPU cards on the server. */
    private int gpuCount;
    /** 总显存容量（GB）。 Total GPU memory capacity in GB. */
    private int totalMemoryGb;
    /** GPU 健康状态（来自 PaaS 心跳）。 GPU health status (from PaaS heartbeat). */
    private String healthStatus;
    /** 资源创建时间（Unix 毫秒）。 Resource creation time in Unix milliseconds. */
    private long createdAt;
    private String rawData;

    /** 无参构造函数。 No-args constructor. */
    public GPUResource() {}

    /**
     * 全参构造函数（rawData 需单独设置）。
     * All-args constructor (rawData set separately).
     *
     * @param serverId      服务器 ID / server ID
     * @param sn            序列号 / serial number
     * @param tenantId      所属租户 ID / owner tenant ID
     * @param nodeId        所属节点 ID / node ID
     * @param modelId       GPU 型号 ID / GPU model ID
     * @param status        资源状态 / resource status
     * @param poolId        资源池 ID / resource pool ID
     * @param ipAddress     宿主机 IP / host IP address
     * @param gpuCount      GPU 卡数量 / GPU card count
     * @param totalMemoryGb 总显存（GB）/ total GPU memory in GB
     * @param healthStatus  健康状态 / health status
     * @param createdAt     创建时间（ms）/ creation time in ms
     */
    public GPUResource(String serverId, String sn, String tenantId, String nodeId,
                       String modelId, String status, String poolId,
                       String ipAddress, int gpuCount, int totalMemoryGb,
                       String healthStatus, long createdAt) {
        this.serverId = serverId;
        this.sn = sn;
        this.tenantId = tenantId;
        this.nodeId = nodeId;
        this.modelId = modelId;
        this.status = status;
        this.poolId = poolId;
        this.ipAddress = ipAddress;
        this.gpuCount = gpuCount;
        this.totalMemoryGb = totalMemoryGb;
        this.healthStatus = healthStatus;
        this.createdAt = createdAt;
    }

    public String getServerId() { return serverId; }
    public void setServerId(String serverId) { this.serverId = serverId; }

    public String getSn() { return sn; }
    public void setSn(String sn) { this.sn = sn; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getNodeId() { return nodeId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }

    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPoolId() { return poolId; }
    public void setPoolId(String poolId) { this.poolId = poolId; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public int getGpuCount() { return gpuCount; }
    public void setGpuCount(int gpuCount) { this.gpuCount = gpuCount; }

    public int getTotalMemoryGb() { return totalMemoryGb; }
    public void setTotalMemoryGb(int totalMemoryGb) { this.totalMemoryGb = totalMemoryGb; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }

    @Override
    public String toString() {
        return "GPUResource{serverId='" + serverId + "', sn='" + sn
                + "', tenantId='" + tenantId + "', nodeId='" + nodeId
                + "', modelId='" + modelId + "', status='" + status
                + "', poolId='" + poolId + "', gpuCount=" + gpuCount
                + ", totalMemoryGb=" + totalMemoryGb + ", healthStatus='" + healthStatus + "'}";
    }
}
