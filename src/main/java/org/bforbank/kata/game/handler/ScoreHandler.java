package org.bforbank.kata.game.handler;

import org.bforbank.kata.game.model.Player;

public interface ScoreHandler {
    String updateScore(Player player) ;
    String specialEvent(Player player) ;
}
