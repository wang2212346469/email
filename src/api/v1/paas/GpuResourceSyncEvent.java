package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * GPU 资源同步事件（主要的高频数据）。
 * GPU resource sync event (primary high-frequency data).
 *
 * <p>Topic: paas-resource-sync
 *
 * <p>Proto definition:
 * <pre>
 * message GpuResourceSyncEvent {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   SyncHeader header = 2;
 *   repeated GpuResourceInfo resources = 3;
 * }
 * </pre>
 */
public final class GpuResourceSyncEvent {

    private final MessageEnvelope envelope;
    private final SyncHeader header;
    private final List<GpuResourceInfo> resources;

    private GpuResourceSyncEvent(Builder builder) {
        this.envelope = builder.envelope;
        this.header = builder.header;
        this.resources = Collections.unmodifiableList(new ArrayList<>(builder.resources));
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    public SyncHeader getHeader() {
        return header;
    }

    public List<GpuResourceInfo> getResources() {
        return resources;
    }

    public int getResourcesCount() {
        return resources.size();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GpuResourceSyncEvent)) return false;
        GpuResourceSyncEvent that = (GpuResourceSyncEvent) o;
        return Objects.equals(envelope, that.envelope)
                && Objects.equals(header, that.header)
                && Objects.equals(resources, that.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, header, resources);
    }

    @Override
    public String toString() {
        return "GpuResourceSyncEvent{"
                + "envelope=" + envelope
                + ", header=" + header
                + ", resources=" + resources
                + '}';
    }

    /** Builder for {@link GpuResourceSyncEvent}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private SyncHeader header;
        private List<GpuResourceInfo> resources = new ArrayList<>();

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setHeader(SyncHeader header) {
            this.header = header;
            return this;
        }

        public Builder addResources(GpuResourceInfo resource) {
            this.resources.add(resource);
            return this;
        }

        public Builder addAllResources(List<GpuResourceInfo> resources) {
            this.resources.addAll(resources);
            return this;
        }

        public GpuResourceSyncEvent build() {
            return new GpuResourceSyncEvent(this);
        }
    }
}
