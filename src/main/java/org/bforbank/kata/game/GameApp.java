package org.bforbank.kata.game;

import org.bforbank.kata.game.services.tennis.TennisService;

public class GameApp {

    public static void main(String[] args) {

        var tennisService = new TennisService();
        var a = tennisService.addPlayer("A");
        var b = tennisService.addPlayer("B");
        var tennis = tennisService.startAParty(a, b);

        tennisService.updateScore(a, tennis);
        tennisService.updateScore(b, tennis);
        tennisService.updateScore(a, tennis);
        tennisService.updateScore(b, tennis);
        tennisService.updateScore(a, tennis);
        tennisService.updateScore(a, tennis);
        tennisService.updateScore(a, tennis);


    }
}
