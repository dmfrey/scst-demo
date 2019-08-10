package io.pivotal.dfrey.scst.scstdemo.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.NONE;

@EqualsAndHashCode( exclude = "occurredOn" )
@ToString
public abstract class AbstractDomainEvent implements DomainEvent, Serializable {

    @Getter( NONE )
    private final UUID id;

    @Getter( NONE )
    private final String user;

    @Getter( NONE )
    private final Instant occurredOn;

    AbstractDomainEvent( final UUID id, final String user, final Instant occurredOn ) {

        this.id = id;
        this.user = user;
        this.occurredOn = occurredOn;

    }

    @Override
    @JsonProperty( "id" )
    public UUID id() {

        return id;
    }

    @Override
    @JsonProperty( "user" )
    public String user() {

        return user;
    }

    @Override
    @JsonProperty( "occurredOn" )
    public Instant occurredOn() {

        return occurredOn;
    }

}
