package paas.kafka;

/**
 * GPU 资源健康状态枚举，对应 PaaS 心跳上报的状态值。
 * GPU resource health status enum corresponding to PaaS heartbeat status values.
 */
public enum HealthStatus {
    HEALTHY,
    DEGRADED,
    FAILED,
    UNKNOWN;

    /**
     * 将 PaaS 原始状态字符串转换为 HealthStatus 枚举值。
     * Converts a raw PaaS status string to a HealthStatus enum value.
     *
     * @param paasStatus PaaS 上报的状态字符串 / raw status string from PaaS
     * @return 对应的 HealthStatus，无法识别时返回 UNKNOWN / corresponding HealthStatus, UNKNOWN if unrecognised
     */
    public static HealthStatus fromPaasStatus(String paasStatus) {
        if (paasStatus == null) {
            return UNKNOWN;
        }
        switch (paasStatus.toUpperCase()) {
            case "HEALTHY":
            case "NORMAL":
            case "OK":
                return HEALTHY;
            case "DEGRADED":
            case "WARNING":
                return DEGRADED;
            case "FAILED":
            case "ERROR":
            case "DOWN":
                return FAILED;
            default:
                return UNKNOWN;
        }
    }
}
