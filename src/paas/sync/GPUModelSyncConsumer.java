package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.GPUModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GPU 型号实体同步消费者，处理 PaaS 同步消息中的 GPU_MODEL 类型。
 * GPU model entity sync consumer handling GPU_MODEL type messages from PaaS sync topics.
 */
public class GPUModelSyncConsumer extends EntitySyncConsumer {

    /** 已同步的 GPU 型号列表。 List of synced GPU models. */
    private final List<GPUModel> syncedGPUModels = new ArrayList<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_SYNC_FULL;
    }

    /**
     * 返回已同步的 GPU 型号列表（不可变视图）。
     * Returns the list of synced GPU models (read-only view).
     */
    public List<GPUModel> getSyncedGPUModels() {
        return Collections.unmodifiableList(syncedGPUModels);
    }

    @Override
    protected void handleUpsert(String payloadJson) {
        GPUModel model = EntityConverter.toGPUModel(payloadJson);
        if (model == null || model.getModelId() == null) {
            System.err.println("[GPUModelSyncConsumer] Skipping upsert - invalid payload");
            return;
        }
        syncedGPUModels.removeIf(m -> model.getModelId().equals(m.getModelId()));
        syncedGPUModels.add(model);
        System.out.println("[GPUModelSyncConsumer] Upserted: " + model);
    }

    @Override
    protected void handleDelete(String payloadJson) {
        String modelId = extractField(payloadJson, "modelId", "model_id");
        if (modelId == null) return;
        syncedGPUModels.removeIf(m -> modelId.equals(m.getModelId()));
        System.out.println("[GPUModelSyncConsumer] Deleted modelId=" + modelId);
    }
}
