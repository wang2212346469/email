package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 租户信息同步事件。
 * Tenant information sync event.
 *
 * <p>Topic: paas-resource-sync
 *
 * <p>Proto definition:
 * <pre>
 * message TenantSyncEvent {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   SyncHeader header = 2;
 *   repeated TenantInfo tenants = 3;
 * }
 * </pre>
 */
public final class TenantSyncEvent {

    private final MessageEnvelope envelope;
    private final SyncHeader header;
    private final List<TenantInfo> tenants;

    private TenantSyncEvent(Builder builder) {
        this.envelope = builder.envelope;
        this.header = builder.header;
        this.tenants = Collections.unmodifiableList(new ArrayList<>(builder.tenants));
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    public SyncHeader getHeader() {
        return header;
    }

    public List<TenantInfo> getTenants() {
        return tenants;
    }

    public int getTenantsCount() {
        return tenants.size();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantSyncEvent)) return false;
        TenantSyncEvent that = (TenantSyncEvent) o;
        return Objects.equals(envelope, that.envelope)
                && Objects.equals(header, that.header)
                && Objects.equals(tenants, that.tenants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, header, tenants);
    }

    @Override
    public String toString() {
        return "TenantSyncEvent{"
                + "envelope=" + envelope
                + ", header=" + header
                + ", tenants=" + tenants
                + '}';
    }

    /** Builder for {@link TenantSyncEvent}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private SyncHeader header;
        private List<TenantInfo> tenants = new ArrayList<>();

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setHeader(SyncHeader header) {
            this.header = header;
            return this;
        }

        public Builder addTenants(TenantInfo tenant) {
            this.tenants.add(tenant);
            return this;
        }

        public Builder addAllTenants(List<TenantInfo> tenants) {
            this.tenants.addAll(tenants);
            return this;
        }

        public TenantSyncEvent build() {
            return new TenantSyncEvent(this);
        }
    }
}
