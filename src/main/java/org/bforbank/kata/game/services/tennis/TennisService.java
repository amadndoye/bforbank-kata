package org.bforbank.kata.game.services.tennis;


import org.bforbank.kata.game.model.Party;
import org.bforbank.kata.game.model.PartyStatus;
import org.bforbank.kata.game.model.Player;
import org.bforbank.kata.game.services.IPartyService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.bforbank.kata.game.model.PartyStatus.PARTY_ALREADY_FINISHED;

public class TennisService implements IPartyService {
    private final AtomicLong partiesCount = new AtomicLong(0);
    Map<Long, TennisScoreHandler> scores = new HashMap<>();

    @Override
    public Party startAParty(Player a, Player b) {
        TennisScoreHandler scoreHandler = initTennisScore(a, b);
        Party tennisParty = new Party(partiesCount.getAndIncrement());
        scores.put(tennisParty.getPartyId(), scoreHandler);
        return tennisParty;
    }

    private TennisScoreHandler initTennisScore(Player a, Player b) {
        Map<String,Integer> startScore = new HashMap<>();
        startScore.put(a.getName(), 0);
        startScore.put(b.getName(), 0);
        return new TennisScoreHandler(startScore);
    }

    @Override
    public String updateScore( Player player, Party tennis) {
        switch (tennis.getStatus()){
            case PartyStatus.START:
            case PartyStatus.ON_GOING:
                tennis.setStatus(scores.get(tennis.getPartyId()).updateScore(player));
                break;
            case TennisPartyStatus.DEUCE:
            case TennisPartyStatus.ADVANTAGE:
                tennis.setStatus(scores.get(tennis.getPartyId()).specialEvent(player));
                break;
            case PartyStatus.WIN:
            case PartyStatus.PARTY_ALREADY_FINISHED:
                tennis.setStatus(PARTY_ALREADY_FINISHED);
                break;
            default:
                tennis.setStatus(PartyStatus.UNKNOWN);
        }
        return tennis.getStatus() + " score: " + scores.get(tennis.getPartyId()).getPlayerScore("A") + " / score: " + scores.get(tennis.getPartyId()).getPlayerScore("B");
    }
}
