package paas;

import paas.kafka.DrainRequest;
import paas.kafka.HealthStatus;
import paas.kafka.ReserveRequest;
import paas.kafka.ScheduleEventNotification;
import paas.load.TenantLoadConsumer;
import paas.schedule.DrainProducer;
import paas.schedule.DrainResponseConsumer;
import paas.schedule.ReserveProducer;
import paas.schedule.ReserveResponseConsumer;
import paas.schedule.ScheduleEventProducer;
import paas.status.ResourceStatusConsumer;
import paas.sync.DataCenterSyncConsumer;
import paas.sync.GPUModelSyncConsumer;
import paas.sync.GPUResourceSyncConsumer;
import paas.sync.GameSyncConsumer;
import paas.sync.NodeSyncConsumer;
import paas.sync.ResourcePoolMatcher;
import paas.sync.TenantSyncConsumer;

/**
 * PaaS 集成模块门面（Facade），统一封装所有与 PaaS 平台交互的能力：
 * <ul>
 *   <li>实体数据同步（6 类实体，全量/增量）</li>
 *   <li>调度操作协调（预留、腾挪、调度事件）</li>
 *   <li>负载监控与上报（健康状态、租户负载）</li>
 * </ul>
 *
 * <p>Facade for the PaaS Integration Module, providing unified access to all
 * capabilities for interacting with the PaaS platform:</p>
 * <ul>
 *   <li>Entity data synchronization (6 entity types, full/incremental)</li>
 *   <li>Scheduling operation coordination (reserve, drain, schedule events)</li>
 *   <li>Load monitoring and reporting (health status, tenant load)</li>
 * </ul>
 *
 * <p>使用示例 / Usage example:</p>
 * <pre>{@code
 * PaasIntegrationModule paas = new PaasIntegrationModule();
 * paas.syncEntity("paas-resource-sync-full", "srv-001", payload);
 * HealthStatus health = paas.getResourceHealth("srv-001");
 * }</pre>
 */
public class PaasIntegrationModule {

    // ---- Entity sync consumers / 实体同步消费者 ----
    private final TenantSyncConsumer       tenantSyncConsumer;
    private final GameSyncConsumer         gameSyncConsumer;
    private final NodeSyncConsumer         nodeSyncConsumer;
    private final DataCenterSyncConsumer   dataCenterSyncConsumer;
    private final GPUModelSyncConsumer     gpuModelSyncConsumer;
    private final GPUResourceSyncConsumer  gpuResourceSyncConsumer;

    // ---- Status monitoring / 状态监控 ----
    private final ResourceStatusConsumer   resourceStatusConsumer;

    // ---- Load reporting / 负载上报 ----
    private final TenantLoadConsumer       tenantLoadConsumer;

    // ---- Scheduling producers / 调度生产者 ----
    private final ReserveProducer          reserveProducer;
    private final DrainProducer            drainProducer;
    private final ScheduleEventProducer    scheduleEventProducer;

    // ---- Response consumers / 响应消费者 ----
    private final ReserveResponseConsumer  reserveResponseConsumer;
    private final DrainResponseConsumer    drainResponseConsumer;

    /**
     * 无参构造函数，使用默认配置初始化所有组件。
     * No-args constructor that initializes all components with default configuration.
     */
    public PaasIntegrationModule() {
        ResourcePoolMatcher poolMatcher = new ResourcePoolMatcher();

        this.tenantSyncConsumer       = new TenantSyncConsumer();
        this.gameSyncConsumer         = new GameSyncConsumer();
        this.nodeSyncConsumer         = new NodeSyncConsumer();
        this.dataCenterSyncConsumer   = new DataCenterSyncConsumer();
        this.gpuModelSyncConsumer     = new GPUModelSyncConsumer();
        this.gpuResourceSyncConsumer  = new GPUResourceSyncConsumer(poolMatcher);

        this.resourceStatusConsumer   = new ResourceStatusConsumer();
        this.tenantLoadConsumer       = new TenantLoadConsumer();

        this.reserveProducer          = new ReserveProducer();
        this.drainProducer            = new DrainProducer();
        this.scheduleEventProducer    = new ScheduleEventProducer();

        this.reserveResponseConsumer  = new ReserveResponseConsumer();
        this.drainResponseConsumer    = new DrainResponseConsumer();
    }

    // =========================================================================
    // Entity sync dispatch / 实体同步分发
    // =========================================================================

    /**
     * 将来自全量或增量同步主题的消息分发给所有实体同步消费者。
     * 每个消费者会内部判断消息头中的 payloadType 决定是否处理。
     *
     * Dispatches a message from full or incremental sync topics to all entity sync consumers.
     * Each consumer inspects the payloadType header to decide whether to process it.
     *
     * @param topic   消息来源主题 / source topic
     * @param key     消息键 / message key
     * @param payload 消息体（含 SyncHeader 和 data 字段）/ message payload (with SyncHeader and data)
     */
    public void syncEntity(String topic, String key, String payload) {
        dispatchToConsumer(tenantSyncConsumer,      topic, key, payload);
        dispatchToConsumer(gameSyncConsumer,         topic, key, payload);
        dispatchToConsumer(nodeSyncConsumer,         topic, key, payload);
        dispatchToConsumer(dataCenterSyncConsumer,   topic, key, payload);
        dispatchToConsumer(gpuModelSyncConsumer,     topic, key, payload);
        dispatchToConsumer(gpuResourceSyncConsumer,  topic, key, payload);
    }

    // =========================================================================
    // Status & load dispatch / 状态与负载分发
    // =========================================================================

    /**
     * 处理来自 paas-resource-status 主题的健康状态消息。
     * Handles a health status message from the paas-resource-status topic.
     *
     * @param key     消息键（serverId）/ message key (serverId)
     * @param payload 消息体 / message payload
     */
    public void reportStatus(String key, String payload) {
        dispatchToConsumer(resourceStatusConsumer, paas.kafka.KafkaTopics.PAAS_RESOURCE_STATUS, key, payload);
    }

    /**
     * 处理来自 paas-tenant-load 主题的租户负载消息。
     * Handles a tenant load message from the paas-tenant-load topic.
     *
     * @param key     消息键（tenantId）/ message key (tenantId)
     * @param payload 消息体 / message payload
     */
    public void reportLoad(String key, String payload) {
        dispatchToConsumer(tenantLoadConsumer, paas.kafka.KafkaTopics.PAAS_TENANT_LOAD, key, payload);
    }

    // =========================================================================
    // Scheduling operations / 调度操作
    // =========================================================================

    /**
     * 发送批量资源预留请求到 PaaS。
     * Sends a batch resource reserve request to PaaS.
     *
     * @param req 预留请求 / reserve request
     */
    public void sendReserveRequest(ReserveRequest req) {
        reserveProducer.sendReserveRequest(req);
    }

    /**
     * 发送批量资源腾挪请求到 PaaS。
     * Sends a batch resource drain request to PaaS.
     *
     * @param req 腾挪请求 / drain request
     */
    public void sendDrainRequest(DrainRequest req) {
        drainProducer.sendDrainRequest(req);
    }

    /**
     * 向 PaaS 发送调度事件通知（LEND/RECLAIM/TRANSFER）。
     * Sends a schedule event notification (LEND/RECLAIM/TRANSFER) to PaaS.
     *
     * @param event 调度事件 / schedule event notification
     */
    public void sendScheduleEvent(ScheduleEventNotification event) {
        scheduleEventProducer.sendEvent(event);
    }

    /**
     * 处理来自 paas-reserve-response 主题的预留响应消息。
     * Handles a reserve response message from the paas-reserve-response topic.
     *
     * @param key     消息键（taskId）/ message key (taskId)
     * @param payload 消息体 / message payload
     */
    public void handleReserveResponse(String key, String payload) {
        dispatchToConsumer(reserveResponseConsumer, paas.kafka.KafkaTopics.PAAS_RESERVE_RESPONSE, key, payload);
    }

    /**
     * 处理来自 paas-drain-response 主题的腾挪响应消息。
     * Handles a drain response message from the paas-drain-response topic.
     *
     * @param key     消息键（taskId）/ message key (taskId)
     * @param payload 消息体 / message payload
     */
    public void handleDrainResponse(String key, String payload) {
        dispatchToConsumer(drainResponseConsumer, paas.kafka.KafkaTopics.PAAS_DRAIN_RESPONSE, key, payload);
    }

    // =========================================================================
    // Query methods / 查询方法
    // =========================================================================

    /**
     * 获取指定 GPU 服务器的健康状态。
     * Returns the health status of the specified GPU server.
     *
     * @param serverId 服务器 ID / server ID
     * @return 健康状态 / health status
     */
    public HealthStatus getResourceHealth(String serverId) {
        return resourceStatusConsumer.getHealthStatus(serverId);
    }

    /**
     * 获取指定租户的综合负载评分，无数据时返回 0.0。
     * Returns the overall load score for the specified tenant; 0.0 if no data.
     *
     * @param tenantId 租户 ID / tenant ID
     * @return 负载评分 / load score
     */
    public double getTenantLoadScore(String tenantId) {
        return tenantLoadConsumer.getLoadScore(tenantId);
    }

    // =========================================================================
    // Component accessors / 组件访问器
    // =========================================================================

    /** 返回租户同步消费者。 Returns the tenant sync consumer. */
    public TenantSyncConsumer getTenantSyncConsumer() { return tenantSyncConsumer; }

    /** 返回游戏同步消费者。 Returns the game sync consumer. */
    public GameSyncConsumer getGameSyncConsumer() { return gameSyncConsumer; }

    /** 返回节点同步消费者。 Returns the node sync consumer. */
    public NodeSyncConsumer getNodeSyncConsumer() { return nodeSyncConsumer; }

    /** 返回数据中心同步消费者。 Returns the data center sync consumer. */
    public DataCenterSyncConsumer getDataCenterSyncConsumer() { return dataCenterSyncConsumer; }

    /** 返回 GPU 型号同步消费者。 Returns the GPU model sync consumer. */
    public GPUModelSyncConsumer getGpuModelSyncConsumer() { return gpuModelSyncConsumer; }

    /** 返回 GPU 资源同步消费者。 Returns the GPU resource sync consumer. */
    public GPUResourceSyncConsumer getGpuResourceSyncConsumer() { return gpuResourceSyncConsumer; }

    /** 返回资源状态消费者。 Returns the resource status consumer. */
    public ResourceStatusConsumer getResourceStatusConsumer() { return resourceStatusConsumer; }

    /** 返回租户负载消费者。 Returns the tenant load consumer. */
    public TenantLoadConsumer getTenantLoadConsumer() { return tenantLoadConsumer; }

    /** 返回预留响应消费者。 Returns the reserve response consumer. */
    public ReserveResponseConsumer getReserveResponseConsumer() { return reserveResponseConsumer; }

    /** 返回腾挪响应消费者。 Returns the drain response consumer. */
    public DrainResponseConsumer getDrainResponseConsumer() { return drainResponseConsumer; }

    // =========================================================================
    // Private helpers / 私有辅助方法
    // =========================================================================

    /**
     * 将消息安全地分发给消费者，捕获并记录异常。
     * Safely dispatches a message to a consumer, catching and logging exceptions.
     */
    private void dispatchToConsumer(paas.kafka.KafkaConsumerAdapter consumer,
                                    String topic, String key, String payload) {
        try {
            consumer.handle(topic, key, payload);
        } catch (Exception e) {
            System.err.println("[PaasIntegrationModule] Error dispatching to "
                    + consumer.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
