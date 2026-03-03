package api.v1.paas;

import java.time.Instant;
import java.util.Objects;

/**
 * 同步消息公共头，各实体同步消息共用的元数据字段。
 * Common header for sync messages shared by all entity sync messages.
 *
 * <p>Proto definition:
 * <pre>
 * message SyncHeader {
 *   string request_id = 1;
 *   SyncPayloadType payload_type = 2;
 *   google.protobuf.Timestamp sync_time = 3;
 *   bool is_full_sync = 4;
 *   int32 batch_index = 5;
 *   bool is_last_batch = 6;
 * }
 * </pre>
 */
public final class SyncHeader {

    private final String requestId;
    private final SyncPayloadType payloadType;
    private final Instant syncTime;
    private final boolean isFullSync;
    private final int batchIndex;
    private final boolean isLastBatch;

    private SyncHeader(Builder builder) {
        this.requestId = builder.requestId;
        this.payloadType = builder.payloadType;
        this.syncTime = builder.syncTime;
        this.isFullSync = builder.isFullSync;
        this.batchIndex = builder.batchIndex;
        this.isLastBatch = builder.isLastBatch;
    }

    /** 返回请求 ID（幂等键，关联同一次同步）。 */
    public String getRequestId() {
        return requestId;
    }

    /** 返回载荷类型。 */
    public SyncPayloadType getPayloadType() {
        return payloadType;
    }

    public Instant getSyncTime() {
        return syncTime;
    }

    /** 返回 true 表示全量同步，false 表示增量同步。 */
    public boolean isFullSync() {
        return isFullSync;
    }

    /** 返回批次序号（全量同步时使用，从 0 开始）。 */
    public int getBatchIndex() {
        return batchIndex;
    }

    /** 返回是否最后一批（全量同步时使用）。 */
    public boolean isLastBatch() {
        return isLastBatch;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SyncHeader)) return false;
        SyncHeader that = (SyncHeader) o;
        return isFullSync == that.isFullSync
                && batchIndex == that.batchIndex
                && isLastBatch == that.isLastBatch
                && Objects.equals(requestId, that.requestId)
                && payloadType == that.payloadType
                && Objects.equals(syncTime, that.syncTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, payloadType, syncTime, isFullSync, batchIndex, isLastBatch);
    }

    @Override
    public String toString() {
        return "SyncHeader{"
                + "requestId='" + requestId + '\''
                + ", payloadType=" + payloadType
                + ", syncTime=" + syncTime
                + ", isFullSync=" + isFullSync
                + ", batchIndex=" + batchIndex
                + ", isLastBatch=" + isLastBatch
                + '}';
    }

    /** Builder for {@link SyncHeader}. */
    public static final class Builder {
        private String requestId = "";
        private SyncPayloadType payloadType = SyncPayloadType.SYNC_PAYLOAD_TYPE_UNSPECIFIED;
        private Instant syncTime;
        private boolean isFullSync;
        private int batchIndex;
        private boolean isLastBatch;

        private Builder() {}

        public Builder setRequestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder setPayloadType(SyncPayloadType payloadType) {
            this.payloadType = payloadType;
            return this;
        }

        public Builder setSyncTime(Instant syncTime) {
            this.syncTime = syncTime;
            return this;
        }

        public Builder setIsFullSync(boolean isFullSync) {
            this.isFullSync = isFullSync;
            return this;
        }

        public Builder setBatchIndex(int batchIndex) {
            this.batchIndex = batchIndex;
            return this;
        }

        public Builder setIsLastBatch(boolean isLastBatch) {
            this.isLastBatch = isLastBatch;
            return this;
        }

        public SyncHeader build() {
            return new SyncHeader(this);
        }
    }
}
