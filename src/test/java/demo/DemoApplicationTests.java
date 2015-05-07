package demo;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void shouldReturnMessage() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/greeting")).andExpect(request().asyncStarted()).andReturn();

		Object result = mvcResult.getAsyncResult();

		mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.content", startsWith("Hello")));

	}

	@Test
	public void shouldReturnMessageWithName() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/greeting?name=Evgeniy")).andExpect(request().asyncStarted())
				.andReturn();

		Object result = mvcResult.getAsyncResult();

		mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.content", startsWith("Hello")))
				.andExpect(jsonPath("$.content", endsWith("Evgeniy!")));

	}

	@Test
	public void shouldReturnBadRequestForABadName() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/greeting?name=Evgeniy1")).andExpect(request().asyncStarted())
				.andReturn();

		Object result = mvcResult.getAsyncResult();

		mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isBadRequest());

	}
}
