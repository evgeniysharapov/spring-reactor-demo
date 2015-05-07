/**
 * 
 */
package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.Environment;
import reactor.bus.EventBus;
import reactor.rx.Promise;

@RestController
public class GreetingController {
	@Autowired
	private Environment env;
	@Autowired
	private EventBus eventBus;

	@Autowired
	private GreetingService greetingService;

	@RequestMapping("/greeting")
	public Promise<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return greetingService.provideGreetingFor(name).next();
	}
}
