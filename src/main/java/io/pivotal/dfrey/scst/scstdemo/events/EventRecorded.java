package io.pivotal.dfrey.scst.scstdemo.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@EqualsAndHashCode( callSuper = true )
@ToString( callSuper = true )
@JsonPropertyOrder({ "eventType", "id", "user", "occurredOn" })
public class EventRecorded extends AbstractDomainEvent {

    @JsonCreator
    public EventRecorded(
            @JsonProperty( "id" ) final UUID id,
            @JsonProperty( "user" ) final String user,
            @JsonProperty( "occurredOn" ) final Instant occurredOn
    ) {
        super( id, user, occurredOn );

    }

    @Override
    @JsonIgnore
    public String eventType() {

        return this.getClass().getSimpleName();
    }

}
