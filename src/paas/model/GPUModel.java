package paas.model;

/**
 * 表示 PaaS 平台中的 GPU 型号实体。
 * Represents a GPU model entity in the PaaS platform.
 */
public class GPUModel {

    private String modelId;
    private String name;
    private int memoryGb;
    private int powerWatts;
    private String rawData;

    /** 无参构造函数。 No-args constructor. */
    public GPUModel() {}

    /**
     * 全参构造函数（rawData 需单独设置）。
     * All-args constructor (rawData set separately).
     *
     * @param modelId     GPU 型号 ID / GPU model ID
     * @param name        GPU 型号名称 / GPU model name
     * @param memoryGb    显存容量（GB）/ GPU memory size in GB
     * @param powerWatts  额定功率（瓦）/ rated power in watts
     */
    public GPUModel(String modelId, String name, int memoryGb, int powerWatts) {
        this.modelId = modelId;
        this.name = name;
        this.memoryGb = memoryGb;
        this.powerWatts = powerWatts;
    }

    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMemoryGb() { return memoryGb; }
    public void setMemoryGb(int memoryGb) { this.memoryGb = memoryGb; }

    public int getPowerWatts() { return powerWatts; }
    public void setPowerWatts(int powerWatts) { this.powerWatts = powerWatts; }

    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }

    @Override
    public String toString() {
        return "GPUModel{modelId='" + modelId + "', name='" + name
                + "', memoryGb=" + memoryGb + ", powerWatts=" + powerWatts + "}";
    }
}
