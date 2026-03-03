package api.v1.paas;

import java.util.Objects;

/**
 * 节点级负载明细，内嵌于 TenantLoadReport。
 * Node-level load detail, embedded in TenantLoadReport.
 * Describes load for a tenant on a specific node.
 *
 * <p>Proto definition:
 * <pre>
 * message NodeLoadDetail {
 *   string node_id = 1;
 *   string node_name = 2;
 *   int32 total_gpu_count = 3;
 *   int32 used_gpu_count = 4;
 *   int32 available_gpu_count = 5;
 *   double gpu_usage_percent = 6;
 *   int32 active_sessions = 7;
 *   int32 total_capacity = 8;
 * }
 * </pre>
 */
public final class NodeLoadDetail {

    private final String nodeId;
    private final String nodeName;
    private final int totalGpuCount;
    private final int usedGpuCount;
    private final int availableGpuCount;
    private final double gpuUsagePercent;
    private final int activeSessions;
    private final int totalCapacity;

    private NodeLoadDetail(Builder builder) {
        this.nodeId = builder.nodeId;
        this.nodeName = builder.nodeName;
        this.totalGpuCount = builder.totalGpuCount;
        this.usedGpuCount = builder.usedGpuCount;
        this.availableGpuCount = builder.availableGpuCount;
        this.gpuUsagePercent = builder.gpuUsagePercent;
        this.activeSessions = builder.activeSessions;
        this.totalCapacity = builder.totalCapacity;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    /** 返回该租户在此节点的 GPU 总数。 */
    public int getTotalGpuCount() {
        return totalGpuCount;
    }

    /** 返回该租户在此节点的已用 GPU 数。 */
    public int getUsedGpuCount() {
        return usedGpuCount;
    }

    /** 返回该租户在此节点的可用 GPU 数。 */
    public int getAvailableGpuCount() {
        return availableGpuCount;
    }

    /** 返回 GPU 使用率。 */
    public double getGpuUsagePercent() {
        return gpuUsagePercent;
    }

    /** 返回活跃会话数。 */
    public int getActiveSessions() {
        return activeSessions;
    }

    /** 返回总容量（路数）。 */
    public int getTotalCapacity() {
        return totalCapacity;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeLoadDetail)) return false;
        NodeLoadDetail that = (NodeLoadDetail) o;
        return totalGpuCount == that.totalGpuCount
                && usedGpuCount == that.usedGpuCount
                && availableGpuCount == that.availableGpuCount
                && Double.compare(that.gpuUsagePercent, gpuUsagePercent) == 0
                && activeSessions == that.activeSessions
                && totalCapacity == that.totalCapacity
                && Objects.equals(nodeId, that.nodeId)
                && Objects.equals(nodeName, that.nodeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, nodeName, totalGpuCount, usedGpuCount, availableGpuCount,
                gpuUsagePercent, activeSessions, totalCapacity);
    }

    @Override
    public String toString() {
        return "NodeLoadDetail{"
                + "nodeId='" + nodeId + '\''
                + ", nodeName='" + nodeName + '\''
                + ", totalGpuCount=" + totalGpuCount
                + ", usedGpuCount=" + usedGpuCount
                + ", availableGpuCount=" + availableGpuCount
                + ", gpuUsagePercent=" + gpuUsagePercent
                + ", activeSessions=" + activeSessions
                + ", totalCapacity=" + totalCapacity
                + '}';
    }

    /** Builder for {@link NodeLoadDetail}. */
    public static final class Builder {
        private String nodeId = "";
        private String nodeName = "";
        private int totalGpuCount;
        private int usedGpuCount;
        private int availableGpuCount;
        private double gpuUsagePercent;
        private int activeSessions;
        private int totalCapacity;

        private Builder() {}

        public Builder setNodeId(String nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        public Builder setNodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public Builder setTotalGpuCount(int totalGpuCount) {
            this.totalGpuCount = totalGpuCount;
            return this;
        }

        public Builder setUsedGpuCount(int usedGpuCount) {
            this.usedGpuCount = usedGpuCount;
            return this;
        }

        public Builder setAvailableGpuCount(int availableGpuCount) {
            this.availableGpuCount = availableGpuCount;
            return this;
        }

        public Builder setGpuUsagePercent(double gpuUsagePercent) {
            this.gpuUsagePercent = gpuUsagePercent;
            return this;
        }

        public Builder setActiveSessions(int activeSessions) {
            this.activeSessions = activeSessions;
            return this;
        }

        public Builder setTotalCapacity(int totalCapacity) {
            this.totalCapacity = totalCapacity;
            return this;
        }

        public NodeLoadDetail build() {
            return new NodeLoadDetail(this);
        }
    }
}
