package org.bforbank.kata.game.properties;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class PartyRules implements Serializable {

    private final Integer initialScore;
    private final String conditionBase;
    private final Map<String,Integer> pointAttribution;
}
