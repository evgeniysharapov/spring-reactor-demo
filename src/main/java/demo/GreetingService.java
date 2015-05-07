package demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	public Greeting provideGreetingFor(String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
