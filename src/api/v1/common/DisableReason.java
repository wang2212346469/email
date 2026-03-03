package api.v1.common;

/**
 * 禁用原因枚举。
 * Enumeration of reasons for disabling a resource.
 *
 * <p>Proto definition:
 * <pre>
 * enum DisableReason {
 *   DISABLE_REASON_UNSPECIFIED = 0;
 *   DISABLE_REASON_MAINTENANCE = 1;
 *   DISABLE_REASON_FAULT = 2;
 *   DISABLE_REASON_CAPACITY_ADJUST = 3;
 *   DISABLE_REASON_MANUAL = 4;
 * }
 * </pre>
 */
public enum DisableReason {

    DISABLE_REASON_UNSPECIFIED(0),
    /** 维护 */
    DISABLE_REASON_MAINTENANCE(1),
    /** 故障 */
    DISABLE_REASON_FAULT(2),
    /** 容量调整 */
    DISABLE_REASON_CAPACITY_ADJUST(3),
    /** 手动禁用 */
    DISABLE_REASON_MANUAL(4);

    private final int number;

    DisableReason(int number) {
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
     * @return 对应的枚举常量，未匹配时返回 {@link #DISABLE_REASON_UNSPECIFIED}
     */
    public static DisableReason forNumber(int number) {
        for (DisableReason value : values()) {
            if (value.number == number) {
                return value;
            }
        }
        return DISABLE_REASON_UNSPECIFIED;
    }
}
