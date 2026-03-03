package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.GPUModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GPU 型号实体同步消费者，处理 PaaS 同步消息中的 GPU_MODEL 类型。
 * GPU model entity sync consumer handling GPU_MODEL type messages from PaaS sync topics.
 */
public class GPUModelSyncConsumer extends EntitySyncConsumer {

    /** 已同步的 GPU 型号映射（modelId → GPUModel），保持插入顺序。 Map of synced GPU models (modelId → GPUModel), preserving insertion order. */
    private final Map<String, GPUModel> syncedGPUModels = new LinkedHashMap<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_SYNC_FULL;
    }

    /**
     * 返回已同步的 GPU 型号列表（不可变视图）。
     * Returns the list of synced GPU models (read-only view).
     */
    public List<GPUModel> getSyncedGPUModels() {
        return Collections.unmodifiableList(new ArrayList<>(syncedGPUModels.values()));
    }

    @Override
    protected void handleUpsert(String payloadJson) {
        GPUModel model = EntityConverter.toGPUModel(payloadJson);
        if (model == null || model.getModelId() == null) {
            System.err.println("[GPUModelSyncConsumer] Skipping upsert - invalid payload");
            return;
        }
        syncedGPUModels.put(model.getModelId(), model);
        System.out.println("[GPUModelSyncConsumer] Upserted: " + model);
    }

    @Override
    protected void handleDelete(String payloadJson) {
        String modelId = extractField(payloadJson, "modelId", "model_id");
        if (modelId == null) return;
        syncedGPUModels.remove(modelId);
        System.out.println("[GPUModelSyncConsumer] Deleted modelId=" + modelId);
    }
}
