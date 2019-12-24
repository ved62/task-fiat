package ru.ved.taskfiat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.ved.taskfiat.model.Quote;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TaskFiatApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		// not implemented yet
	}

	@Test
	public void printTestData() throws Exception {
		this.mockMvc.perform(get("/quotes"))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	public void testFindByIsin() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
	      .get("/quotes/{isin}", "TESTQUOTE102")
	      .accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.isin").value("TESTQUOTE102"));
	}

	@Test
	public void testAddAndUpdateNewQuoteElvl() throws Exception {
		// add new
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/quotes")
				.content(asJsonString(new Quote("TESTQUOTE103", 100.2, 113.5, 100, 100)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		// check that elvl = bid
		this.mockMvc.perform(MockMvcRequestBuilders
			      .get("/quotes/{isin}", "TESTQUOTE103")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.elvl").value(100.2));
		// update and check that elvl = new bid
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/quotes")
				.content(asJsonString(new Quote("TESTQUOTE103", 120.2, 123.5, 100, 100)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.elvl").value(120.2))
				.andExpect(status().isOk());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
