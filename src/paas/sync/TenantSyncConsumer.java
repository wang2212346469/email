package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.Tenant;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户实体同步消费者，处理 PaaS 全量/增量同步中的 TENANT 类型消息。
 * Tenant entity sync consumer handling TENANT type messages from PaaS full/incremental sync.
 */
public class TenantSyncConsumer extends EntitySyncConsumer {

    /** 已同步的租户列表。 List of synced tenants. */
    private final List<Tenant> syncedTenants = new ArrayList<>();

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
        return java.util.Collections.unmodifiableList(syncedTenants);
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
        // Remove existing entry with same ID then re-add (upsert semantics)
        syncedTenants.removeIf(t -> tenant.getTenantId().equals(t.getTenantId()));
        syncedTenants.add(tenant);
        System.out.println("[TenantSyncConsumer] Upserted: " + tenant);
    }

    /**
     * 处理 DELETE 事件：从列表中移除对应租户。
     * Handles DELETE events: removes the corresponding tenant from the list.
     */
    @Override
    protected void handleDelete(String payloadJson) {
        String tenantId = extractField(payloadJson, "tenantId", "tenant_id");
        if (tenantId == null) return;
        syncedTenants.removeIf(t -> tenantId.equals(t.getTenantId()));
        System.out.println("[TenantSyncConsumer] Deleted tenantId=" + tenantId);
    }
}
