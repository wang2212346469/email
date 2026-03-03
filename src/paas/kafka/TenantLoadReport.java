package paas.kafka;

import java.util.List;

/**
 * 租户负载上报消息，来自 paas-tenant-load 主题。
 * Tenant load report message from the paas-tenant-load topic.
 */
public class TenantLoadReport {

    /** 租户 ID（Kafka 分区键）。 Tenant ID (Kafka partition key). */
    private String tenantId;

    /** 综合负载评分。 Calculated overall load score. */
    private double calculatedLoadScore;

    /** 各节点的负载详情列表。 Per-node load detail list. */
    private List<NodeLoadDetail> nodeLoadDetails;

    /** 各数据中心的负载详情列表。 Per-data-center load detail list. */
    private List<DataCenterLoadDetail> dataCenterLoadDetails;

    /** 上报时间戳（Unix 毫秒）。 Report timestamp in Unix milliseconds. */
    private long timestamp;

    // -------------------------------------------------------------------------
    // Inner classes / 内部类
    // -------------------------------------------------------------------------

    /**
     * 单节点负载详情。 Per-node load detail.
     */
    public static class NodeLoadDetail {
        private String nodeId;
        private double loadScore;

        public NodeLoadDetail() {}

        public NodeLoadDetail(String nodeId, double loadScore) {
            this.nodeId = nodeId;
            this.loadScore = loadScore;
        }

        public String getNodeId() { return nodeId; }
        public void setNodeId(String nodeId) { this.nodeId = nodeId; }

        public double getLoadScore() { return loadScore; }
        public void setLoadScore(double loadScore) { this.loadScore = loadScore; }

        @Override
        public String toString() {
            return "NodeLoadDetail{nodeId='" + nodeId + "', loadScore=" + loadScore + "}";
        }
    }

    /**
     * 单数据中心负载详情。 Per-data-center load detail.
     */
    public static class DataCenterLoadDetail {
        private String dcId;
        private double loadScore;

        public DataCenterLoadDetail() {}

        public DataCenterLoadDetail(String dcId, double loadScore) {
            this.dcId = dcId;
            this.loadScore = loadScore;
        }

        public String getDcId() { return dcId; }
        public void setDcId(String dcId) { this.dcId = dcId; }

        public double getLoadScore() { return loadScore; }
        public void setLoadScore(double loadScore) { this.loadScore = loadScore; }

        @Override
        public String toString() {
            return "DataCenterLoadDetail{dcId='" + dcId + "', loadScore=" + loadScore + "}";
        }
    }

    // -------------------------------------------------------------------------
    // Constructors / 构造函数
    // -------------------------------------------------------------------------

    /** 无参构造函数。 No-args constructor. */
    public TenantLoadReport() {}

    /**
     * 全参构造函数。 All-args constructor.
     *
     * @param tenantId               租户 ID / tenant ID
     * @param calculatedLoadScore    综合负载评分 / calculated load score
     * @param nodeLoadDetails        节点负载详情 / node load details
     * @param dataCenterLoadDetails  数据中心负载详情 / data center load details
     * @param timestamp              时间戳（ms）/ timestamp in ms
     */
    public TenantLoadReport(String tenantId, double calculatedLoadScore,
                             List<NodeLoadDetail> nodeLoadDetails,
                             List<DataCenterLoadDetail> dataCenterLoadDetails,
                             long timestamp) {
        this.tenantId = tenantId;
        this.calculatedLoadScore = calculatedLoadScore;
        this.nodeLoadDetails = nodeLoadDetails;
        this.dataCenterLoadDetails = dataCenterLoadDetails;
        this.timestamp = timestamp;
    }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public double getCalculatedLoadScore() { return calculatedLoadScore; }
    public void setCalculatedLoadScore(double calculatedLoadScore) { this.calculatedLoadScore = calculatedLoadScore; }

    public List<NodeLoadDetail> getNodeLoadDetails() { return nodeLoadDetails; }
    public void setNodeLoadDetails(List<NodeLoadDetail> nodeLoadDetails) { this.nodeLoadDetails = nodeLoadDetails; }

    public List<DataCenterLoadDetail> getDataCenterLoadDetails() { return dataCenterLoadDetails; }
    public void setDataCenterLoadDetails(List<DataCenterLoadDetail> dataCenterLoadDetails) { this.dataCenterLoadDetails = dataCenterLoadDetails; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "TenantLoadReport{tenantId='" + tenantId + "', calculatedLoadScore=" + calculatedLoadScore
                + ", timestamp=" + timestamp + "}";
    }
}
