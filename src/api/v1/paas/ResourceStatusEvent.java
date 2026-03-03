package api.v1.paas;

import api.v1.common.DisableReason;
import api.v1.common.MessageEnvelope;
import api.v1.common.PaasResourceStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * 资源状态事件，仅用于故障和状态变更事件。
 * Resource status event, used only for fault and status change events.
 *
 * <p>Topic: paas-resource-status
 *
 * <p>Proto definition:
 * <pre>
 * message ResourceStatusEvent {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   string event_id = 2;
 *   string server_id = 3;
 *   string sn = 4;
 *   string tenant_id = 5;
 *   EventType event_type = 6;
 *   api.v1.common.PaasResourceStatus status = 7;
 *   string message = 8;
 *   string fault_code = 9;
 *   api.v1.common.DisableReason disable_reason = 10;
 *   google.protobuf.Timestamp event_time = 11;
 * }
 * </pre>
 */
public final class ResourceStatusEvent {

    /**
     * 资源状态事件类型。
     * Event types for resource status events.
     */
    public enum EventType {
        EVENT_TYPE_UNSPECIFIED(0),
        /** 故障事件 */
        EVENT_TYPE_FAULT(1),
        /** 状态变更事件 */
        EVENT_TYPE_STATUS_CHANGE(2);

        private final int number;

        EventType(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        public static EventType forNumber(int number) {
            for (EventType value : values()) {
                if (value.number == number) {
                    return value;
                }
            }
            return EVENT_TYPE_UNSPECIFIED;
        }
    }

    private final MessageEnvelope envelope;
    private final String eventId;
    private final String serverId;
    private final String sn;
    private final String tenantId;
    private final EventType eventType;
    private final PaasResourceStatus status;
    private final String message;
    private final String faultCode;
    private final DisableReason disableReason;
    private final Instant eventTime;

    private ResourceStatusEvent(Builder builder) {
        this.envelope = builder.envelope;
        this.eventId = builder.eventId;
        this.serverId = builder.serverId;
        this.sn = builder.sn;
        this.tenantId = builder.tenantId;
        this.eventType = builder.eventType;
        this.status = builder.status;
        this.message = builder.message;
        this.faultCode = builder.faultCode;
        this.disableReason = builder.disableReason;
        this.eventTime = builder.eventTime;
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    public String getEventId() {
        return eventId;
    }

    /** 返回 GPU serverId（系统主键）。 */
    public String getServerId() {
        return serverId;
    }

    /** 返回设备 SN（兼容字段）。 */
    public String getSn() {
        return sn;
    }

    public String getTenantId() {
        return tenantId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public PaasResourceStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public DisableReason getDisableReason() {
        return disableReason;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceStatusEvent)) return false;
        ResourceStatusEvent that = (ResourceStatusEvent) o;
        return Objects.equals(envelope, that.envelope)
                && Objects.equals(eventId, that.eventId)
                && Objects.equals(serverId, that.serverId)
                && Objects.equals(sn, that.sn)
                && Objects.equals(tenantId, that.tenantId)
                && eventType == that.eventType
                && status == that.status
                && Objects.equals(message, that.message)
                && Objects.equals(faultCode, that.faultCode)
                && disableReason == that.disableReason
                && Objects.equals(eventTime, that.eventTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, eventId, serverId, sn, tenantId, eventType, status,
                message, faultCode, disableReason, eventTime);
    }

    @Override
    public String toString() {
        return "ResourceStatusEvent{"
                + "envelope=" + envelope
                + ", eventId='" + eventId + '\''
                + ", serverId='" + serverId + '\''
                + ", sn='" + sn + '\''
                + ", tenantId='" + tenantId + '\''
                + ", eventType=" + eventType
                + ", status=" + status
                + ", message='" + message + '\''
                + ", faultCode='" + faultCode + '\''
                + ", disableReason=" + disableReason
                + ", eventTime=" + eventTime
                + '}';
    }

    /** Builder for {@link ResourceStatusEvent}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private String eventId = "";
        private String serverId = "";
        private String sn = "";
        private String tenantId = "";
        private EventType eventType = EventType.EVENT_TYPE_UNSPECIFIED;
        private PaasResourceStatus status = PaasResourceStatus.PAAS_RESOURCE_STATUS_UNSPECIFIED;
        private String message = "";
        private String faultCode = "";
        private DisableReason disableReason = DisableReason.DISABLE_REASON_UNSPECIFIED;
        private Instant eventTime;

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder setServerId(String serverId) {
            this.serverId = serverId;
            return this;
        }

        public Builder setSn(String sn) {
            this.sn = sn;
            return this;
        }

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder setEventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder setStatus(PaasResourceStatus status) {
            this.status = status;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setFaultCode(String faultCode) {
            this.faultCode = faultCode;
            return this;
        }

        public Builder setDisableReason(DisableReason disableReason) {
            this.disableReason = disableReason;
            return this;
        }

        public Builder setEventTime(Instant eventTime) {
            this.eventTime = eventTime;
            return this;
        }

        public ResourceStatusEvent build() {
            return new ResourceStatusEvent(this);
        }
    }
}
