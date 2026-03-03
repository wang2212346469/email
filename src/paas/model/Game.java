package paas.model;

/**
 * 表示 PaaS 平台中的游戏实体。
 * Represents a game entity in the PaaS platform.
 */
public class Game {

    private String gameId;
    private String tenantId;
    private String name;
    private String rawData;

    /** 无参构造函数。 No-args constructor. */
    public Game() {}

    /**
     * 全参构造函数（rawData 需单独设置）。
     * All-args constructor (rawData set separately).
     *
     * @param gameId   游戏 ID / game ID
     * @param tenantId 所属租户 ID / owner tenant ID
     * @param name     游戏名称 / game name
     */
    public Game(String gameId, String tenantId, String name) {
        this.gameId = gameId;
        this.tenantId = tenantId;
        this.name = name;
    }

    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }

    @Override
    public String toString() {
        return "Game{gameId='" + gameId + "', tenantId='" + tenantId + "', name='" + name + "'}";
    }
}
