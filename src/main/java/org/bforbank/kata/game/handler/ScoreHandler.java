package org.bforbank.kata.game.handler;


import org.bforbank.kata.game.services.IPartyService;

import java.util.Map;

public interface ScoreHandler {
    String updateScore(IPartyService.Player player);

    String specialEvent(IPartyService.Player player);

    Map<IPartyService.Player, Integer> getPlayersScores();

    int calculateScore(IPartyService.Player player);
}


