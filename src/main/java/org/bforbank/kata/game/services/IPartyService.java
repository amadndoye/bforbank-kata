package org.bforbank.kata.game.services;


import org.bforbank.kata.game.model.Party;
import org.bforbank.kata.game.model.Player;

public interface IPartyService {

    Party startAParty(Player a, Player b);
    String updateScore( Player player, Party party);
}
