package io.pivotal.dfrey.scst.scstdemo;

import io.pivotal.dfrey.scst.scstdemo.events.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
class MessageSender {

    private final MessageChannel output;

    MessageSender( @Qualifier( "scst-events-output" ) final MessageChannel output ) {

        this.output = output;

    }

    @SendTo( BindingConfig.Processor.OUTPUT )
    void processEvents( final UUID eventId, List<DomainEvent> events ) {

        events.forEach( event ->
                output.send(
                        MessageBuilder
                                .withPayload( event )
                                .setHeader( "eventId", eventId )
                                .build()
                )
        );

    }
}
