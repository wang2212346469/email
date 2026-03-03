package paas.model;

/**
 * 表示 PaaS 平台中的租户实体。
 * Represents a tenant entity in the PaaS platform.
 */
public class Tenant {

    private String tenantId;
    private String name;
    private String status;
    private String purpose;
    private String rawData;

    /** 无参构造函数。 No-args constructor. */
    public Tenant() {}

    /**
     * 全参构造函数（rawData 需单独设置）。
     * All-args constructor (rawData set separately).
     *
     * @param tenantId 租户 ID / tenant ID
     * @param name     租户名称 / tenant name
     * @param status   租户状态 / tenant status
     * @param purpose  租户用途 / tenant purpose
     */
    public Tenant(String tenantId, String name, String status, String purpose) {
        this.tenantId = tenantId;
        this.name = name;
        this.status = status;
        this.purpose = purpose;
    }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }

    @Override
    public String toString() {
        return "Tenant{tenantId='" + tenantId + "', name='" + name
                + "', status='" + status + "', purpose='" + purpose + "'}";
    }
}
