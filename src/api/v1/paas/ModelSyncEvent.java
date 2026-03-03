package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 机型信息同步事件。
 * Machine model information sync event.
 *
 * <p>Topic: paas-resource-sync
 *
 * <p>Proto definition:
 * <pre>
 * message ModelSyncEvent {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   SyncHeader header = 2;
 *   repeated ModelInfo models = 3;
 * }
 * </pre>
 */
public final class ModelSyncEvent {

    private final MessageEnvelope envelope;
    private final SyncHeader header;
    private final List<ModelInfo> models;

    private ModelSyncEvent(Builder builder) {
        this.envelope = builder.envelope;
        this.header = builder.header;
        this.models = Collections.unmodifiableList(new ArrayList<>(builder.models));
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    public SyncHeader getHeader() {
        return header;
    }

    public List<ModelInfo> getModels() {
        return models;
    }

    public int getModelsCount() {
        return models.size();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelSyncEvent)) return false;
        ModelSyncEvent that = (ModelSyncEvent) o;
        return Objects.equals(envelope, that.envelope)
                && Objects.equals(header, that.header)
                && Objects.equals(models, that.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, header, models);
    }

    @Override
    public String toString() {
        return "ModelSyncEvent{"
                + "envelope=" + envelope
                + ", header=" + header
                + ", models=" + models
                + '}';
    }

    /** Builder for {@link ModelSyncEvent}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private SyncHeader header;
        private List<ModelInfo> models = new ArrayList<>();

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setHeader(SyncHeader header) {
            this.header = header;
            return this;
        }

        public Builder addModels(ModelInfo model) {
            this.models.add(model);
            return this;
        }

        public Builder addAllModels(List<ModelInfo> models) {
            this.models.addAll(models);
            return this;
        }

        public ModelSyncEvent build() {
            return new ModelSyncEvent(this);
        }
    }
}
