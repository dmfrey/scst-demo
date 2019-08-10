package io.pivotal.dfrey.scst.scstdemo.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventType",
        defaultImpl = DomainEventIgnored.class
)
@JsonSubTypes({
        @JsonSubTypes.Type( value = EventRecorded.class, name = "EventRecorded" )
})
public interface DomainEvent {

    UUID id();

    String user();

    Instant occurredOn();

    String eventType();

}
