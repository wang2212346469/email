package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 批量排空响应（PaaS → 算力调度）。
 * Batch drain response (PaaS → GPU Scheduler).
 * Returned once all servers in the batch have completed draining (or timed out / failed).
 *
 * <p>Topic: paas-drain-response
 *
 * <p>Proto definition:
 * <pre>
 * message BatchDrainResponse {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   string request_id = 2;
 *   string task_id = 3;
 *   int32 total_requested = 4;
 *   int32 success_count = 5;
 *   int32 failed_count = 6;
 *   repeated ServerDrainResult results = 7;
 *   google.protobuf.Timestamp response_time = 8;
 * }
 * </pre>
 */
public final class BatchDrainResponse {

    private final MessageEnvelope envelope;
    private final String requestId;
    private final String taskId;
    private final int totalRequested;
    private final int successCount;
    private final int failedCount;
    private final List<ServerDrainResult> results;
    private final Instant responseTime;

    private BatchDrainResponse(Builder builder) {
        this.envelope = builder.envelope;
        this.requestId = builder.requestId;
        this.taskId = builder.taskId;
        this.totalRequested = builder.totalRequested;
        this.successCount = builder.successCount;
        this.failedCount = builder.failedCount;
        this.results = Collections.unmodifiableList(new ArrayList<>(builder.results));
        this.responseTime = builder.responseTime;
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    /** 返回关联请求 ID。 */
    public String getRequestId() {
        return requestId;
    }

    /** 返回关联调度任务 ID。 */
    public String getTaskId() {
        return taskId;
    }

    /** 返回请求的总数。 */
    public int getTotalRequested() {
        return totalRequested;
    }

    /** 返回排空成功数。 */
    public int getSuccessCount() {
        return successCount;
    }

    /** 返回排空失败数。 */
    public int getFailedCount() {
        return failedCount;
    }

    /** 返回各服务器排空结果列表。 */
    public List<ServerDrainResult> getResults() {
        return results;
    }

    public int getResultsCount() {
        return results.size();
    }

    public Instant getResponseTime() {
        return responseTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BatchDrainResponse)) return false;
        BatchDrainResponse that = (BatchDrainResponse) o;
        return totalRequested == that.totalRequested
                && successCount == that.successCount
                && failedCount == that.failedCount
                && Objects.equals(envelope, that.envelope)
                && Objects.equals(requestId, that.requestId)
                && Objects.equals(taskId, that.taskId)
                && Objects.equals(results, that.results)
                && Objects.equals(responseTime, that.responseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, requestId, taskId, totalRequested, successCount,
                failedCount, results, responseTime);
    }

    @Override
    public String toString() {
        return "BatchDrainResponse{"
                + "envelope=" + envelope
                + ", requestId='" + requestId + '\''
                + ", taskId='" + taskId + '\''
                + ", totalRequested=" + totalRequested
                + ", successCount=" + successCount
                + ", failedCount=" + failedCount
                + ", results=" + results
                + ", responseTime=" + responseTime
                + '}';
    }

    /** Builder for {@link BatchDrainResponse}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private String requestId = "";
        private String taskId = "";
        private int totalRequested;
        private int successCount;
        private int failedCount;
        private List<ServerDrainResult> results = new ArrayList<>();
        private Instant responseTime;

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

        public Builder setTotalRequested(int totalRequested) {
            this.totalRequested = totalRequested;
            return this;
        }

        public Builder setSuccessCount(int successCount) {
            this.successCount = successCount;
            return this;
        }

        public Builder setFailedCount(int failedCount) {
            this.failedCount = failedCount;
            return this;
        }

        public Builder addResults(ServerDrainResult result) {
            this.results.add(result);
            return this;
        }

        public Builder addAllResults(List<ServerDrainResult> results) {
            this.results.addAll(results);
            return this;
        }

        public Builder setResponseTime(Instant responseTime) {
            this.responseTime = responseTime;
            return this;
        }

        public BatchDrainResponse build() {
            return new BatchDrainResponse(this);
        }
    }
}
