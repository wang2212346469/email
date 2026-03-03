package paas.sync;

import paas.kafka.JsonUtils;
import paas.model.DataCenter;
import paas.model.GPUModel;
import paas.model.GPUResource;
import paas.model.Game;
import paas.model.Node;
import paas.model.Tenant;

/**
 * 实体转换器（反腐层），负责将 PaaS 原始 JSON 转换为内部领域模型。
 * 使用简单字符串解析（无外部依赖），同时支持 camelCase 和 snake_case 字段名。
 *
 * Entity Converter (Anti-Corruption Layer) responsible for converting raw PaaS JSON
 * to internal domain models. Uses simple string parsing (no external dependencies),
 * supporting both camelCase and snake_case field names.
 */
public class EntityConverter {

    private EntityConverter() {}

    /**
     * 将 PaaS JSON 转换为 Tenant 实体。
     * Converts a PaaS JSON string to a Tenant entity.
     *
     * @param json PaaS 原始 JSON / raw PaaS JSON
     * @return Tenant 实体，解析失败时返回 null / Tenant entity, null on failure
     */
    public static Tenant toTenant(String json) {
        if (json == null || json.isEmpty()) return null;
        Tenant t = new Tenant();
        t.setTenantId(JsonUtils.extractField(json, "tenantId", "tenant_id"));
        t.setName(JsonUtils.extractField(json, "name", "name"));
        t.setStatus(JsonUtils.extractField(json, "status", "status"));
        t.setPurpose(JsonUtils.extractField(json, "purpose", "purpose"));
        t.setRawData(json);
        return t;
    }

    /**
     * 将 PaaS JSON 转换为 Game 实体。
     * Converts a PaaS JSON string to a Game entity.
     *
     * @param json PaaS 原始 JSON / raw PaaS JSON
     * @return Game 实体，解析失败时返回 null / Game entity, null on failure
     */
    public static Game toGame(String json) {
        if (json == null || json.isEmpty()) return null;
        Game g = new Game();
        g.setGameId(JsonUtils.extractField(json, "gameId", "game_id"));
        g.setTenantId(JsonUtils.extractField(json, "tenantId", "tenant_id"));
        g.setName(JsonUtils.extractField(json, "name", "name"));
        g.setRawData(json);
        return g;
    }

    /**
     * 将 PaaS JSON 转换为 Node 实体。
     * Converts a PaaS JSON string to a Node entity.
     *
     * @param json PaaS 原始 JSON / raw PaaS JSON
     * @return Node 实体，解析失败时返回 null / Node entity, null on failure
     */
    public static Node toNode(String json) {
        if (json == null || json.isEmpty()) return null;
        Node n = new Node();
        n.setNodeId(JsonUtils.extractField(json, "nodeId", "node_id"));
        n.setName(JsonUtils.extractField(json, "name", "name"));
        n.setDataCenterId(JsonUtils.extractField(json, "dataCenterId", "data_center_id"));
        n.setIpAddress(JsonUtils.extractField(json, "ipAddress", "ip_address"));
        n.setRawData(json);
        return n;
    }

    /**
     * 将 PaaS JSON 转换为 DataCenter 实体。
     * Converts a PaaS JSON string to a DataCenter entity.
     *
     * @param json PaaS 原始 JSON / raw PaaS JSON
     * @return DataCenter 实体，解析失败时返回 null / DataCenter entity, null on failure
     */
    public static DataCenter toDataCenter(String json) {
        if (json == null || json.isEmpty()) return null;
        DataCenter dc = new DataCenter();
        dc.setDcId(JsonUtils.extractField(json, "dcId", "dc_id"));
        dc.setName(JsonUtils.extractField(json, "name", "name"));
        dc.setRegion(JsonUtils.extractField(json, "region", "region"));
        dc.setRawData(json);
        return dc;
    }

    /**
     * 将 PaaS JSON 转换为 GPUModel 实体。
     * Converts a PaaS JSON string to a GPUModel entity.
     *
     * @param json PaaS 原始 JSON / raw PaaS JSON
     * @return GPUModel 实体，解析失败时返回 null / GPUModel entity, null on failure
     */
    public static GPUModel toGPUModel(String json) {
        if (json == null || json.isEmpty()) return null;
        GPUModel m = new GPUModel();
        m.setModelId(JsonUtils.extractField(json, "modelId", "model_id"));
        m.setName(JsonUtils.extractField(json, "name", "name"));
        m.setMemoryGb(JsonUtils.extractInt(json, "memoryGb", "memory_gb", 0));
        m.setPowerWatts(JsonUtils.extractInt(json, "powerWatts", "power_watts", 0));
        m.setRawData(json);
        return m;
    }

    /**
     * 将 PaaS JSON 转换为 GPUResource 实体。
     * Converts a PaaS JSON string to a GPUResource entity.
     *
     * @param json PaaS 原始 JSON / raw PaaS JSON
     * @return GPUResource 实体，解析失败时返回 null / GPUResource entity, null on failure
     */
    public static GPUResource toGPUResource(String json) {
        if (json == null || json.isEmpty()) return null;
        GPUResource r = new GPUResource();
        r.setServerId(JsonUtils.extractField(json, "serverId", "server_id"));
        r.setSn(JsonUtils.extractField(json, "sn", "sn"));
        r.setTenantId(JsonUtils.extractField(json, "tenantId", "tenant_id"));
        r.setNodeId(JsonUtils.extractField(json, "nodeId", "node_id"));
        r.setModelId(JsonUtils.extractField(json, "modelId", "model_id"));
        r.setStatus(JsonUtils.extractField(json, "status", "status"));
        r.setPoolId(JsonUtils.extractField(json, "poolId", "pool_id"));
        r.setIpAddress(JsonUtils.extractField(json, "ipAddress", "ip_address"));
        r.setGpuCount(JsonUtils.extractInt(json, "gpuCount", "gpu_count", 0));
        r.setTotalMemoryGb(JsonUtils.extractInt(json, "totalMemoryGb", "total_memory_gb", 0));
        r.setHealthStatus(JsonUtils.extractField(json, "healthStatus", "health_status"));
        r.setCreatedAt(JsonUtils.extractLong(json, "createdAt", "created_at", 0L));
        r.setRawData(json);
        return r;
    }

    // -------------------------------------------------------------------------
    // Private helpers / 私有辅助方法
    // -------------------------------------------------------------------------

    // All JSON parsing is delegated to JsonUtils. No private helpers needed.
}
