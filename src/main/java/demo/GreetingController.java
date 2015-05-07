/**
 * 
 */
package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.Environment;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.rx.Promise;
import reactor.rx.Promises;

@RestController
public class GreetingController {
	@Autowired
	private Environment env;
	@Autowired
	private EventBus eventBus;

	@RequestMapping("/greeting")
	public Promise<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		Promise<Greeting> p = Promises.prepare(env);
		eventBus.notify("/provideGreeting", Event.wrap(p));
		return p;
	}
}
