package api.v1.paas;

import java.time.Instant;
import java.util.Objects;

/**
 * 游戏信息。
 * Game information.
 *
 * <p>Proto definition:
 * <pre>
 * message GameInfo {
 *   string game_id = 1;
 *   string tenant_id = 2;
 *   string tenant_name = 3;
 *   string game_name = 4;
 *   string global_game_id = 5;
 *   google.protobuf.Timestamp create_time = 6;
 *   google.protobuf.Timestamp update_time = 7;
 *   google.protobuf.Timestamp delete_time = 8;
 * }
 * </pre>
 */
public final class GameInfo {

    private final String gameId;
    private final String tenantId;
    private final String tenantName;
    private final String gameName;
    private final String globalGameId;
    private final Instant createTime;
    private final Instant updateTime;
    private final Instant deleteTime;

    private GameInfo(Builder builder) {
        this.gameId = builder.gameId;
        this.tenantId = builder.tenantId;
        this.tenantName = builder.tenantName;
        this.gameName = builder.gameName;
        this.globalGameId = builder.globalGameId;
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
        this.deleteTime = builder.deleteTime;
    }

    public String getGameId() {
        return gameId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGlobalGameId() {
        return globalGameId;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    /** 返回软删除时间。 */
    public Instant getDeleteTime() {
        return deleteTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameInfo)) return false;
        GameInfo that = (GameInfo) o;
        return Objects.equals(gameId, that.gameId)
                && Objects.equals(tenantId, that.tenantId)
                && Objects.equals(tenantName, that.tenantName)
                && Objects.equals(gameName, that.gameName)
                && Objects.equals(globalGameId, that.globalGameId)
                && Objects.equals(createTime, that.createTime)
                && Objects.equals(updateTime, that.updateTime)
                && Objects.equals(deleteTime, that.deleteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, tenantId, tenantName, gameName, globalGameId,
                createTime, updateTime, deleteTime);
    }

    @Override
    public String toString() {
        return "GameInfo{"
                + "gameId='" + gameId + '\''
                + ", tenantId='" + tenantId + '\''
                + ", tenantName='" + tenantName + '\''
                + ", gameName='" + gameName + '\''
                + ", globalGameId='" + globalGameId + '\''
                + ", createTime=" + createTime
                + ", updateTime=" + updateTime
                + ", deleteTime=" + deleteTime
                + '}';
    }

    /** Builder for {@link GameInfo}. */
    public static final class Builder {
        private String gameId = "";
        private String tenantId = "";
        private String tenantName = "";
        private String gameName = "";
        private String globalGameId = "";
        private Instant createTime;
        private Instant updateTime;
        private Instant deleteTime;

        private Builder() {}

        public Builder setGameId(String gameId) {
            this.gameId = gameId;
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

        public Builder setGameName(String gameName) {
            this.gameName = gameName;
            return this;
        }

        public Builder setGlobalGameId(String globalGameId) {
            this.globalGameId = globalGameId;
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

        public GameInfo build() {
            return new GameInfo(this);
        }
    }
}
