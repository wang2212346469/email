package api.v1.paas;

import java.time.Instant;
import java.util.Objects;

/**
 * 租户信息。
 * Tenant information.
 *
 * <p>Proto definition:
 * <pre>
 * message TenantInfo {
 *   string tenant_id = 1;
 *   string tenant_name = 2;
 *   google.protobuf.Timestamp create_time = 3;
 *   google.protobuf.Timestamp update_time = 4;
 *   string description = 5;
 *   google.protobuf.Timestamp delete_time = 6;
 * }
 * </pre>
 */
public final class TenantInfo {

    private final String tenantId;
    private final String tenantName;
    private final Instant createTime;
    private final Instant updateTime;
    private final String description;
    private final Instant deleteTime;

    private TenantInfo(Builder builder) {
        this.tenantId = builder.tenantId;
        this.tenantName = builder.tenantName;
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
        this.description = builder.description;
        this.deleteTime = builder.deleteTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    /** 返回租户描述。 */
    public String getDescription() {
        return description;
    }

    /** 返回软删除时间，非空表示已删除。 */
    public Instant getDeleteTime() {
        return deleteTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantInfo)) return false;
        TenantInfo that = (TenantInfo) o;
        return Objects.equals(tenantId, that.tenantId)
                && Objects.equals(tenantName, that.tenantName)
                && Objects.equals(createTime, that.createTime)
                && Objects.equals(updateTime, that.updateTime)
                && Objects.equals(description, that.description)
                && Objects.equals(deleteTime, that.deleteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, tenantName, createTime, updateTime, description, deleteTime);
    }

    @Override
    public String toString() {
        return "TenantInfo{"
                + "tenantId='" + tenantId + '\''
                + ", tenantName='" + tenantName + '\''
                + ", createTime=" + createTime
                + ", updateTime=" + updateTime
                + ", description='" + description + '\''
                + ", deleteTime=" + deleteTime
                + '}';
    }

    /** Builder for {@link TenantInfo}. */
    public static final class Builder {
        private String tenantId = "";
        private String tenantName = "";
        private Instant createTime;
        private Instant updateTime;
        private String description = "";
        private Instant deleteTime;

        private Builder() {}

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder setTenantName(String tenantName) {
            this.tenantName = tenantName;
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

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDeleteTime(Instant deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }

        public TenantInfo build() {
            return new TenantInfo(this);
        }
    }
}
