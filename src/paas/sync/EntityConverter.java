package paas.sync;

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
        t.setTenantId(extract(json, "tenantId", "tenant_id"));
        t.setName(extract(json, "name", "name"));
        t.setStatus(extract(json, "status", "status"));
        t.setPurpose(extract(json, "purpose", "purpose"));
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
        g.setGameId(extract(json, "gameId", "game_id"));
        g.setTenantId(extract(json, "tenantId", "tenant_id"));
        g.setName(extract(json, "name", "name"));
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
        n.setNodeId(extract(json, "nodeId", "node_id"));
        n.setName(extract(json, "name", "name"));
        n.setDataCenterId(extract(json, "dataCenterId", "data_center_id"));
        n.setIpAddress(extract(json, "ipAddress", "ip_address"));
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
        dc.setDcId(extract(json, "dcId", "dc_id"));
        dc.setName(extract(json, "name", "name"));
        dc.setRegion(extract(json, "region", "region"));
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
        m.setModelId(extract(json, "modelId", "model_id"));
        m.setName(extract(json, "name", "name"));
        m.setMemoryGb(parseInt(extract(json, "memoryGb", "memory_gb"), 0));
        m.setPowerWatts(parseInt(extract(json, "powerWatts", "power_watts"), 0));
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
        r.setServerId(extract(json, "serverId", "server_id"));
        r.setSn(extract(json, "sn", "sn"));
        r.setTenantId(extract(json, "tenantId", "tenant_id"));
        r.setNodeId(extract(json, "nodeId", "node_id"));
        r.setModelId(extract(json, "modelId", "model_id"));
        r.setStatus(extract(json, "status", "status"));
        r.setPoolId(extract(json, "poolId", "pool_id"));
        r.setIpAddress(extract(json, "ipAddress", "ip_address"));
        r.setGpuCount(parseInt(extract(json, "gpuCount", "gpu_count"), 0));
        r.setTotalMemoryGb(parseInt(extract(json, "totalMemoryGb", "total_memory_gb"), 0));
        r.setHealthStatus(extract(json, "healthStatus", "health_status"));
        r.setCreatedAt(parseLong(extract(json, "createdAt", "created_at"), 0L));
        r.setRawData(json);
        return r;
    }

    // -------------------------------------------------------------------------
    // Private helpers / 私有辅助方法
    // -------------------------------------------------------------------------

    /**
     * 从 JSON 字符串中提取指定字段的字符串值，同时支持 camelCase 和 snake_case。
     * Extracts a string field value from a JSON string supporting both camelCase and snake_case.
     */
    private static String extract(String json, String camelKey, String snakeKey) {
        String val = extractByKey(json, camelKey);
        if (val == null) {
            val = extractByKey(json, snakeKey);
        }
        return val;
    }

    /**
     * 从 JSON 字符串中按单一键名提取字段值（仅支持简单 key-value，不支持嵌套）。
     * Extracts a field value from a JSON string by a single key (simple key-value only, no nesting).
     */
    private static String extractByKey(String json, String key) {
        if (json == null || key == null) return null;
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) return null;
        int colonIdx = json.indexOf(':', idx + pattern.length());
        if (colonIdx < 0) return null;
        int start = colonIdx + 1;
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }
        if (start >= json.length()) return null;
        if (json.charAt(start) == '"') {
            int end = json.indexOf('"', start + 1);
            if (end < 0) return null;
            return json.substring(start + 1, end);
        } else {
            int end = start;
            while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') {
                end++;
            }
            return json.substring(start, end).trim();
        }
    }

    private static int parseInt(String val, int defaultVal) {
        if (val == null || val.isEmpty()) return defaultVal;
        try { return Integer.parseInt(val.trim()); } catch (NumberFormatException e) { return defaultVal; }
    }

    private static long parseLong(String val, long defaultVal) {
        if (val == null || val.isEmpty()) return defaultVal;
        try { return Long.parseLong(val.trim()); } catch (NumberFormatException e) { return defaultVal; }
    }
}
