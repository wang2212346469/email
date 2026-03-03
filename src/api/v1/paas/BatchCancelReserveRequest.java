package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 批量取消预留请求（算力调度 → PaaS）。
 * Batch cancel reservation request (GPU Scheduler → PaaS).
 * Used to release reserved resources when a task is cancelled or times out.
 *
 * <p>Topic: gpuscheduler-paas-reserve（复用 Topic，通过消息类型区分）
 *
 * <p>Proto definition:
 * <pre>
 * message BatchCancelReserveRequest {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   string request_id = 2;
 *   string task_id = 3;
 *   repeated string server_ids = 4;
 *   string reason = 5;
 *   google.protobuf.Timestamp request_time = 6;
 * }
 * </pre>
 */
public final class BatchCancelReserveRequest {

    private final MessageEnvelope envelope;
    private final String requestId;
    private final String taskId;
    private final List<String> serverIds;
    private final String reason;
    private final Instant requestTime;

    private BatchCancelReserveRequest(Builder builder) {
        this.envelope = builder.envelope;
        this.requestId = builder.requestId;
        this.taskId = builder.taskId;
        this.serverIds = Collections.unmodifiableList(new ArrayList<>(builder.serverIds));
        this.reason = builder.reason;
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

    /** 返回需取消预留的 server_ids 列表。 */
    public List<String> getServerIds() {
        return serverIds;
    }

    public int getServerIdsCount() {
        return serverIds.size();
    }

    /** 返回取消原因。 */
    public String getReason() {
        return reason;
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
        if (!(o instanceof BatchCancelReserveRequest)) return false;
        BatchCancelReserveRequest that = (BatchCancelReserveRequest) o;
        return Objects.equals(envelope, that.envelope)
                && Objects.equals(requestId, that.requestId)
                && Objects.equals(taskId, that.taskId)
                && Objects.equals(serverIds, that.serverIds)
                && Objects.equals(reason, that.reason)
                && Objects.equals(requestTime, that.requestTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, requestId, taskId, serverIds, reason, requestTime);
    }

    @Override
    public String toString() {
        return "BatchCancelReserveRequest{"
                + "envelope=" + envelope
                + ", requestId='" + requestId + '\''
                + ", taskId='" + taskId + '\''
                + ", serverIds=" + serverIds
                + ", reason='" + reason + '\''
                + ", requestTime=" + requestTime
                + '}';
    }

    /** Builder for {@link BatchCancelReserveRequest}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private String requestId = "";
        private String taskId = "";
        private List<String> serverIds = new ArrayList<>();
        private String reason = "";
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

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder setRequestTime(Instant requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public BatchCancelReserveRequest build() {
            return new BatchCancelReserveRequest(this);
        }
    }
}
