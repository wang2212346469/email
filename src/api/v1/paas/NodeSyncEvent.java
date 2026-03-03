package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 节点信息同步事件。
 * Node information sync event.
 *
 * <p>Topic: paas-resource-sync
 *
 * <p>Proto definition:
 * <pre>
 * message NodeSyncEvent {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   SyncHeader header = 2;
 *   repeated NodeInfo nodes = 3;
 * }
 * </pre>
 */
public final class NodeSyncEvent {

    private final MessageEnvelope envelope;
    private final SyncHeader header;
    private final List<NodeInfo> nodes;

    private NodeSyncEvent(Builder builder) {
        this.envelope = builder.envelope;
        this.header = builder.header;
        this.nodes = Collections.unmodifiableList(new ArrayList<>(builder.nodes));
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    public SyncHeader getHeader() {
        return header;
    }

    public List<NodeInfo> getNodes() {
        return nodes;
    }

    public int getNodesCount() {
        return nodes.size();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeSyncEvent)) return false;
        NodeSyncEvent that = (NodeSyncEvent) o;
        return Objects.equals(envelope, that.envelope)
                && Objects.equals(header, that.header)
                && Objects.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, header, nodes);
    }

    @Override
    public String toString() {
        return "NodeSyncEvent{"
                + "envelope=" + envelope
                + ", header=" + header
                + ", nodes=" + nodes
                + '}';
    }

    /** Builder for {@link NodeSyncEvent}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private SyncHeader header;
        private List<NodeInfo> nodes = new ArrayList<>();

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setHeader(SyncHeader header) {
            this.header = header;
            return this;
        }

        public Builder addNodes(NodeInfo node) {
            this.nodes.add(node);
            return this;
        }

        public Builder addAllNodes(List<NodeInfo> nodes) {
            this.nodes.addAll(nodes);
            return this;
        }

        public NodeSyncEvent build() {
            return new NodeSyncEvent(this);
        }
    }
}
