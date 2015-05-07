/**
 * 
 */
package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.Environment;
import reactor.bus.EventBus;
import reactor.fn.Function;
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
	public Promise<ResponseEntity<?>> greeting(final @RequestParam(value = "name", defaultValue = "World") String name) {
		return greetingService.provideGreetingFor(name).map(new Function<Greeting, ResponseEntity<?>>() {
			@Override
			public ResponseEntity<?> apply(Greeting t) {
				return new ResponseEntity<>(t, HttpStatus.OK);
			}
		}).onErrorReturn(WrongNameException.class, new Function<WrongNameException, ResponseEntity<?>>() {
			@Override
			public ResponseEntity<?> apply(WrongNameException t) {
				System.out.println(">>>>>>>>>>>>>>>>>> " + t.getMessage() + " <<<<<<<<<<<<<<<<<<<<");
				return new ResponseEntity<>(t.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}).next();
	}
}
