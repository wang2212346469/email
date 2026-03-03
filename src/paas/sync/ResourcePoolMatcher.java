package paas.sync;

import paas.model.GPUResource;

import java.util.HashMap;
import java.util.Map;

/**
 * 资源池匹配器，维护租户与资源池 ID 之间的映射关系，
 * 并在 GPU 资源同步完成后自动为资源设置所属资源池。
 *
 * Resource pool matcher that maintains tenant-to-pool-ID mappings
 * and automatically assigns resource pool IDs to GPU resources after sync.
 */
public class ResourcePoolMatcher {

    /**
     * 租户 ID → 资源池 ID 的映射表。
     * Map from tenant ID to resource pool ID.
     */
    private final Map<String, String> tenantToPoolMapping = new HashMap<>();

    /**
     * 注册租户与资源池的对应关系。
     * Registers the mapping from a source tenant to a resource pool.
     *
     * @param sourceTenantId 租户 ID / tenant ID
     * @param poolId         资源池 ID / resource pool ID
     */
    public void registerPool(String sourceTenantId, String poolId) {
        if (sourceTenantId != null && poolId != null) {
            tenantToPoolMapping.put(sourceTenantId, poolId);
        }
    }

    /**
     * 根据 GPU 资源的租户 ID 查找并设置对应的资源池 ID。
     * 若找不到映射则资源的 poolId 保持不变。
     * Looks up and sets the pool ID for a GPU resource based on its tenant ID.
     * If no mapping is found, the resource's poolId remains unchanged.
     *
     * @param resource 待匹配资源池的 GPU 资源 / GPU resource to match pool for
     */
    public void matchPool(GPUResource resource) {
        if (resource == null) return;
        String tenantId = resource.getTenantId();
        if (tenantId == null) return;
        String poolId = tenantToPoolMapping.get(tenantId);
        if (poolId != null) {
            resource.setPoolId(poolId);
        }
    }

    /**
     * 返回当前映射表的快照（不可修改）。
     * Returns an unmodifiable snapshot of the current mapping.
     *
     * @return 租户 → 资源池映射 / tenant-to-pool mapping
     */
    public Map<String, String> getMappingSnapshot() {
        return java.util.Collections.unmodifiableMap(tenantToPoolMapping);
    }
}
