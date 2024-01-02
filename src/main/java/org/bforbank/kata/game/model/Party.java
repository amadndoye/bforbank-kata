package org.bforbank.kata.game.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Party implements Serializable {

    private final Long partyId;
    private String status = PartyStatus.START;

}
