/**
 * 
 */
package demo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import reactor.bus.EventBus;
import reactor.spring.context.config.EnableReactor;
import reactor.spring.webmvc.PromiseHandlerMethodReturnValueHandler;

@Configuration
@EnableReactor
public class DemoAppConfiguration {

	public @Bean EventBus reactor() {
		final EventBus ev = EventBus.config().get();
		return ev;
	}

	public @Bean HandlerMethodReturnValueHandler promiseHandler() {
		return new PromiseHandlerMethodReturnValueHandler();
	}

	@Resource
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@PostConstruct
	public void init() {
		final List<HandlerMethodReturnValueHandler> originalHandlers = new ArrayList<>(
				requestMappingHandlerAdapter.getReturnValueHandlers());
		originalHandlers.add(0, promiseHandler());
		requestMappingHandlerAdapter.setReturnValueHandlers(originalHandlers);
	}
}
