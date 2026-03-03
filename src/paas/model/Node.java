package paas.model;

/**
 * 表示 PaaS 平台中的节点（物理服务器宿主机）实体。
 * Represents a node (physical server host) entity in the PaaS platform.
 */
public class Node {

    private String nodeId;
    private String name;
    private String dataCenterId;
    private String ipAddress;
    private String rawData;

    /** 无参构造函数。 No-args constructor. */
    public Node() {}

    /**
     * 全参构造函数（rawData 需单独设置）。
     * All-args constructor (rawData set separately).
     *
     * @param nodeId       节点 ID / node ID
     * @param name         节点名称 / node name
     * @param dataCenterId 所属数据中心 ID / data center ID
     * @param ipAddress    节点 IP 地址 / node IP address
     */
    public Node(String nodeId, String name, String dataCenterId, String ipAddress) {
        this.nodeId = nodeId;
        this.name = name;
        this.dataCenterId = dataCenterId;
        this.ipAddress = ipAddress;
    }

    public String getNodeId() { return nodeId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDataCenterId() { return dataCenterId; }
    public void setDataCenterId(String dataCenterId) { this.dataCenterId = dataCenterId; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }

    @Override
    public String toString() {
        return "Node{nodeId='" + nodeId + "', name='" + name
                + "', dataCenterId='" + dataCenterId + "', ipAddress='" + ipAddress + "'}";
    }
}
