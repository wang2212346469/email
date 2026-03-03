package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 节点实体同步消费者，处理 PaaS 同步消息中的 NODE 类型。
 * Node entity sync consumer handling NODE type messages from PaaS sync topics.
 */
public class NodeSyncConsumer extends EntitySyncConsumer {

    /** 已同步的节点列表。 List of synced nodes. */
    private final List<Node> syncedNodes = new ArrayList<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_SYNC_FULL;
    }

    /**
     * 返回已同步的节点列表（不可变视图）。
     * Returns the list of synced nodes (read-only view).
     */
    public List<Node> getSyncedNodes() {
        return Collections.unmodifiableList(syncedNodes);
    }

    @Override
    protected void handleUpsert(String payloadJson) {
        Node node = EntityConverter.toNode(payloadJson);
        if (node == null || node.getNodeId() == null) {
            System.err.println("[NodeSyncConsumer] Skipping upsert - invalid payload");
            return;
        }
        syncedNodes.removeIf(n -> node.getNodeId().equals(n.getNodeId()));
        syncedNodes.add(node);
        System.out.println("[NodeSyncConsumer] Upserted: " + node);
    }

    @Override
    protected void handleDelete(String payloadJson) {
        String nodeId = extractField(payloadJson, "nodeId", "node_id");
        if (nodeId == null) return;
        syncedNodes.removeIf(n -> nodeId.equals(n.getNodeId()));
        System.out.println("[NodeSyncConsumer] Deleted nodeId=" + nodeId);
    }
}
