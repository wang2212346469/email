package api.v1.common;

/**
 * PaaS 资源状态枚举。
 * Enumeration of PaaS resource statuses.
 *
 * <p>Proto definition:
 * <pre>
 * enum PaasResourceStatus {
 *   PAAS_RESOURCE_STATUS_UNSPECIFIED = 0;
 *   PAAS_RESOURCE_STATUS_AVAILABLE = 1;
 *   PAAS_RESOURCE_STATUS_IN_USE = 2;
 *   PAAS_RESOURCE_STATUS_RESERVED = 3;
 *   PAAS_RESOURCE_STATUS_DRAINING = 4;
 *   PAAS_RESOURCE_STATUS_FAULT = 5;
 *   PAAS_RESOURCE_STATUS_OFFLINE = 6;
 *   PAAS_RESOURCE_STATUS_DISABLED = 7;
 *   PAAS_RESOURCE_STATUS_UNKNOWN = 8;
 * }
 * </pre>
 */
public enum PaasResourceStatus {

    PAAS_RESOURCE_STATUS_UNSPECIFIED(0),
    /** 空闲可用 */
    PAAS_RESOURCE_STATUS_AVAILABLE(1),
    /** 使用中 */
    PAAS_RESOURCE_STATUS_IN_USE(2),
    /** 已预留 */
    PAAS_RESOURCE_STATUS_RESERVED(3),
    /** 排空中 */
    PAAS_RESOURCE_STATUS_DRAINING(4),
    /** 故障 */
    PAAS_RESOURCE_STATUS_FAULT(5),
    /** 下线 */
    PAAS_RESOURCE_STATUS_OFFLINE(6),
    /** 禁用 */
    PAAS_RESOURCE_STATUS_DISABLED(7),
    /** 未知 */
    PAAS_RESOURCE_STATUS_UNKNOWN(8);

    private final int number;

    PaasResourceStatus(int number) {
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
     * @return 对应的枚举常量，未匹配时返回 {@link #PAAS_RESOURCE_STATUS_UNSPECIFIED}
     */
    public static PaasResourceStatus forNumber(int number) {
        for (PaasResourceStatus value : values()) {
            if (value.number == number) {
                return value;
            }
        }
        return PAAS_RESOURCE_STATUS_UNSPECIFIED;
    }
}
