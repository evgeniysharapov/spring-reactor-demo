/**
 * 
 */
package demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.Environment;
import reactor.rx.Promise;
import reactor.rx.Promises;

@RestController
public class GreetingController {
	@Autowired
	private Environment env;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Promise<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		Promise<Greeting> p = Promises.prepare(env);
		p.accept(new Greeting(counter.incrementAndGet(), String.format(template, name)));
		return p;
	}
}
