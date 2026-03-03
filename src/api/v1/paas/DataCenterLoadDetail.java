package api.v1.paas;

import java.util.Objects;

/**
 * 机房级负载明细，内嵌于 TenantLoadReport。
 * Data center level load detail, embedded in TenantLoadReport.
 * Describes load for a tenant in a specific data center.
 *
 * <p>Proto definition:
 * <pre>
 * message DataCenterLoadDetail {
 *   string data_center_id = 1;
 *   string data_center_code = 2;
 *   string data_center_name = 3;
 *   int32 total_gpu_count = 4;
 *   int32 used_gpu_count = 5;
 *   int32 available_gpu_count = 6;
 *   double avg_gpu_usage_percent = 7;
 *   int32 total_active_sessions = 8;
 *   int32 total_capacity = 9;
 *   int32 faulty_gpu_count = 10;
 * }
 * </pre>
 */
public final class DataCenterLoadDetail {

    private final String dataCenterId;
    private final String dataCenterCode;
    private final String dataCenterName;
    private final int totalGpuCount;
    private final int usedGpuCount;
    private final int availableGpuCount;
    private final double avgGpuUsagePercent;
    private final int totalActiveSessions;
    private final int totalCapacity;
    private final int faultyGpuCount;

    private DataCenterLoadDetail(Builder builder) {
        this.dataCenterId = builder.dataCenterId;
        this.dataCenterCode = builder.dataCenterCode;
        this.dataCenterName = builder.dataCenterName;
        this.totalGpuCount = builder.totalGpuCount;
        this.usedGpuCount = builder.usedGpuCount;
        this.availableGpuCount = builder.availableGpuCount;
        this.avgGpuUsagePercent = builder.avgGpuUsagePercent;
        this.totalActiveSessions = builder.totalActiveSessions;
        this.totalCapacity = builder.totalCapacity;
        this.faultyGpuCount = builder.faultyGpuCount;
    }

    public String getDataCenterId() {
        return dataCenterId;
    }

    public String getDataCenterCode() {
        return dataCenterCode;
    }

    public String getDataCenterName() {
        return dataCenterName;
    }

    /** 返回该租户在此机房的 GPU 总数。 */
    public int getTotalGpuCount() {
        return totalGpuCount;
    }

    /** 返回该租户在此机房的已用 GPU 数。 */
    public int getUsedGpuCount() {
        return usedGpuCount;
    }

    /** 返回该租户在此机房的可用 GPU 数。 */
    public int getAvailableGpuCount() {
        return availableGpuCount;
    }

    /** 返回平均 GPU 使用率。 */
    public double getAvgGpuUsagePercent() {
        return avgGpuUsagePercent;
    }

    /** 返回活跃会话总数。 */
    public int getTotalActiveSessions() {
        return totalActiveSessions;
    }

    /** 返回总容量。 */
    public int getTotalCapacity() {
        return totalCapacity;
    }

    /** 返回故障 GPU 数。 */
    public int getFaultyGpuCount() {
        return faultyGpuCount;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataCenterLoadDetail)) return false;
        DataCenterLoadDetail that = (DataCenterLoadDetail) o;
        return totalGpuCount == that.totalGpuCount
                && usedGpuCount == that.usedGpuCount
                && availableGpuCount == that.availableGpuCount
                && Double.compare(that.avgGpuUsagePercent, avgGpuUsagePercent) == 0
                && totalActiveSessions == that.totalActiveSessions
                && totalCapacity == that.totalCapacity
                && faultyGpuCount == that.faultyGpuCount
                && Objects.equals(dataCenterId, that.dataCenterId)
                && Objects.equals(dataCenterCode, that.dataCenterCode)
                && Objects.equals(dataCenterName, that.dataCenterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataCenterId, dataCenterCode, dataCenterName, totalGpuCount,
                usedGpuCount, availableGpuCount, avgGpuUsagePercent, totalActiveSessions,
                totalCapacity, faultyGpuCount);
    }

    @Override
    public String toString() {
        return "DataCenterLoadDetail{"
                + "dataCenterId='" + dataCenterId + '\''
                + ", dataCenterCode='" + dataCenterCode + '\''
                + ", dataCenterName='" + dataCenterName + '\''
                + ", totalGpuCount=" + totalGpuCount
                + ", usedGpuCount=" + usedGpuCount
                + ", availableGpuCount=" + availableGpuCount
                + ", avgGpuUsagePercent=" + avgGpuUsagePercent
                + ", totalActiveSessions=" + totalActiveSessions
                + ", totalCapacity=" + totalCapacity
                + ", faultyGpuCount=" + faultyGpuCount
                + '}';
    }

    /** Builder for {@link DataCenterLoadDetail}. */
    public static final class Builder {
        private String dataCenterId = "";
        private String dataCenterCode = "";
        private String dataCenterName = "";
        private int totalGpuCount;
        private int usedGpuCount;
        private int availableGpuCount;
        private double avgGpuUsagePercent;
        private int totalActiveSessions;
        private int totalCapacity;
        private int faultyGpuCount;

        private Builder() {}

        public Builder setDataCenterId(String dataCenterId) {
            this.dataCenterId = dataCenterId;
            return this;
        }

        public Builder setDataCenterCode(String dataCenterCode) {
            this.dataCenterCode = dataCenterCode;
            return this;
        }

        public Builder setDataCenterName(String dataCenterName) {
            this.dataCenterName = dataCenterName;
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

        public Builder setAvgGpuUsagePercent(double avgGpuUsagePercent) {
            this.avgGpuUsagePercent = avgGpuUsagePercent;
            return this;
        }

        public Builder setTotalActiveSessions(int totalActiveSessions) {
            this.totalActiveSessions = totalActiveSessions;
            return this;
        }

        public Builder setTotalCapacity(int totalCapacity) {
            this.totalCapacity = totalCapacity;
            return this;
        }

        public Builder setFaultyGpuCount(int faultyGpuCount) {
            this.faultyGpuCount = faultyGpuCount;
            return this;
        }

        public DataCenterLoadDetail build() {
            return new DataCenterLoadDetail(this);
        }
    }
}
