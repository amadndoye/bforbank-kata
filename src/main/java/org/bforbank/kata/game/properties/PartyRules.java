package org.bforbank.kata.game.properties;

import java.io.Serializable;
import java.util.Map;

public record PartyRules(Integer initialScore, String conditionBase,
                         Map<String, Integer> pointAttribution) implements Serializable {

}
