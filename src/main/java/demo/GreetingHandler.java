/**
 * 
 */
package demo;

import static reactor.spring.context.annotation.SelectorType.URI;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.bus.EventBus;
import reactor.rx.Promise;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.ReplyTo;
import reactor.spring.context.annotation.Selector;

/**
 * This is a layer between business logic and front-end
 * 
 * @author esharapov
 *
 */
@Consumer
public class GreetingHandler {
	@Autowired
	private EventBus eventBus;

	@Autowired
	private GreetingService greetingService;

	@Selector(type = URI, value = "/provideGreeting")
	@ReplyTo
	public void provideGreetingFor(Promise<Greeting> p) {
		p.accept(greetingService.provideGreetingFor("test"));
	}
}
