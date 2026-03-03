package api.v1.common;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 消息信封（所有 Kafka 消息的公共头部）。
 * Message envelope serving as the common header for all Kafka messages.
 *
 * <p>Proto definition:
 * <pre>
 * message MessageEnvelope {
 *   string event_id = 1;
 *   string event_type = 2;
 *   string producer = 3;
 *   string version = 4;
 *   google.protobuf.Timestamp created_at = 5;
 *   map&lt;string, string&gt; metadata = 6;
 * }
 * </pre>
 */
public final class MessageEnvelope {

    private final String eventId;
    private final String eventType;
    private final String producer;
    private final String version;
    private final Instant createdAt;
    private final Map<String, String> metadata;

    private MessageEnvelope(Builder builder) {
        this.eventId = builder.eventId;
        this.eventType = builder.eventType;
        this.producer = builder.producer;
        this.version = builder.version;
        this.createdAt = builder.createdAt;
        this.metadata = Collections.unmodifiableMap(new HashMap<>(builder.metadata));
    }

    /** 返回全局唯一事件 ID。 */
    public String getEventId() {
        return eventId;
    }

    /** 返回事件类型（如 "paas.resource.sync"）。 */
    public String getEventType() {
        return eventType;
    }

    /** 返回生产者标识。 */
    public String getProducer() {
        return producer;
    }

    /** 返回消息版本。 */
    public String getVersion() {
        return version;
    }

    /** 返回消息创建时间。 */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /** 返回扩展元数据（不可变视图）。 */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageEnvelope)) return false;
        MessageEnvelope that = (MessageEnvelope) o;
        return Objects.equals(eventId, that.eventId)
                && Objects.equals(eventType, that.eventType)
                && Objects.equals(producer, that.producer)
                && Objects.equals(version, that.version)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventType, producer, version, createdAt, metadata);
    }

    @Override
    public String toString() {
        return "MessageEnvelope{"
                + "eventId='" + eventId + '\''
                + ", eventType='" + eventType + '\''
                + ", producer='" + producer + '\''
                + ", version='" + version + '\''
                + ", createdAt=" + createdAt
                + ", metadata=" + metadata
                + '}';
    }

    /** Builder for {@link MessageEnvelope}. */
    public static final class Builder {
        private String eventId = "";
        private String eventType = "";
        private String producer = "";
        private String version = "";
        private Instant createdAt;
        private Map<String, String> metadata = new HashMap<>();

        private Builder() {}

        public Builder setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder setEventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder setProducer(String producer) {
            this.producer = producer;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder putMetadata(String key, String value) {
            this.metadata.put(key, value);
            return this;
        }

        public Builder putAllMetadata(Map<String, String> metadata) {
            this.metadata.putAll(metadata);
            return this;
        }

        public MessageEnvelope build() {
            return new MessageEnvelope(this);
        }
    }
}
