package api.v1.paas;

import java.util.Objects;

/**
 * 单台服务器排空结果。
 * Drain result for a single server.
 *
 * <p>Proto definition:
 * <pre>
 * message ServerDrainResult {
 *   string server_id = 1;
 *   string sn = 2;
 *   bool success = 3;
 *   int32 terminated_sessions = 4;
 *   string message = 5;
 * }
 * </pre>
 */
public final class ServerDrainResult {

    private final String serverId;
    private final String sn;
    private final boolean success;
    private final int terminatedSessions;
    private final String message;

    private ServerDrainResult(Builder builder) {
        this.serverId = builder.serverId;
        this.sn = builder.sn;
        this.success = builder.success;
        this.terminatedSessions = builder.terminatedSessions;
        this.message = builder.message;
    }

    public String getServerId() {
        return serverId;
    }

    /** 返回设备 SN（兼容字段）。 */
    public String getSn() {
        return sn;
    }

    /** 返回是否排空成功。 */
    public boolean isSuccess() {
        return success;
    }

    /** 返回终止的会话数。 */
    public int getTerminatedSessions() {
        return terminatedSessions;
    }

    /** 返回描述信息（失败原因等）。 */
    public String getMessage() {
        return message;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerDrainResult)) return false;
        ServerDrainResult that = (ServerDrainResult) o;
        return success == that.success
                && terminatedSessions == that.terminatedSessions
                && Objects.equals(serverId, that.serverId)
                && Objects.equals(sn, that.sn)
                && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, sn, success, terminatedSessions, message);
    }

    @Override
    public String toString() {
        return "ServerDrainResult{"
                + "serverId='" + serverId + '\''
                + ", sn='" + sn + '\''
                + ", success=" + success
                + ", terminatedSessions=" + terminatedSessions
                + ", message='" + message + '\''
                + '}';
    }

    /** Builder for {@link ServerDrainResult}. */
    public static final class Builder {
        private String serverId = "";
        private String sn = "";
        private boolean success;
        private int terminatedSessions;
        private String message = "";

        private Builder() {}

        public Builder setServerId(String serverId) {
            this.serverId = serverId;
            return this;
        }

        public Builder setSn(String sn) {
            this.sn = sn;
            return this;
        }

        public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder setTerminatedSessions(int terminatedSessions) {
            this.terminatedSessions = terminatedSessions;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ServerDrainResult build() {
            return new ServerDrainResult(this);
        }
    }
}
