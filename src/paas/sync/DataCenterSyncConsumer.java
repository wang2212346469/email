package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.DataCenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据中心实体同步消费者，处理 PaaS 同步消息中的 DATA_CENTER 类型。
 * Data center entity sync consumer handling DATA_CENTER type messages from PaaS sync topics.
 */
public class DataCenterSyncConsumer extends EntitySyncConsumer {

    /** 已同步的数据中心映射（dcId → DataCenter），保持插入顺序。 Map of synced data centers (dcId → DataCenter), preserving insertion order. */
    private final Map<String, DataCenter> syncedDataCenters = new LinkedHashMap<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_SYNC_FULL;
    }

    /**
     * 返回已同步的数据中心列表（不可变视图）。
     * Returns the list of synced data centers (read-only view).
     */
    public List<DataCenter> getSyncedDataCenters() {
        return Collections.unmodifiableList(new ArrayList<>(syncedDataCenters.values()));
    }

    @Override
    protected void handleUpsert(String payloadJson) {
        DataCenter dc = EntityConverter.toDataCenter(payloadJson);
        if (dc == null || dc.getDcId() == null) {
            System.err.println("[DataCenterSyncConsumer] Skipping upsert - invalid payload");
            return;
        }
        syncedDataCenters.put(dc.getDcId(), dc);
        System.out.println("[DataCenterSyncConsumer] Upserted: " + dc);
    }

    @Override
    protected void handleDelete(String payloadJson) {
        String dcId = extractField(payloadJson, "dcId", "dc_id");
        if (dcId == null) return;
        syncedDataCenters.remove(dcId);
        System.out.println("[DataCenterSyncConsumer] Deleted dcId=" + dcId);
    }
}
