package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 批量预留资源请求（算力调度 → PaaS）。
 * Batch resource reservation request (GPU Scheduler → PaaS).
 *
 * <p>Topic: gpuscheduler-paas-reserve
 *
 * <p>双模式:
 * <ul>
 *   <li>手动模式: 填写 server_ids，PaaS 逐一检查并预留</li>
 *   <li>自动模式: server_ids 为空时使用 criteria，PaaS 实时选择最优服务器并原子预留</li>
 * </ul>
 *
 * <p>Proto definition:
 * <pre>
 * message BatchReserveResourceRequest {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   string request_id = 2;
 *   string task_id = 3;
 *   string tenant_id = 4;
 *   repeated string server_ids = 5;
 *   SelectionCriteria criteria = 6;
 *   string reason = 7;
 *   int32 timeout_seconds = 8;
 *   google.protobuf.Timestamp request_time = 9;
 * }
 * </pre>
 */
public final class BatchReserveResourceRequest {

    private final MessageEnvelope envelope;
    private final String requestId;
    private final String taskId;
    private final String tenantId;
    private final List<String> serverIds;
    private final SelectionCriteria criteria;
    private final String reason;
    private final int timeoutSeconds;
    private final Instant requestTime;

    private BatchReserveResourceRequest(Builder builder) {
        this.envelope = builder.envelope;
        this.requestId = builder.requestId;
        this.taskId = builder.taskId;
        this.tenantId = builder.tenantId;
        this.serverIds = Collections.unmodifiableList(new ArrayList<>(builder.serverIds));
        this.criteria = builder.criteria;
        this.reason = builder.reason;
        this.timeoutSeconds = builder.timeoutSeconds;
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

    /** 返回当前归属租户 ID。 */
    public String getTenantId() {
        return tenantId;
    }

    /** 返回手动模式指定的服务器 ID 列表，非空时优先使用，忽略 criteria。 */
    public List<String> getServerIds() {
        return serverIds;
    }

    public int getServerIdsCount() {
        return serverIds.size();
    }

    /** 返回自动模式选择条件，server_ids 为空时使用。 */
    public SelectionCriteria getCriteria() {
        return criteria;
    }

    /** 返回预留原因。 */
    public String getReason() {
        return reason;
    }

    /** 返回预留超时时间（秒），默认 300。 */
    public int getTimeoutSeconds() {
        return timeoutSeconds;
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
        if (!(o instanceof BatchReserveResourceRequest)) return false;
        BatchReserveResourceRequest that = (BatchReserveResourceRequest) o;
        return timeoutSeconds == that.timeoutSeconds
                && Objects.equals(envelope, that.envelope)
                && Objects.equals(requestId, that.requestId)
                && Objects.equals(taskId, that.taskId)
                && Objects.equals(tenantId, that.tenantId)
                && Objects.equals(serverIds, that.serverIds)
                && Objects.equals(criteria, that.criteria)
                && Objects.equals(reason, that.reason)
                && Objects.equals(requestTime, that.requestTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, requestId, taskId, tenantId, serverIds, criteria,
                reason, timeoutSeconds, requestTime);
    }

    @Override
    public String toString() {
        return "BatchReserveResourceRequest{"
                + "envelope=" + envelope
                + ", requestId='" + requestId + '\''
                + ", taskId='" + taskId + '\''
                + ", tenantId='" + tenantId + '\''
                + ", serverIds=" + serverIds
                + ", criteria=" + criteria
                + ", reason='" + reason + '\''
                + ", timeoutSeconds=" + timeoutSeconds
                + ", requestTime=" + requestTime
                + '}';
    }

    /** Builder for {@link BatchReserveResourceRequest}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private String requestId = "";
        private String taskId = "";
        private String tenantId = "";
        private List<String> serverIds = new ArrayList<>();
        private SelectionCriteria criteria;
        private String reason = "";
        private int timeoutSeconds = 300;
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

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
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

        public Builder setCriteria(SelectionCriteria criteria) {
            this.criteria = criteria;
            return this;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder setTimeoutSeconds(int timeoutSeconds) {
            this.timeoutSeconds = timeoutSeconds;
            return this;
        }

        public Builder setRequestTime(Instant requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public BatchReserveResourceRequest build() {
            return new BatchReserveResourceRequest(this);
        }
    }
}
