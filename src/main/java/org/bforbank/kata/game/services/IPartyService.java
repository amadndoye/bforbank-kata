package org.bforbank.kata.game.services;


import org.bforbank.kata.game.model.Party;

import java.io.Serializable;

public interface IPartyService {
    default Player addPlayer(String name) {
        return new Player(name);
    }

    Party startAParty(Player a, Player b);

    String updateScore(Player player, Party party);

    String getScoreStatus(Party tennis);

    record Player(String name) implements Serializable {
        public final static String DEFAULT_PLAYER_NAME = "NONE";

    }
}
