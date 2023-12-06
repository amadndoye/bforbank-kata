package org.bforbank.kata.game.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class Party implements Serializable {

    private final Long partyId;
    private String status;
    public Party(Long partyId){
        status = PartyStatus.START;
        this.partyId =partyId;
    }

}
