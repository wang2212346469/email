package paas.model;

/**
 * 表示 PaaS 平台中的数据中心实体。
 * Represents a data center entity in the PaaS platform.
 */
public class DataCenter {

    private String dcId;
    private String name;
    private String region;
    private String rawData;

    /** 无参构造函数。 No-args constructor. */
    public DataCenter() {}

    /**
     * 全参构造函数（rawData 需单独设置）。
     * All-args constructor (rawData set separately).
     *
     * @param dcId   数据中心 ID / data center ID
     * @param name   数据中心名称 / data center name
     * @param region 所属区域 / region
     */
    public DataCenter(String dcId, String name, String region) {
        this.dcId = dcId;
        this.name = name;
        this.region = region;
    }

    public String getDcId() { return dcId; }
    public void setDcId(String dcId) { this.dcId = dcId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }

    @Override
    public String toString() {
        return "DataCenter{dcId='" + dcId + "', name='" + name + "', region='" + region + "'}";
    }
}
