# Spring Cloud Stream Demo

This project will demonstrate issues processing `java.time.ZonedDateTime`.

The _master_ branch is a working version. Dates are stored as `java.time.Instant`.

The _zoneddatetime_ branch is the broken branch. It changes out the `java.time.Instant` 
for a `java.time.ZonedDateTime`. The kafka topics are also changed so as to keep the 
data separate.

## Basic Application

There is a single event, which is an implementation of `io.pivotal.dfrey.scstdemo.events.DomainEvent`.
It simply contains one of 5 UUIDs, an arbitrary user, and the time the event occurred. 
THe _Application_ contains a scheduler that emits events every 5 seconds. There is a lone
message sender `@Component` that sends the events through the message channel.

Writing dates to kafka with Spring Cloud Stream is not in question. Rather, parsing the dates 
when the stream is read. To demonstrate this, a KTable is constructed by reading the event stream
to make it available for querying at a later time.

