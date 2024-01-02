package org.bforbank.kata.game.games.tennis;

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

    /**
     * @return the scores of players
     */
    @Override
    public Map<IPartyService.Player, Integer> getPlayersScores() {
        return Collections.unmodifiableMap(playersScores);
    }

    /**
     * @return the winner when the party ended
     */
    public String getTheWinner(){
       return playerWithAdvantage;
    }

    /**
     * @param player
     * calculate the new score of the player then evaluate the party for a status change
     * @return the new status of the party
     */
    @Override
    public String updateScore(IPartyService.Player player) {
        playersScores.computeIfPresent(player, (x, y) -> y + calculateScore(player));
        if (playersScores.get(player) == 40 && !Objects.equals(playerWithAdvantage, player.name())) {
            advantageCount.set(0);
            playerWithAdvantage = player.name();
            return playersScores.values().stream().allMatch(x -> x == 40) ? TennisPartyStatus.DEUCE : TennisPartyStatus.ADVANTAGE;
        }
        return PartyStatus.ON_GOING;
    }

    /**
     * @param player
     * Handle special Event. In tennis, we have two special Event : DEUCE and Advantage
     * we are in Advantage when a player reach 40 points. In that case when the player with advantage score twice he won't the game
     * we are in Deuce when both player reach 40 points. In that case, the next player who score won the game
     * @return the new status, END,ADVANTAGE or DEUCE
     */
    @Override
    public String specialEvent(IPartyService.Player player) {
        playersScores.computeIfPresent(player, (x, y) -> y + calculateScore(player));

        if (Objects.equals(playerWithAdvantage, player.name())) {
            if (advantageCount.incrementAndGet() == (playersScores.values().stream().allMatch(x -> x == 40) ? 2 : 1)) {
                return PartyStatus.END;
            }
            return TennisPartyStatus.ADVANTAGE;
        }
        advantageCount.set(0);
        return TennisPartyStatus.DEUCE;
    }

    /**
     * @param player
     * Calculate the next score of a player according to the number of ball he scored. after the 3 ball scored, the player won't increase his score
     * @return previous score + 15 points for the 2 first ball scored,
     *         previous score 10 points for the 3 first ball scored
     */
    @Override
    public int calculateScore(IPartyService.Player player) {
        return switch (playersBallCount.compute(player, (x, y) -> Objects.isNull(y) ? 0 : y == 3 ? y : y + 1)) {
            case 0, 1 -> 15;
            case 2 -> 10;
            default -> 0;
        };
    }

}
