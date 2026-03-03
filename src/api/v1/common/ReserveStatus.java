package api.v1.common;

/**
 * 预留状态枚举。
 * Enumeration of reservation statuses.
 *
 * <p>Proto definition:
 * <pre>
 * enum ReserveStatus {
 *   RESERVE_STATUS_UNSPECIFIED = 0;
 *   RESERVE_STATUS_SUCCESS = 1;
 *   RESERVE_STATUS_FAILED = 2;
 *   RESERVE_STATUS_ALREADY_RESERVED = 3;
 *   RESERVE_STATUS_NOT_FOUND = 4;
 *   RESERVE_STATUS_UNAVAILABLE = 5;
 * }
 * </pre>
 */
public enum ReserveStatus {

    RESERVE_STATUS_UNSPECIFIED(0),
    /** 预留成功 */
    RESERVE_STATUS_SUCCESS(1),
    /** 预留失败 */
    RESERVE_STATUS_FAILED(2),
    /** 已被预留 */
    RESERVE_STATUS_ALREADY_RESERVED(3),
    /** 资源不存在 */
    RESERVE_STATUS_NOT_FOUND(4),
    /** 资源不可用 */
    RESERVE_STATUS_UNAVAILABLE(5);

    private final int number;

    ReserveStatus(int number) {
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
     * @return 对应的枚举常量，未匹配时返回 {@link #RESERVE_STATUS_UNSPECIFIED}
     */
    public static ReserveStatus forNumber(int number) {
        for (ReserveStatus value : values()) {
            if (value.number == number) {
                return value;
            }
        }
        return RESERVE_STATUS_UNSPECIFIED;
    }
}
