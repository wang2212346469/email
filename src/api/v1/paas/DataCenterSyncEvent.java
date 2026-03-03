package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 机房信息同步事件。
 * Data center information sync event.
 *
 * <p>Topic: paas-resource-sync
 *
 * <p>Proto definition:
 * <pre>
 * message DataCenterSyncEvent {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   SyncHeader header = 2;
 *   repeated DataCenterInfo data_centers = 3;
 * }
 * </pre>
 */
public final class DataCenterSyncEvent {

    private final MessageEnvelope envelope;
    private final SyncHeader header;
    private final List<DataCenterInfo> dataCenters;

    private DataCenterSyncEvent(Builder builder) {
        this.envelope = builder.envelope;
        this.header = builder.header;
        this.dataCenters = Collections.unmodifiableList(new ArrayList<>(builder.dataCenters));
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    public SyncHeader getHeader() {
        return header;
    }

    public List<DataCenterInfo> getDataCenters() {
        return dataCenters;
    }

    public int getDataCentersCount() {
        return dataCenters.size();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataCenterSyncEvent)) return false;
        DataCenterSyncEvent that = (DataCenterSyncEvent) o;
        return Objects.equals(envelope, that.envelope)
                && Objects.equals(header, that.header)
                && Objects.equals(dataCenters, that.dataCenters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, header, dataCenters);
    }

    @Override
    public String toString() {
        return "DataCenterSyncEvent{"
                + "envelope=" + envelope
                + ", header=" + header
                + ", dataCenters=" + dataCenters
                + '}';
    }

    /** Builder for {@link DataCenterSyncEvent}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private SyncHeader header;
        private List<DataCenterInfo> dataCenters = new ArrayList<>();

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setHeader(SyncHeader header) {
            this.header = header;
            return this;
        }

        public Builder addDataCenters(DataCenterInfo dataCenter) {
            this.dataCenters.add(dataCenter);
            return this;
        }

        public Builder addAllDataCenters(List<DataCenterInfo> dataCenters) {
            this.dataCenters.addAll(dataCenters);
            return this;
        }

        public DataCenterSyncEvent build() {
            return new DataCenterSyncEvent(this);
        }
    }
}
