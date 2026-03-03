package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.DataCenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据中心实体同步消费者，处理 PaaS 同步消息中的 DATA_CENTER 类型。
 * Data center entity sync consumer handling DATA_CENTER type messages from PaaS sync topics.
 */
public class DataCenterSyncConsumer extends EntitySyncConsumer {

    /** 已同步的数据中心列表。 List of synced data centers. */
    private final List<DataCenter> syncedDataCenters = new ArrayList<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_SYNC_FULL;
    }

    /**
     * 返回已同步的数据中心列表（不可变视图）。
     * Returns the list of synced data centers (read-only view).
     */
    public List<DataCenter> getSyncedDataCenters() {
        return Collections.unmodifiableList(syncedDataCenters);
    }

    @Override
    protected void handleUpsert(String payloadJson) {
        DataCenter dc = EntityConverter.toDataCenter(payloadJson);
        if (dc == null || dc.getDcId() == null) {
            System.err.println("[DataCenterSyncConsumer] Skipping upsert - invalid payload");
            return;
        }
        syncedDataCenters.removeIf(d -> dc.getDcId().equals(d.getDcId()));
        syncedDataCenters.add(dc);
        System.out.println("[DataCenterSyncConsumer] Upserted: " + dc);
    }

    @Override
    protected void handleDelete(String payloadJson) {
        String dcId = extractField(payloadJson, "dcId", "dc_id");
        if (dcId == null) return;
        syncedDataCenters.removeIf(d -> dcId.equals(d.getDcId()));
        System.out.println("[DataCenterSyncConsumer] Deleted dcId=" + dcId);
    }
}
