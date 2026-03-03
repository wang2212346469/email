package api.v1.paas;

/**
 * 同步载荷类型枚举，标识本批次携带的数据类型。
 * Enumeration of sync payload types identifying the data type in a batch.
 *
 * <p>Proto definition:
 * <pre>
 * enum SyncPayloadType {
 *   SYNC_PAYLOAD_TYPE_UNSPECIFIED = 0;
 *   SYNC_PAYLOAD_TYPE_TENANT = 1;
 *   SYNC_PAYLOAD_TYPE_GAME = 2;
 *   SYNC_PAYLOAD_TYPE_NODE = 3;
 *   SYNC_PAYLOAD_TYPE_DATA_CENTER = 4;
 *   SYNC_PAYLOAD_TYPE_MODEL = 5;
 *   SYNC_PAYLOAD_TYPE_GPU_RESOURCE = 6;
 * }
 * </pre>
 */
public enum SyncPayloadType {

    SYNC_PAYLOAD_TYPE_UNSPECIFIED(0),
    /** 租户信息 */
    SYNC_PAYLOAD_TYPE_TENANT(1),
    /** 游戏信息 */
    SYNC_PAYLOAD_TYPE_GAME(2),
    /** 节点信息 */
    SYNC_PAYLOAD_TYPE_NODE(3),
    /** 机房信息 */
    SYNC_PAYLOAD_TYPE_DATA_CENTER(4),
    /** 机型信息 */
    SYNC_PAYLOAD_TYPE_MODEL(5),
    /** GPU 资源信息 */
    SYNC_PAYLOAD_TYPE_GPU_RESOURCE(6);

    private final int number;

    SyncPayloadType(int number) {
        this.number = number;
    }

    /** 返回枚举对应的 proto 数值。 */
    public int getNumber() {
        return number;
    }

    /**
     * 根据 proto 数值查找对应枚举常量。
     *
     * @param number proto 数值
     * @return 对应的枚举常量，未匹配时返回 {@link #SYNC_PAYLOAD_TYPE_UNSPECIFIED}
     */
    public static SyncPayloadType forNumber(int number) {
        for (SyncPayloadType value : values()) {
            if (value.number == number) {
                return value;
            }
        }
        return SYNC_PAYLOAD_TYPE_UNSPECIFIED;
    }
}
