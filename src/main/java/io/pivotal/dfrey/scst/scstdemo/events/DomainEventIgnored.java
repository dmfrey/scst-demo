package io.pivotal.dfrey.scst.scstdemo.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.NONE;

@Value
@ToString( callSuper = true )
@JsonPropertyOrder({ "eventType", "workorderId", "user", "occurredOn" })
public class DomainEventIgnored implements DomainEvent {

    @Getter( NONE )
    private final UUID id;

    @Getter( NONE )
    private final String user;

    @Getter( NONE )
    private final Instant occurredOn;

    @JsonCreator
    public DomainEventIgnored(
            @JsonProperty( "id" ) final UUID id,
            @JsonProperty( "user" ) final String user,
            @JsonProperty( "occurredOn" ) final Instant occurredOn
    ) {

        this.id = id;
        this.user = user;
        this.occurredOn = occurredOn;

    }

    @Override
    @JsonProperty( "id" )
    public UUID id() {

        return null;
    }

    @Override
    @JsonProperty( "user" )
    public String user() {

        return null;
    }

    @JsonProperty( "occurredOn" )
    public Instant occurredOn() {

        return this.occurredOn;
    }

    @Override
    public String eventType() {

        return this.getClass().getSimpleName();
    }

}
