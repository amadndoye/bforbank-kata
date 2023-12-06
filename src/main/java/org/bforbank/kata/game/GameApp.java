package org.bforbank.kata.game;

import org.bforbank.kata.game.model.Party;
import org.bforbank.kata.game.model.Player;
import org.bforbank.kata.game.services.tennis.TennisService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@SpringBootApplication
//@EnableConfigurationProperties
public class GameApp {
    public static void main(String[] args) {
        TennisService tennisService = new TennisService();
        Player a = new Player("A");
        Player b = new Player("B");
        Party tennis = tennisService.startAParty(a,b);
        System.out.println(tennisService.updateScore(a,tennis));
        System.out.println(tennisService.updateScore(b,tennis));
        System.out.println(tennisService.updateScore(a,tennis));
        System.out.println(tennisService.updateScore(b,tennis));
        System.out.println(tennisService.updateScore(a,tennis));
        System.out.println(tennisService.updateScore(a,tennis));
        System.out.println(tennisService.updateScore(a,tennis));


    }
}
