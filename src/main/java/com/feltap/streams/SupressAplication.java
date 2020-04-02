
package com.feltap.streams;

import static org.apache.kafka.streams.kstream.Suppressed.untilWindowCloses;
import static org.apache.kafka.streams.kstream.Suppressed.BufferConfig.unbounded;

import java.time.Duration;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsStateStore;

@SpringBootApplication
@EnableBinding(SupressBinding.class)
public class SupressAplication {
	public static void main(String[] args) {
		SpringApplication.run(SupressAplication.class, args);
	}

	
	@StreamListener
	@KafkaStreamsStateStore(name = "dummy")
	public void streamProcess(@Input(SupressBinding.INPUT) KStream<String,String> inputStream) {
		
		
		
		inputStream
			
			.groupByKey()
			.windowedBy(TimeWindows.of(Duration.ofMinutes(1)).grace(Duration.ZERO))
			.count()
			.suppress(untilWindowCloses(unbounded()))
			.toStream()
			.peek( (key,value) ->  System.out.println(String.format("%s -> %d", key.toString(),value)  ));
	}

}
