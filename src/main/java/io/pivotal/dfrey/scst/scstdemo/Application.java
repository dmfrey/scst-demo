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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class Application {

	@Autowired
	private MessageSender messageSender;

	@Value( "${eventIds}" )
	private String[] eventIds;


	public static void main( String[] args ) {

		SpringApplication.run( Application.class, args );

	}

	@Scheduled( fixedDelay = 5000 )
	public void sendEvents() {

		Random random = new Random();
		UUID eventId = UUID.fromString( eventIds[ random.nextInt( eventIds.length - 1 ) ] );
		Instant occurredOn = Instant.now();

		EventRecorded eventRecorded = new EventRecorded( eventId, "test", occurredOn );
		log.info( "sendEvents : eventRecorded={}", eventRecorded );

	}

}
