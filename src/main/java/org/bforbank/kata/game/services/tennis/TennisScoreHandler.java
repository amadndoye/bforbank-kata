package org.bforbank.kata.game.services.tennis;

import org.bforbank.kata.game.handler.ScoreHandler;
import org.bforbank.kata.game.model.PartyStatus;
import org.bforbank.kata.game.services.IPartyService;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bforbank.kata.game.services.IPartyService.Player.DEFAULT_PLAYER_NAME;

public class TennisScoreHandler implements ScoreHandler, Serializable {

    private final Map<IPartyService.Player, Integer> playersScores = new HashMap<>();
    private final Map<IPartyService.Player, Integer> playersBallCount = new HashMap<>();
    private final AtomicInteger advantageCount;
    private String playerWithAdvantage = DEFAULT_PLAYER_NAME;

    public TennisScoreHandler(IPartyService.Player a, IPartyService.Player b) {
        this.playersScores.put(a, 0);
        this.playersScores.put(b, 0);
        this.advantageCount = new AtomicInteger(0);
    }

    @Override
    public Map<IPartyService.Player, Integer> getPlayersScores() {
        return Collections.unmodifiableMap(playersScores);
    }

    @Override
    public String updateScore(IPartyService.Player player) {
        if (Objects.equals(playerWithAdvantage, DEFAULT_PLAYER_NAME)) {
            playersScores.computeIfPresent(player, (x, y) -> y + calculateScore(player));
        }
        if (playersScores.get(player) == 40 && !Objects.equals(playerWithAdvantage, player.name())) {
            advantageCount.set(0);
            playerWithAdvantage = player.name();
            return playersScores.values().stream().allMatch(x -> x == 40) ? TennisPartyStatus.DEUCE : TennisPartyStatus.ADVANTAGE;
        }
        return PartyStatus.ON_GOING;
    }

    @Override
    public String specialEvent(IPartyService.Player player) {
        if (Objects.equals(playerWithAdvantage, player.name())) {
            if (advantageCount.incrementAndGet() == (playersScores.values().stream().allMatch(x -> x == 40) ? 2 : 1)) {
                return PartyStatus.WIN;
            }
            return TennisPartyStatus.ADVANTAGE;
        }
        advantageCount.set(0);
        return TennisPartyStatus.DEUCE;
    }

    @Override
    public int calculateScore(IPartyService.Player player) {
        return switch (playersBallCount.compute(player, (x, y) -> Objects.isNull(y) ? 0 : y == 3 ? y : y + 1)) {
            case 0, 1 -> 15;
            case 2 -> 10;
            default -> 0;
        };
    }

}
