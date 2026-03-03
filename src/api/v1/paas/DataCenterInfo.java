package api.v1.paas;

import java.time.Instant;
import java.util.Objects;

/**
 * 机房信息。
 * Data center information.
 *
 * <p>Proto definition:
 * <pre>
 * message DataCenterInfo {
 *   string data_center_id = 1;
 *   string data_center_name = 2;
 *   google.protobuf.Timestamp create_time = 3;
 *   google.protobuf.Timestamp update_time = 4;
 *   google.protobuf.Timestamp delete_time = 5;
 * }
 * </pre>
 */
public final class DataCenterInfo {

    private final String dataCenterId;
    private final String dataCenterName;
    private final Instant createTime;
    private final Instant updateTime;
    private final Instant deleteTime;

    private DataCenterInfo(Builder builder) {
        this.dataCenterId = builder.dataCenterId;
        this.dataCenterName = builder.dataCenterName;
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
        this.deleteTime = builder.deleteTime;
    }

    public String getDataCenterId() {
        return dataCenterId;
    }

    /** 返回机房名称。 */
    public String getDataCenterName() {
        return dataCenterName;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    /** 返回软删除时间。 */
    public Instant getDeleteTime() {
        return deleteTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataCenterInfo)) return false;
        DataCenterInfo that = (DataCenterInfo) o;
        return Objects.equals(dataCenterId, that.dataCenterId)
                && Objects.equals(dataCenterName, that.dataCenterName)
                && Objects.equals(createTime, that.createTime)
                && Objects.equals(updateTime, that.updateTime)
                && Objects.equals(deleteTime, that.deleteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataCenterId, dataCenterName, createTime, updateTime, deleteTime);
    }

    @Override
    public String toString() {
        return "DataCenterInfo{"
                + "dataCenterId='" + dataCenterId + '\''
                + ", dataCenterName='" + dataCenterName + '\''
                + ", createTime=" + createTime
                + ", updateTime=" + updateTime
                + ", deleteTime=" + deleteTime
                + '}';
    }

    /** Builder for {@link DataCenterInfo}. */
    public static final class Builder {
        private String dataCenterId = "";
        private String dataCenterName = "";
        private Instant createTime;
        private Instant updateTime;
        private Instant deleteTime;

        private Builder() {}

        public Builder setDataCenterId(String dataCenterId) {
            this.dataCenterId = dataCenterId;
            return this;
        }

        public Builder setDataCenterName(String dataCenterName) {
            this.dataCenterName = dataCenterName;
            return this;
        }

        public Builder setCreateTime(Instant createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder setUpdateTime(Instant updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder setDeleteTime(Instant deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }

        public DataCenterInfo build() {
            return new DataCenterInfo(this);
        }
    }
}
