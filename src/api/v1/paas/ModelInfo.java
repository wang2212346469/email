package api.v1.paas;

import java.time.Instant;
import java.util.Objects;

/**
 * 机型信息。
 * Machine model information.
 *
 * <p>Proto definition:
 * <pre>
 * message ModelInfo {
 *   string model_id = 1;
 *   string model_name = 2;
 *   google.protobuf.Timestamp create_time = 3;
 *   google.protobuf.Timestamp update_time = 4;
 *   google.protobuf.Timestamp delete_time = 5;
 * }
 * </pre>
 */
public final class ModelInfo {

    private final String modelId;
    private final String modelName;
    private final Instant createTime;
    private final Instant updateTime;
    private final Instant deleteTime;

    private ModelInfo(Builder builder) {
        this.modelId = builder.modelId;
        this.modelName = builder.modelName;
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
        this.deleteTime = builder.deleteTime;
    }

    public String getModelId() {
        return modelId;
    }

    public String getModelName() {
        return modelName;
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
        if (!(o instanceof ModelInfo)) return false;
        ModelInfo that = (ModelInfo) o;
        return Objects.equals(modelId, that.modelId)
                && Objects.equals(modelName, that.modelName)
                && Objects.equals(createTime, that.createTime)
                && Objects.equals(updateTime, that.updateTime)
                && Objects.equals(deleteTime, that.deleteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelId, modelName, createTime, updateTime, deleteTime);
    }

    @Override
    public String toString() {
        return "ModelInfo{"
                + "modelId='" + modelId + '\''
                + ", modelName='" + modelName + '\''
                + ", createTime=" + createTime
                + ", updateTime=" + updateTime
                + ", deleteTime=" + deleteTime
                + '}';
    }

    /** Builder for {@link ModelInfo}. */
    public static final class Builder {
        private String modelId = "";
        private String modelName = "";
        private Instant createTime;
        private Instant updateTime;
        private Instant deleteTime;

        private Builder() {}

        public Builder setModelId(String modelId) {
            this.modelId = modelId;
            return this;
        }

        public Builder setModelName(String modelName) {
            this.modelName = modelName;
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

        public ModelInfo build() {
            return new ModelInfo(this);
        }
    }
}
