package demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.Environment;
import reactor.fn.Function;
import reactor.rx.Stream;
import reactor.rx.Streams;

@Service
public class GreetingService {
	@Autowired
	private Environment env;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	public Stream<Greeting> provideGreetingFor(String name) {
		return Streams.just(name).dispatchOn(env).map(new Function<String, Greeting>() {
			@Override
			public Greeting apply(String t) {
				if (t == null || t.matches(".*\\d+.*"))
					throw new WrongNameException();
				return new Greeting(counter.incrementAndGet(), String.format(template, t));
			}
		});
	}
}
