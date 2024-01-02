package org.bforbank.kata.game.games.tennis;

import org.bforbank.kata.game.model.PartyStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TennisServiceTest {

    @Test
    void startAParty() {
        var tennisService = new TennisService();
        var a = tennisService.addPlayer("A");
        var b = tennisService.addPlayer("B");
        var tennis = tennisService.startAParty(a, b);
        Assertions.assertEquals( PartyStatus.START,tennis.getStatus());
        Assertions.assertEquals("Tennis game Started score A - B: 0 - 0",tennisService.getScoreStatus(tennis));
    }

    @Test
    void updateScoreAdvantage() {
        var tennisService = new TennisService();
        var a = tennisService.addPlayer("A");
        var b = tennisService.addPlayer("B");
        var tennis = tennisService.startAParty(a, b);
        tennisService.updateScore(a, tennis);
        tennisService.updateScore(b, tennis);
        tennisService.updateScore(a, tennis);
        Assertions.assertEquals( PartyStatus.ON_GOING,tennis.getStatus());
        Assertions.assertEquals("Tennis game OnGoing score A - B: 30 - 15",tennisService.getScoreStatus(tennis));

        tennisService.updateScore(b, tennis);
        tennisService.updateScore(a, tennis);
        Assertions.assertEquals( TennisPartyStatus.ADVANTAGE,tennis.getStatus());
        Assertions.assertEquals("Tennis game Advantage score A - B: 40 - 30",tennisService.getScoreStatus(tennis));
        tennisService.updateScore(a, tennis);
        Assertions.assertEquals(PartyStatus.END,tennis.getStatus());
        Assertions.assertEquals("Tennis game Ended score A - B: 40 - 30 the winner is A",tennisService.getScoreStatus(tennis));
        tennisService.updateScore(a, tennis);
        Assertions.assertEquals( PartyStatus.PARTY_ALREADY_FINISHED,tennis.getStatus());
        Assertions.assertEquals("Tennis game The game already ended score A - B: 40 - 30",tennisService.getScoreStatus(tennis));
    }

    @Test
    void updateScoreDeuce() {
        var tennisService = new TennisService();
        var a = tennisService.addPlayer("A");
        var b = tennisService.addPlayer("B");
        var tennis = tennisService.startAParty(a, b);
        tennisService.updateScore(a, tennis);
        tennisService.updateScore(b, tennis);
        tennisService.updateScore(a, tennis);
        Assertions.assertEquals(PartyStatus.ON_GOING,tennis.getStatus());
        Assertions.assertEquals("Tennis game OnGoing score A - B: 30 - 15",tennisService.getScoreStatus(tennis));

        tennisService.updateScore(b, tennis);
        tennisService.updateScore(a, tennis);
        Assertions.assertEquals(TennisPartyStatus.ADVANTAGE,tennis.getStatus());
        Assertions.assertEquals("Tennis game Advantage score A - B: 40 - 30",tennisService.getScoreStatus(tennis));
        tennisService.updateScore(b, tennis);
        Assertions.assertEquals(TennisPartyStatus.DEUCE,tennis.getStatus());
        Assertions.assertEquals("Tennis game Deuce score A - B: 40 - 40",tennisService.getScoreStatus(tennis));
        tennisService.updateScore(a, tennis);
        Assertions.assertEquals(TennisPartyStatus.ADVANTAGE,tennis.getStatus());
        Assertions.assertEquals("Tennis game Advantage score A - B: 40 - 40",tennisService.getScoreStatus(tennis));
        tennisService.updateScore(a, tennis);
        Assertions.assertEquals(PartyStatus.END,tennis.getStatus());
        Assertions.assertEquals("Tennis game Ended score A - B: 40 - 40 the winner is A",tennisService.getScoreStatus(tennis));


    }
}