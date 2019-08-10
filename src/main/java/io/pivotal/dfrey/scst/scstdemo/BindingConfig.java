package io.pivotal.dfrey.scst.scstdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.dfrey.scst.scstdemo.events.DomainEvent;
import io.pivotal.dfrey.scst.scstdemo.serdes.ArrayListSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.MessageChannel;

import java.util.ArrayList;
import java.util.List;

@EnableBinding( BindingConfig.Processor.class )
public class BindingConfig {

    private final Serde<DomainEvent> domainEventSerde;

    BindingConfig( final ObjectMapper objectMapper ) {

        this.domainEventSerde = new JsonSerde<>( DomainEvent.class, objectMapper );

    }

    @StreamListener( BindingConfig.Processor.INPUT_STREAM )
    void process( KStream<?, DomainEvent> events ) {

        events
                .map( (key, value) -> new KeyValue<>( value.id().toString(), value ) )
                .groupByKey( Serialized.with( Serdes.String(), this.domainEventSerde ) )
                .aggregate(
                        ArrayList::new,
                        (key, value, list) -> {
                            list.add( value );
                            return list;
                        },
                        Materialized.<String, List<DomainEvent>, KeyValueStore<Bytes, byte[]>>as( BindingConfig.Processor.EVENTS_BY_ID )
                                .withKeySerde( Serdes.String() )
                                .withValueSerde( new ArrayListSerde( this.domainEventSerde ) )
                );

    }

    interface Processor {

        String OUTPUT = "scst-events-output";
        String INPUT_STREAM = "scst-events-input";
        String EVENTS_BY_ID = "scst-events-by-id";

        @Output( OUTPUT )
        MessageChannel output();

        @Input( INPUT_STREAM )
        KStream<?, ?> inputStream();

    }

}
