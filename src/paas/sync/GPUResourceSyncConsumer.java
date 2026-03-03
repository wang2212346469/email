package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.GPUResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GPU 物理资源实体同步消费者，处理 PaaS 同步消息中的 GPU_RESOURCE 类型。
 * 同步完成后会触发资源池自动匹配逻辑。
 * GPU physical resource entity sync consumer handling GPU_RESOURCE type messages.
 * After sync, it triggers resource pool auto-matching logic.
 */
public class GPUResourceSyncConsumer extends EntitySyncConsumer {

    /** 已同步的 GPU 资源映射（serverId → GPUResource），保持插入顺序。 Map of synced GPU resources (serverId → GPUResource), preserving insertion order. */
    private final Map<String, GPUResource> syncedResources = new LinkedHashMap<>();

    /** 资源池匹配器，负责按租户 ID 匹配资源池。 Pool matcher for tenant-based pool assignment. */
    private final ResourcePoolMatcher poolMatcher;

    /**
     * 构造函数，注入资源池匹配器。
     * Constructor injecting the resource pool matcher.
     *
     * @param poolMatcher 资源池匹配器 / resource pool matcher
     */
    public GPUResourceSyncConsumer(ResourcePoolMatcher poolMatcher) {
        this.poolMatcher = poolMatcher;
    }

    /** 使用默认（空）资源池匹配器的无参构造函数。 No-args constructor with empty pool matcher. */
    public GPUResourceSyncConsumer() {
        this.poolMatcher = new ResourcePoolMatcher();
    }

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_SYNC_FULL;
    }

    /**
     * 返回已同步的 GPU 资源列表（不可变视图）。
     * Returns the list of synced GPU resources (read-only view).
     */
    public List<GPUResource> getSyncedResources() {
        return Collections.unmodifiableList(new ArrayList<>(syncedResources.values()));
    }

    /**
     * 返回关联的资源池匹配器。 Returns the associated resource pool matcher.
     */
    public ResourcePoolMatcher getPoolMatcher() {
        return poolMatcher;
    }

    @Override
    protected void handleUpsert(String payloadJson) {
        GPUResource resource = EntityConverter.toGPUResource(payloadJson);
        if (resource == null || resource.getServerId() == null) {
            System.err.println("[GPUResourceSyncConsumer] Skipping upsert - invalid payload");
            return;
        }
        // 资源池自动匹配 / auto-match resource pool
        poolMatcher.matchPool(resource);

        syncedResources.put(resource.getServerId(), resource);
        System.out.println("[GPUResourceSyncConsumer] Upserted: " + resource);
    }

    @Override
    protected void handleDelete(String payloadJson) {
        String serverId = extractField(payloadJson, "serverId", "server_id");
        if (serverId == null) return;
        syncedResources.remove(serverId);
        System.out.println("[GPUResourceSyncConsumer] Deleted serverId=" + serverId);
    }
}
