package com.feltap.streams;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface SupressBinding {
	String INPUT = "input";
	@Input(INPUT)
	KStream<?,?> process(); 
}
