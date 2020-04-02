package com.feltap.streams;

import java.time.Duration;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;

public class DummyTransformer implements Transformer<String, String, KeyValue<String, String>> {

	private ProcessorContext context;

	@Override
	public void init(ProcessorContext context) {
		this.context = context;
		this.context.schedule(Duration.ofSeconds(30), PunctuationType.WALL_CLOCK_TIME, (timestamp) -> {
			System.out.println("Forward");
			context.forward("dummy", "dummy");

		});
	}

	@Override
	public KeyValue<String, String> transform(String key, String value) {
		return new KeyValue<String, String>(key, value);
	}

	@Override
	public void close() {

	}

}
