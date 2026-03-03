package paas.sync;

import paas.kafka.KafkaTopics;
import paas.model.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 游戏实体同步消费者，处理 PaaS 同步消息中的 GAME 类型。
 * Game entity sync consumer handling GAME type messages from PaaS sync topics.
 */
public class GameSyncConsumer extends EntitySyncConsumer {

    /** 已同步的游戏列表。 List of synced games. */
    private final List<Game> syncedGames = new ArrayList<>();

    @Override
    protected String getTopic() {
        return KafkaTopics.PAAS_RESOURCE_SYNC_FULL;
    }

    /**
     * 返回已同步的游戏列表（不可变视图）。
     * Returns the list of synced games (read-only view).
     */
    public List<Game> getSyncedGames() {
        return Collections.unmodifiableList(syncedGames);
    }

    @Override
    protected void handleUpsert(String payloadJson) {
        Game game = EntityConverter.toGame(payloadJson);
        if (game == null || game.getGameId() == null) {
            System.err.println("[GameSyncConsumer] Skipping upsert - invalid payload");
            return;
        }
        syncedGames.removeIf(g -> game.getGameId().equals(g.getGameId()));
        syncedGames.add(game);
        System.out.println("[GameSyncConsumer] Upserted: " + game);
    }

    @Override
    protected void handleDelete(String payloadJson) {
        String gameId = extractField(payloadJson, "gameId", "game_id");
        if (gameId == null) return;
        syncedGames.removeIf(g -> gameId.equals(g.getGameId()));
        System.out.println("[GameSyncConsumer] Deleted gameId=" + gameId);
    }
}
