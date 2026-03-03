package api.v1.paas;

import api.v1.common.PaasResourceStatus;
import api.v1.common.ReserveStatus;

import java.util.Objects;

/**
 * 单台服务器预留结果。
 * Reservation result for a single server.
 *
 * <p>Proto definition:
 * <pre>
 * message ServerReserveResult {
 *   string server_id = 1;
 *   string sn = 2;
 *   api.v1.common.ReserveStatus reserve_status = 3;
 *   api.v1.common.PaasResourceStatus current_status = 4;
 *   int32 active_sessions = 5;
 *   string message = 6;
 * }
 * </pre>
 */
public final class ServerReserveResult {

    private final String serverId;
    private final String sn;
    private final ReserveStatus reserveStatus;
    private final PaasResourceStatus currentStatus;
    private final int activeSessions;
    private final String message;

    private ServerReserveResult(Builder builder) {
        this.serverId = builder.serverId;
        this.sn = builder.sn;
        this.reserveStatus = builder.reserveStatus;
        this.currentStatus = builder.currentStatus;
        this.activeSessions = builder.activeSessions;
        this.message = builder.message;
    }

    public String getServerId() {
        return serverId;
    }

    /** 返回设备 SN（兼容字段）。 */
    public String getSn() {
        return sn;
    }

    /** 返回预留结果。 */
    public ReserveStatus getReserveStatus() {
        return reserveStatus;
    }

    /** 返回服务器当前实际状态。 */
    public PaasResourceStatus getCurrentStatus() {
        return currentStatus;
    }

    /** 返回当前活跃会话数（用于决定是否需排空）。 */
    public int getActiveSessions() {
        return activeSessions;
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
        if (!(o instanceof ServerReserveResult)) return false;
        ServerReserveResult that = (ServerReserveResult) o;
        return activeSessions == that.activeSessions
                && Objects.equals(serverId, that.serverId)
                && Objects.equals(sn, that.sn)
                && reserveStatus == that.reserveStatus
                && currentStatus == that.currentStatus
                && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, sn, reserveStatus, currentStatus, activeSessions, message);
    }

    @Override
    public String toString() {
        return "ServerReserveResult{"
                + "serverId='" + serverId + '\''
                + ", sn='" + sn + '\''
                + ", reserveStatus=" + reserveStatus
                + ", currentStatus=" + currentStatus
                + ", activeSessions=" + activeSessions
                + ", message='" + message + '\''
                + '}';
    }

    /** Builder for {@link ServerReserveResult}. */
    public static final class Builder {
        private String serverId = "";
        private String sn = "";
        private ReserveStatus reserveStatus = ReserveStatus.RESERVE_STATUS_UNSPECIFIED;
        private PaasResourceStatus currentStatus = PaasResourceStatus.PAAS_RESOURCE_STATUS_UNSPECIFIED;
        private int activeSessions;
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

        public Builder setReserveStatus(ReserveStatus reserveStatus) {
            this.reserveStatus = reserveStatus;
            return this;
        }

        public Builder setCurrentStatus(PaasResourceStatus currentStatus) {
            this.currentStatus = currentStatus;
            return this;
        }

        public Builder setActiveSessions(int activeSessions) {
            this.activeSessions = activeSessions;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ServerReserveResult build() {
            return new ServerReserveResult(this);
        }
    }
}
