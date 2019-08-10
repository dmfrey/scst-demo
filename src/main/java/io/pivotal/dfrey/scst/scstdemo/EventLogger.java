package io.pivotal.dfrey.scst.scstdemo;

import io.pivotal.dfrey.scst.scstdemo.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventLogger {

    private final InteractiveQueryService interactiveQueryService;

    public void logEventsById() {

        final ReadOnlyKeyValueStore<String, List<DomainEvent>> eventStore =
                interactiveQueryService.getQueryableStore("scst-events-by-id",
                        QueryableStoreTypes.<String, List<DomainEvent>>keyValueStore() );

        eventStore.all()
                .forEachRemaining( kv -> {

                    log.info( "logEventsById : eventId [{}], events={}", kv.key, kv.value );

                });

    }

}