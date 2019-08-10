package io.pivotal.dfrey.scst.scstdemo;

import io.pivotal.dfrey.scst.scstdemo.events.EventRecorded;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.*;

import static java.util.Collections.singletonList;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class Application {

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private EventLogger eventLogger;

	@Value( "${eventIds}" )
	private String[] eventIds;


	public static void main( String[] args ) {

		SpringApplication.run( Application.class, args );

	}

	@Scheduled( fixedDelay = 5000 )
	public void sendEvents() {

		Random random = new Random();
		UUID eventId = UUID.fromString( eventIds[ random.nextInt( eventIds.length ) ] );
		Instant occurredOn = Instant.now();

		EventRecorded eventRecorded = new EventRecorded( eventId, "test", occurredOn );
		log.debug( "sendEvents : eventRecorded={}", eventRecorded );
		messageSender.processEvents( eventId, singletonList( eventRecorded ) );

	}

	@Scheduled( initialDelay = 1000, fixedDelay = 5000 )
	public void logEvents() {

		eventLogger.logEventsById();

	}

}
