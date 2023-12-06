package org.bforbank.kata.game.services.tennis;

import org.bforbank.kata.game.model.PartyStatus;
import org.bforbank.kata.game.model.Player;
import org.bforbank.kata.game.handler.ScoreHandler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.bforbank.kata.game.properties.PlayerConstant.DEFAULT_PLAYER_NAME;

public class TennisScoreHandler implements ScoreHandler,Serializable{

    private final Map<String,Integer> playersScores;

    private final Map<String,Integer> playersBallCount;

    private  Integer advantageCount;
    private String playerWithAdvantage;


    public TennisScoreHandler(Map<String,Integer> playersScores){
        this.playersScores =playersScores;
        this.playersBallCount = new HashMap<>();
        advantageCount = 0;
        playerWithAdvantage = DEFAULT_PLAYER_NAME;
    }

    public Integer getPlayerScore(String name){
        return playersScores.get(name);
    }

    @Override
    public String updateScore(Player player) {
        if(Objects.equals(playerWithAdvantage,DEFAULT_PLAYER_NAME)){
             playersScores.compute(player.getName(),(x,y) -> y + getPoint(player));
        }
        if(playersScores.get(player.getName()) == 40 && !Objects.equals(playerWithAdvantage,player.getName())){
            advantageCount = 0;
            playerWithAdvantage = player.getName();
            return playersScores.values().stream().allMatch(x-> x == 40)?TennisPartyStatus.DEUCE:TennisPartyStatus.ADVANTAGE;
        }
        return PartyStatus.ON_GOING;
    }

    private int getPoint(Player player) {
       int count =  playersBallCount.compute(player.getName(),(x,y)-> Objects.isNull(y)?0:y==3?y:y+1);
       switch (count) {
           case 0:
           case 1:
               return 15;
           case 2:
               return 10;
           case 3:
           default:
               return 0;
       }
    }

    @Override
    public String specialEvent(Player player) {
        if(Objects.equals(playerWithAdvantage,player.getName()) ){
            int limit = playersScores.values().stream().allMatch(x-> x == 40)?2:1;
            advantageCount++;
            if(advantageCount == limit ){
                return PartyStatus.WIN;
            }
            return TennisPartyStatus.ADVANTAGE;
        }
        advantageCount = 0;
        return TennisPartyStatus.DEUCE;
    }
}
