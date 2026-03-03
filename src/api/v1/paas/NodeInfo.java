package api.v1.paas;

import java.time.Instant;
import java.util.Objects;

/**
 * 节点信息。
 * Node information.
 *
 * <p>Proto definition:
 * <pre>
 * message NodeInfo {
 *   string node_id = 1;
 *   string node_name = 2;
 *   google.protobuf.Timestamp create_time = 3;
 *   google.protobuf.Timestamp update_time = 4;
 *   google.protobuf.Timestamp delete_time = 5;
 * }
 * </pre>
 */
public final class NodeInfo {

    private final String nodeId;
    private final String nodeName;
    private final Instant createTime;
    private final Instant updateTime;
    private final Instant deleteTime;

    private NodeInfo(Builder builder) {
        this.nodeId = builder.nodeId;
        this.nodeName = builder.nodeName;
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
        this.deleteTime = builder.deleteTime;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    /** 返回软删除时间。 */
    public Instant getDeleteTime() {
        return deleteTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInfo)) return false;
        NodeInfo that = (NodeInfo) o;
        return Objects.equals(nodeId, that.nodeId)
                && Objects.equals(nodeName, that.nodeName)
                && Objects.equals(createTime, that.createTime)
                && Objects.equals(updateTime, that.updateTime)
                && Objects.equals(deleteTime, that.deleteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, nodeName, createTime, updateTime, deleteTime);
    }

    @Override
    public String toString() {
        return "NodeInfo{"
                + "nodeId='" + nodeId + '\''
                + ", nodeName='" + nodeName + '\''
                + ", createTime=" + createTime
                + ", updateTime=" + updateTime
                + ", deleteTime=" + deleteTime
                + '}';
    }

    /** Builder for {@link NodeInfo}. */
    public static final class Builder {
        private String nodeId = "";
        private String nodeName = "";
        private Instant createTime;
        private Instant updateTime;
        private Instant deleteTime;

        private Builder() {}

        public Builder setNodeId(String nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        public Builder setNodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public Builder setCreateTime(Instant createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder setUpdateTime(Instant updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder setDeleteTime(Instant deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }

        public NodeInfo build() {
            return new NodeInfo(this);
        }
    }
}
