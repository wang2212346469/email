package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 批量排空资源请求（算力调度 → PaaS）。
 * Batch resource drain request (GPU Scheduler → PaaS).
 * Used to drain active sessions from servers after successful reservation.
 *
 * <p>Topic: gpuscheduler-paas-drain
 *
 * <p>Proto definition:
 * <pre>
 * message BatchDrainResourceRequest {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   string request_id = 2;
 *   string task_id = 3;
 *   repeated string server_ids = 4;
 *   string tenant_id = 5;
 *   bool is_forced = 6;
 *   string reason = 7;
 *   int32 drain_timeout_seconds = 8;
 *   google.protobuf.Timestamp request_time = 9;
 * }
 * </pre>
 */
public final class BatchDrainResourceRequest {

    private final MessageEnvelope envelope;
    private final String requestId;
    private final String taskId;
    private final List<String> serverIds;
    private final String tenantId;
    private final boolean isForced;
    private final String reason;
    private final int drainTimeoutSeconds;
    private final Instant requestTime;

    private BatchDrainResourceRequest(Builder builder) {
        this.envelope = builder.envelope;
        this.requestId = builder.requestId;
        this.taskId = builder.taskId;
        this.serverIds = Collections.unmodifiableList(new ArrayList<>(builder.serverIds));
        this.tenantId = builder.tenantId;
        this.isForced = builder.isForced;
        this.reason = builder.reason;
        this.drainTimeoutSeconds = builder.drainTimeoutSeconds;
        this.requestTime = builder.requestTime;
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    /** 返回请求 ID（幂等键）。 */
    public String getRequestId() {
        return requestId;
    }

    /** 返回关联调度任务 ID。 */
    public String getTaskId() {
        return taskId;
    }

    /** 返回需排空的 server_ids 列表。 */
    public List<String> getServerIds() {
        return serverIds;
    }

    public int getServerIdsCount() {
        return serverIds.size();
    }

    /** 返回当前租户 ID。 */
    public String getTenantId() {
        return tenantId;
    }

    /** 返回是否强制排空（true=立即断开会话）。 */
    public boolean isForced() {
        return isForced;
    }

    /** 返回排空原因。 */
    public String getReason() {
        return reason;
    }

    /** 返回排空超时时间（秒），超时视为失败。 */
    public int getDrainTimeoutSeconds() {
        return drainTimeoutSeconds;
    }

    public Instant getRequestTime() {
        return requestTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BatchDrainResourceRequest)) return false;
        BatchDrainResourceRequest that = (BatchDrainResourceRequest) o;
        return isForced == that.isForced
                && drainTimeoutSeconds == that.drainTimeoutSeconds
                && Objects.equals(envelope, that.envelope)
                && Objects.equals(requestId, that.requestId)
                && Objects.equals(taskId, that.taskId)
                && Objects.equals(serverIds, that.serverIds)
                && Objects.equals(tenantId, that.tenantId)
                && Objects.equals(reason, that.reason)
                && Objects.equals(requestTime, that.requestTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, requestId, taskId, serverIds, tenantId, isForced,
                reason, drainTimeoutSeconds, requestTime);
    }

    @Override
    public String toString() {
        return "BatchDrainResourceRequest{"
                + "envelope=" + envelope
                + ", requestId='" + requestId + '\''
                + ", taskId='" + taskId + '\''
                + ", serverIds=" + serverIds
                + ", tenantId='" + tenantId + '\''
                + ", isForced=" + isForced
                + ", reason='" + reason + '\''
                + ", drainTimeoutSeconds=" + drainTimeoutSeconds
                + ", requestTime=" + requestTime
                + '}';
    }

    /** Builder for {@link BatchDrainResourceRequest}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private String requestId = "";
        private String taskId = "";
        private List<String> serverIds = new ArrayList<>();
        private String tenantId = "";
        private boolean isForced;
        private String reason = "";
        private int drainTimeoutSeconds;
        private Instant requestTime;

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setRequestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder setTaskId(String taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder addServerIds(String serverId) {
            this.serverIds.add(serverId);
            return this;
        }

        public Builder addAllServerIds(List<String> serverIds) {
            this.serverIds.addAll(serverIds);
            return this;
        }

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder setIsForced(boolean isForced) {
            this.isForced = isForced;
            return this;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder setDrainTimeoutSeconds(int drainTimeoutSeconds) {
            this.drainTimeoutSeconds = drainTimeoutSeconds;
            return this;
        }

        public Builder setRequestTime(Instant requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public BatchDrainResourceRequest build() {
            return new BatchDrainResourceRequest(this);
        }
    }
}
