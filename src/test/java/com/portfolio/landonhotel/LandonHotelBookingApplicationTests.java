package com.portfolio.landonhotel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:landon-hotel-test-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.jpa.show-sql=false",
		"debug=false",
		"logging.level.root=INFO"
})
class LandonHotelBookingApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void messagesEndpointReturnsLocalizedWelcomeAndTimeZones() throws Exception {
		mockMvc.perform(get("/api/messages"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.welcomeEnglish", not(emptyOrNullString())))
				.andExpect(jsonPath("$.welcomeFrench", not(emptyOrNullString())))
				.andExpect(jsonPath("$.timeET", not(emptyOrNullString())))
				.andExpect(jsonPath("$.timeMT", not(emptyOrNullString())))
				.andExpect(jsonPath("$.timeUTC", not(emptyOrNullString())));
	}

	@Test
	void roomSearchAndReservationEndpointsWork() throws Exception {
		mockMvc.perform(get("/room/reservation/v1")
						.param("checkin", "2030-06-01")
						.param("checkout", "2030-06-03"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(3)))
				.andExpect(jsonPath("$.content[0].id").exists())
				.andExpect(jsonPath("$.content[0].roomNumber").exists())
				.andExpect(jsonPath("$.content[0].price").exists());

		mockMvc.perform(post("/room/reservation/v1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "roomId": 1,
								  "checkin": "2030-06-01",
								  "checkout": "2030-06-03"
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.checkin").value("2030-06-01"))
				.andExpect(jsonPath("$.checkout").value("2030-06-03"));
	}

}

