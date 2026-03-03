package api.v1.paas;

import api.v1.common.MessageEnvelope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 游戏信息同步事件。
 * Game information sync event.
 *
 * <p>Topic: paas-resource-sync
 *
 * <p>Proto definition:
 * <pre>
 * message GameSyncEvent {
 *   api.v1.common.MessageEnvelope envelope = 1;
 *   SyncHeader header = 2;
 *   repeated GameInfo games = 3;
 * }
 * </pre>
 */
public final class GameSyncEvent {

    private final MessageEnvelope envelope;
    private final SyncHeader header;
    private final List<GameInfo> games;

    private GameSyncEvent(Builder builder) {
        this.envelope = builder.envelope;
        this.header = builder.header;
        this.games = Collections.unmodifiableList(new ArrayList<>(builder.games));
    }

    public MessageEnvelope getEnvelope() {
        return envelope;
    }

    public SyncHeader getHeader() {
        return header;
    }

    public List<GameInfo> getGames() {
        return games;
    }

    public int getGamesCount() {
        return games.size();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameSyncEvent)) return false;
        GameSyncEvent that = (GameSyncEvent) o;
        return Objects.equals(envelope, that.envelope)
                && Objects.equals(header, that.header)
                && Objects.equals(games, that.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envelope, header, games);
    }

    @Override
    public String toString() {
        return "GameSyncEvent{"
                + "envelope=" + envelope
                + ", header=" + header
                + ", games=" + games
                + '}';
    }

    /** Builder for {@link GameSyncEvent}. */
    public static final class Builder {
        private MessageEnvelope envelope;
        private SyncHeader header;
        private List<GameInfo> games = new ArrayList<>();

        private Builder() {}

        public Builder setEnvelope(MessageEnvelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder setHeader(SyncHeader header) {
            this.header = header;
            return this;
        }

        public Builder addGames(GameInfo game) {
            this.games.add(game);
            return this;
        }

        public Builder addAllGames(List<GameInfo> games) {
            this.games.addAll(games);
            return this;
        }

        public GameSyncEvent build() {
            return new GameSyncEvent(this);
        }
    }
}
