package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 租户负载上报，以租户为根维度，包含租户整体负载及其下各节点、机房的负载明细。
 * Tenant load report with tenant as the root dimension, including overall tenant load
 * and per-node and per-data-center load details.
 *
 * <p>Topic: paas-tenant-load
 *
 * <p>Proto definition:
 * <pre>
 * message TenantLoadReport {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   string report_id = 2;
 *   string tenant_id = 3;
 *   string tenant_name = 4;
 *   int32 gpu_used_count = 5;
 *   int32 gpu_total_count = 6;
 *   int32 service_used_slots = 7;
 *   int32 service_total_slots = 8;
 *   int32 calculated_load_score = 9;
 *   repeated NodeLoadDetail node_loads = 10;
 *   repeated DataCenterLoadDetail data_center_loads = 11;
 *   google.protobuf.Timestamp report_time = 12;
 * }
 * </pre>
 */
public final class TenantLoadReport {

    private final MessageEnvelope envelope;
    private final String reportId;
    private final String tenantId;
    private final String tenantName;
    private final int gpuUsedCount;
    private final int gpuTotalCount;
    private final int serviceUsedSlots;
    private final int serviceTotalSlots;
    private final int calculatedLoadScore;
    private final List<NodeLoadDetail> nodeLoads;
    private final List<DataCenterLoadDetail> dataCenterLoads;
    private final Instant reportTime;

    private TenantLoadReport(Builder builder) {
        this.envelope = builder.envelope;
        this.reportId = builder.reportId;
        this.tenantId = builder.tenantId;
        this.tenantName = builder.tenantName;
        this.gpuUsedCount = builder.gpuUsedCount;
        this.gpuTotalCount = builder.gpuTotalCount;
        this.serviceUsedSlots = builder.serviceUsedSlots;
        this.serviceTotalSlots = builder.serviceTotalSlots;
        this.calculatedLoadScore = builder.calculatedLoadScore;
        this.nodeLoads = Collections.unmodifiableList(new ArrayList<>(builder.nodeLoads));
        this.dataCenterLoads = Collections.unmodifiableList(new ArrayList<>(builder.dataCenterLoads));
        this.reportTime = builder.reportTime;
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    public String getReportId() {
        return reportId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    /** 返回该租户已使用 GPU 数量。 */
    public int getGpuUsedCount() {
        return gpuUsedCount;
    }

    /** 返回该租户分配的 GPU 总量。 */
    public int getGpuTotalCount() {
        return gpuTotalCount;
    }

    /** 返回该租户已用路数。 */
    public int getServiceUsedSlots() {
        return serviceUsedSlots;
    }

    /** 返回该租户总路数。 */
    public int getServiceTotalSlots() {
        return serviceTotalSlots;
    }

    /** 返回综合负载分 0-100（调度引擎依据此值）。 */
    public int getCalculatedLoadScore() {
        return calculatedLoadScore;
    }

    /** 返回节点维度负载明细列表。 */
    public List<NodeLoadDetail> getNodeLoads() {
        return nodeLoads;
    }

    public int getNodeLoadsCount() {
        return nodeLoads.size();
    }

    /** 返回机房维度负载明细列表。 */
    public List<DataCenterLoadDetail> getDataCenterLoads() {
        return dataCenterLoads;
    }

    public int getDataCenterLoadsCount() {
        return dataCenterLoads.size();
    }

    public Instant getReportTime() {
        return reportTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantLoadReport)) return false;
        TenantLoadReport that = (TenantLoadReport) o;
        return gpuUsedCount == that.gpuUsedCount
                && gpuTotalCount == that.gpuTotalCount
                && serviceUsedSlots == that.serviceUsedSlots
                && serviceTotalSlots == that.serviceTotalSlots
                && calculatedLoadScore == that.calculatedLoadScore
                && Objects.equals(envelope, that.envelope)
                && Objects.equals(reportId, that.reportId)
                && Objects.equals(tenantId, that.tenantId)
                && Objects.equals(tenantName, that.tenantName)
                && Objects.equals(nodeLoads, that.nodeLoads)
                && Objects.equals(dataCenterLoads, that.dataCenterLoads)
                && Objects.equals(reportTime, that.reportTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, reportId, tenantId, tenantName, gpuUsedCount, gpuTotalCount,
                serviceUsedSlots, serviceTotalSlots, calculatedLoadScore, nodeLoads,
                dataCenterLoads, reportTime);
    }

    @Override
    public String toString() {
        return "TenantLoadReport{"
                + "envelope=" + envelope
                + ", reportId='" + reportId + '\''
                + ", tenantId='" + tenantId + '\''
                + ", tenantName='" + tenantName + '\''
                + ", gpuUsedCount=" + gpuUsedCount
                + ", gpuTotalCount=" + gpuTotalCount
                + ", serviceUsedSlots=" + serviceUsedSlots
                + ", serviceTotalSlots=" + serviceTotalSlots
                + ", calculatedLoadScore=" + calculatedLoadScore
                + ", nodeLoads=" + nodeLoads
                + ", dataCenterLoads=" + dataCenterLoads
                + ", reportTime=" + reportTime
                + '}';
    }

    /** Builder for {@link TenantLoadReport}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private String reportId = "";
        private String tenantId = "";
        private String tenantName = "";
        private int gpuUsedCount;
        private int gpuTotalCount;
        private int serviceUsedSlots;
        private int serviceTotalSlots;
        private int calculatedLoadScore;
        private List<NodeLoadDetail> nodeLoads = new ArrayList<>();
        private List<DataCenterLoadDetail> dataCenterLoads = new ArrayList<>();
        private Instant reportTime;

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setReportId(String reportId) {
            this.reportId = reportId;
            return this;
        }

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder setTenantName(String tenantName) {
            this.tenantName = tenantName;
            return this;
        }

        public Builder setGpuUsedCount(int gpuUsedCount) {
            this.gpuUsedCount = gpuUsedCount;
            return this;
        }

        public Builder setGpuTotalCount(int gpuTotalCount) {
            this.gpuTotalCount = gpuTotalCount;
            return this;
        }

        public Builder setServiceUsedSlots(int serviceUsedSlots) {
            this.serviceUsedSlots = serviceUsedSlots;
            return this;
        }

        public Builder setServiceTotalSlots(int serviceTotalSlots) {
            this.serviceTotalSlots = serviceTotalSlots;
            return this;
        }

        public Builder setCalculatedLoadScore(int calculatedLoadScore) {
            this.calculatedLoadScore = calculatedLoadScore;
            return this;
        }

        public Builder addNodeLoads(NodeLoadDetail nodeLoad) {
            this.nodeLoads.add(nodeLoad);
            return this;
        }

        public Builder addAllNodeLoads(List<NodeLoadDetail> nodeLoads) {
            this.nodeLoads.addAll(nodeLoads);
            return this;
        }

        public Builder addDataCenterLoads(DataCenterLoadDetail dataCenterLoad) {
            this.dataCenterLoads.add(dataCenterLoad);
            return this;
        }

        public Builder addAllDataCenterLoads(List<DataCenterLoadDetail> dataCenterLoads) {
            this.dataCenterLoads.addAll(dataCenterLoads);
            return this;
        }

        public Builder setReportTime(Instant reportTime) {
            this.reportTime = reportTime;
            return this;
        }

        public TenantLoadReport build() {
            return new TenantLoadReport(this);
        }
    }
}
