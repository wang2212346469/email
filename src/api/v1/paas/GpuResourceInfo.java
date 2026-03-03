package api.v1.paas;

import java.time.Instant;
import java.util.Objects;

/**
 * GPU 资源详细信息。
 * Detailed information about a GPU resource.
 *
 * <p>Proto definition:
 * <pre>
 * message GpuResourceInfo {
 *   string server_id = 1;
 *   string server_name = 2;
 *   string tenant_id = 3;
 *   string tenant_name = 4;
 *   string node_id = 5;
 *   string node_name = 6;
 *   string data_center_id = 7;
 *   string data_center_name = 8;
 *   string model_id = 9;
 *   string model_name = 10;
 *   string nat_ip = 11;
 *   string wan_ip = 12;
 *   string export_ip = 13;
 *   string export_ip_type = 14;
 *   string export_ip2 = 15;
 *   string export_ip2_type = 16;
 *   string export_ip3 = 17;
 *   string export_ip3_type = 18;
 *   string dx_ip = 19;
 *   string lt_ip = 20;
 *   string yd_ip = 21;
 *   string bgp_ip = 22;
 *   int32 status = 23;
 *   int32 freeze = 24;
 *   google.protobuf.Timestamp create_time = 25;
 *   google.protobuf.Timestamp update_time = 26;
 *   google.protobuf.Timestamp delete_time = 27;
 * }
 * </pre>
 */
public final class GpuResourceInfo {

    // 核心标识
    private final String serverId;
    private final String serverName;

    // 租户信息
    private final String tenantId;
    private final String tenantName;

    // 节点信息
    private final String nodeId;
    private final String nodeName;

    // 机房信息
    private final String dataCenterId;
    private final String dataCenterName;

    // 机型信息
    private final String modelId;
    private final String modelName;

    // 网络信息
    private final String natIp;
    private final String wanIp;
    private final String exportIp;
    private final String exportIpType;
    private final String exportIp2;
    private final String exportIp2Type;
    private final String exportIp3;
    private final String exportIp3Type;
    private final String dxIp;
    private final String ltIp;
    private final String ydIp;
    private final String bgpIp;

    // 状态信息
    private final int status;
    private final int freeze;

    // 时间戳
    private final Instant createTime;
    private final Instant updateTime;
    private final Instant deleteTime;

    private GpuResourceInfo(Builder builder) {
        this.serverId = builder.serverId;
        this.serverName = builder.serverName;
        this.tenantId = builder.tenantId;
        this.tenantName = builder.tenantName;
        this.nodeId = builder.nodeId;
        this.nodeName = builder.nodeName;
        this.dataCenterId = builder.dataCenterId;
        this.dataCenterName = builder.dataCenterName;
        this.modelId = builder.modelId;
        this.modelName = builder.modelName;
        this.natIp = builder.natIp;
        this.wanIp = builder.wanIp;
        this.exportIp = builder.exportIp;
        this.exportIpType = builder.exportIpType;
        this.exportIp2 = builder.exportIp2;
        this.exportIp2Type = builder.exportIp2Type;
        this.exportIp3 = builder.exportIp3;
        this.exportIp3Type = builder.exportIp3Type;
        this.dxIp = builder.dxIp;
        this.ltIp = builder.ltIp;
        this.ydIp = builder.ydIp;
        this.bgpIp = builder.bgpIp;
        this.status = builder.status;
        this.freeze = builder.freeze;
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
        this.deleteTime = builder.deleteTime;
    }

    /** 返回系统主键。 */
    public String getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getDataCenterId() {
        return dataCenterId;
    }

    /** 返回机房名称。 */
    public String getDataCenterName() {
        return dataCenterName;
    }

    public String getModelId() {
        return modelId;
    }

    public String getModelName() {
        return modelName;
    }

    /** 返回内网 IP。 */
    public String getNatIp() {
        return natIp;
    }

    /** 返回外网 IP。 */
    public String getWanIp() {
        return wanIp;
    }

    /** 返回出口 IP。 */
    public String getExportIp() {
        return exportIp;
    }

    /** 返回出口 IP 类型。 */
    public String getExportIpType() {
        return exportIpType;
    }

    /** 返回出口 IP2。 */
    public String getExportIp2() {
        return exportIp2;
    }

    /** 返回出口 IP2 类型。 */
    public String getExportIp2Type() {
        return exportIp2Type;
    }

    /** 返回出口 IP3。 */
    public String getExportIp3() {
        return exportIp3;
    }

    /** 返回出口 IP3 类型。 */
    public String getExportIp3Type() {
        return exportIp3Type;
    }

    /** 返回电信 IP。 */
    public String getDxIp() {
        return dxIp;
    }

    /** 返回联通 IP。 */
    public String getLtIp() {
        return ltIp;
    }

    /** 返回移动 IP。 */
    public String getYdIp() {
        return ydIp;
    }

    /** 返回 BGP IP。 */
    public String getBgpIp() {
        return bgpIp;
    }

    /** 返回 PaaS 原始状态 (0-8)。 */
    public int getStatus() {
        return status;
    }

    /** 返回冻结标记（0=冻结, 1=解冻）。 */
    public int getFreeze() {
        return freeze;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    /** 返回软删除时间，非空表示已删除。 */
    public Instant getDeleteTime() {
        return deleteTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GpuResourceInfo)) return false;
        GpuResourceInfo that = (GpuResourceInfo) o;
        return status == that.status
                && freeze == that.freeze
                && Objects.equals(serverId, that.serverId)
                && Objects.equals(serverName, that.serverName)
                && Objects.equals(tenantId, that.tenantId)
                && Objects.equals(tenantName, that.tenantName)
                && Objects.equals(nodeId, that.nodeId)
                && Objects.equals(nodeName, that.nodeName)
                && Objects.equals(dataCenterId, that.dataCenterId)
                && Objects.equals(dataCenterName, that.dataCenterName)
                && Objects.equals(modelId, that.modelId)
                && Objects.equals(modelName, that.modelName)
                && Objects.equals(natIp, that.natIp)
                && Objects.equals(wanIp, that.wanIp)
                && Objects.equals(exportIp, that.exportIp)
                && Objects.equals(exportIpType, that.exportIpType)
                && Objects.equals(exportIp2, that.exportIp2)
                && Objects.equals(exportIp2Type, that.exportIp2Type)
                && Objects.equals(exportIp3, that.exportIp3)
                && Objects.equals(exportIp3Type, that.exportIp3Type)
                && Objects.equals(dxIp, that.dxIp)
                && Objects.equals(ltIp, that.ltIp)
                && Objects.equals(ydIp, that.ydIp)
                && Objects.equals(bgpIp, that.bgpIp)
                && Objects.equals(createTime, that.createTime)
                && Objects.equals(updateTime, that.updateTime)
                && Objects.equals(deleteTime, that.deleteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, serverName, tenantId, tenantName, nodeId, nodeName,
                dataCenterId, dataCenterName, modelId, modelName, natIp, wanIp, exportIp,
                exportIpType, exportIp2, exportIp2Type, exportIp3, exportIp3Type, dxIp, ltIp,
                ydIp, bgpIp, status, freeze, createTime, updateTime, deleteTime);
    }

    @Override
    public String toString() {
        return "GpuResourceInfo{"
                + "serverId='" + serverId + '\''
                + ", serverName='" + serverName + '\''
                + ", tenantId='" + tenantId + '\''
                + ", tenantName='" + tenantName + '\''
                + ", nodeId='" + nodeId + '\''
                + ", nodeName='" + nodeName + '\''
                + ", dataCenterId='" + dataCenterId + '\''
                + ", dataCenterName='" + dataCenterName + '\''
                + ", modelId='" + modelId + '\''
                + ", modelName='" + modelName + '\''
                + ", natIp='" + natIp + '\''
                + ", wanIp='" + wanIp + '\''
                + ", exportIp='" + exportIp + '\''
                + ", exportIpType='" + exportIpType + '\''
                + ", exportIp2='" + exportIp2 + '\''
                + ", exportIp2Type='" + exportIp2Type + '\''
                + ", exportIp3='" + exportIp3 + '\''
                + ", exportIp3Type='" + exportIp3Type + '\''
                + ", dxIp='" + dxIp + '\''
                + ", ltIp='" + ltIp + '\''
                + ", ydIp='" + ydIp + '\''
                + ", bgpIp='" + bgpIp + '\''
                + ", status=" + status
                + ", freeze=" + freeze
                + ", createTime=" + createTime
                + ", updateTime=" + updateTime
                + ", deleteTime=" + deleteTime
                + '}';
    }

    /** Builder for {@link GpuResourceInfo}. */
    public static final class Builder {
        private String serverId = "";
        private String serverName = "";
        private String tenantId = "";
        private String tenantName = "";
        private String nodeId = "";
        private String nodeName = "";
        private String dataCenterId = "";
        private String dataCenterName = "";
        private String modelId = "";
        private String modelName = "";
        private String natIp = "";
        private String wanIp = "";
        private String exportIp = "";
        private String exportIpType = "";
        private String exportIp2 = "";
        private String exportIp2Type = "";
        private String exportIp3 = "";
        private String exportIp3Type = "";
        private String dxIp = "";
        private String ltIp = "";
        private String ydIp = "";
        private String bgpIp = "";
        private int status;
        private int freeze;
        private Instant createTime;
        private Instant updateTime;
        private Instant deleteTime;

        private Builder() {}

        public Builder setServerId(String serverId) {
            this.serverId = serverId;
            return this;
        }

        public Builder setServerName(String serverName) {
            this.serverName = serverName;
            return this;
        }

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder setTenantName(String tenantName) {
            this.tenantName = tenantName;
            return this;
        }

        public Builder setNodeId(String nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        public Builder setNodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public Builder setDataCenterId(String dataCenterId) {
            this.dataCenterId = dataCenterId;
            return this;
        }

        public Builder setDataCenterName(String dataCenterName) {
            this.dataCenterName = dataCenterName;
            return this;
        }

        public Builder setModelId(String modelId) {
            this.modelId = modelId;
            return this;
        }

        public Builder setModelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public Builder setNatIp(String natIp) {
            this.natIp = natIp;
            return this;
        }

        public Builder setWanIp(String wanIp) {
            this.wanIp = wanIp;
            return this;
        }

        public Builder setExportIp(String exportIp) {
            this.exportIp = exportIp;
            return this;
        }

        public Builder setExportIpType(String exportIpType) {
            this.exportIpType = exportIpType;
            return this;
        }

        public Builder setExportIp2(String exportIp2) {
            this.exportIp2 = exportIp2;
            return this;
        }

        public Builder setExportIp2Type(String exportIp2Type) {
            this.exportIp2Type = exportIp2Type;
            return this;
        }

        public Builder setExportIp3(String exportIp3) {
            this.exportIp3 = exportIp3;
            return this;
        }

        public Builder setExportIp3Type(String exportIp3Type) {
            this.exportIp3Type = exportIp3Type;
            return this;
        }

        public Builder setDxIp(String dxIp) {
            this.dxIp = dxIp;
            return this;
        }

        public Builder setLtIp(String ltIp) {
            this.ltIp = ltIp;
            return this;
        }

        public Builder setYdIp(String ydIp) {
            this.ydIp = ydIp;
            return this;
        }

        public Builder setBgpIp(String bgpIp) {
            this.bgpIp = bgpIp;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setFreeze(int freeze) {
            this.freeze = freeze;
            return this;
        }

        public Builder setCreateTime(Instant createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder setUpdateTime(Instant updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder setDeleteTime(Instant deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }

        public GpuResourceInfo build() {
            return new GpuResourceInfo(this);
        }
    }
}
