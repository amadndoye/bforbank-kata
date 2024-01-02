package org.bforbank.kata.game.services.tennis;


import org.bforbank.kata.game.model.Party;
import org.bforbank.kata.game.model.PartyStatus;
import org.bforbank.kata.game.services.IPartyService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.bforbank.kata.game.model.PartyStatus.PARTY_ALREADY_FINISHED;

public class TennisService implements IPartyService {

    private final AtomicLong partiesCount = new AtomicLong(0);
    private final Map<Long, TennisScoreHandler> scores = new HashMap<>();

    @Override
    public Party startAParty(Player a, Player b) {
        var tennisParty = new Party(partiesCount.getAndIncrement());
        var scoreHandler = new TennisScoreHandler(a, b);
        scores.put(tennisParty.getPartyId(), scoreHandler);
        System.out.println(STR."Tennis game started between \{a.name()} and \{b.name()}");
        return tennisParty;
    }

    @Override
    public String updateScore(Player player, Party tennis) {
        var score = scores.get(tennis.getPartyId());
        switch (tennis.getStatus()) {
            case PartyStatus.START, PartyStatus.ON_GOING -> tennis.setStatus(score.updateScore(player));
            case TennisPartyStatus.DEUCE, TennisPartyStatus.ADVANTAGE -> tennis.setStatus(score.specialEvent(player));
            case PartyStatus.WIN, PartyStatus.PARTY_ALREADY_FINISHED -> tennis.setStatus(PARTY_ALREADY_FINISHED);
            default -> tennis.setStatus(PartyStatus.UNKNOWN);
        }
        System.out.println(getScoreStatus(tennis));
        return getScoreStatus(tennis);
    }

    @Override
    public String getScoreStatus(Party tennis) {
        var score = scores.get(tennis.getPartyId());
        record Tuple(String players, String scores) {
        }
        var status = score.getPlayersScores().entrySet().stream().map(x -> new Tuple(x.getKey().name(), STR."\{x.getValue()}"))
                .reduce((x, y) -> new Tuple(STR."\{x.players()} - \{y.players()}", STR."\{x.scores()} - \{y.scores()}")).orElse(new Tuple(PartyStatus.UNKNOWN, PartyStatus.UNKNOWN));
        return STR."Tennis game is \{tennis.getStatus()} score \{status.players()}: \{status.scores()}";
    }
}
