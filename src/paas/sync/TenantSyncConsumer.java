package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.Tenant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 租户实体同步消费者，处理 PaaS 全量/增量同步中的 TENANT 类型消息。
 * Tenant entity sync consumer handling TENANT type messages from PaaS full/incremental sync.
 */
public class TenantSyncConsumer extends EntitySyncConsumer {

    /** 已同步的租户映射（tenantId → Tenant），保持插入顺序。 Map of synced tenants (tenantId → Tenant), preserving insertion order. */
    private final Map<String, Tenant> syncedTenants = new LinkedHashMap<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_SYNC_FULL;
    }

    /**
     * 返回已同步的租户列表（不可变视图）。
     * Returns the list of synced tenants (read-only view).
     *
     * @return 租户列表 / tenant list
     */
    public List<Tenant> getSyncedTenants() {
        return Collections.unmodifiableList(new ArrayList<>(syncedTenants.values()));
    }

    /**
     * 处理 UPSERT 事件：从 JSON 解析租户实体，更新列表。
     * Handles UPSERT events: parses tenant from JSON and updates the list.
     */
    @Override
    protected void handleUpsert(String payloadJson) {
        Tenant tenant = EntityConverter.toTenant(payloadJson);
        if (tenant == null || tenant.getTenantId() == null) {
            System.err.println("[TenantSyncConsumer] Skipping upsert - invalid payload");
            return;
        }
        syncedTenants.put(tenant.getTenantId(), tenant);
        System.out.println("[TenantSyncConsumer] Upserted: " + tenant);
    }

    /**
     * 处理 DELETE 事件：从映射中移除对应租户。
     * Handles DELETE events: removes the corresponding tenant from the map.
     */
    @Override
    protected void handleDelete(String payloadJson) {
        String tenantId = extractField(payloadJson, "tenantId", "tenant_id");
        if (tenantId == null) return;
        syncedTenants.remove(tenantId);
        System.out.println("[TenantSyncConsumer] Deleted tenantId=" + tenantId);
    }
}
