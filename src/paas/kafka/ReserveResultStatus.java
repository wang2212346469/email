package paas.kafka;

/**
 * 资源预留操作结果状态枚举。
 * Reserve operation result status enum.
 */
public enum ReserveResultStatus {
    /** 预留成功。 Reserve succeeded. */
    RESERVED,
    /** 预留失败。 Reserve failed. */
    FAILED,
    /** 资源已被预留（幂等场景）。 Resource already reserved (idempotent case). */
    ALREADY_RESERVED
}
