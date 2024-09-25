package com.edvinas.user;

import com.edvinas.user.controller.UserController;
import com.edvinas.user.entity.User;
import com.edvinas.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;



import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class UserServiceApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testCreateUser() throws Exception {
		User user = new User(1L, "Jonas", "Jonaitis", "jonas.jonaitis@test.com", 1L);
		given(userService.saveUser(user)).willReturn(user);

		mockMvc.perform(post("/users/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Jonas"));
	}

	@Test
	public void testGetUser() throws Exception {

		Long userId = 1L;
		User newUser = new User(userId, "Jonas", "Jonaitis", "jonas.jonaitis@test.com", 1L);

		// Posts a new user
		mockMvc.perform(post("/users/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(newUser)))
				.andExpect(status().isOk());

		// Gets the new user
		mockMvc.perform(get("/users/{id}", userId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testDeleteUser() throws Exception {
		Long userId = 1L;
		doNothing().when(userService).deleteUser(userId);

		mockMvc.perform(delete("/users/{id}", userId))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUser() throws Exception {
		Long userId = 1L;
		User user = new User(userId, "Jonas", "Jonaitis", "jonas.jonaitis@test.com", 1L);

		given(userService.updateUser(userId, user)).willReturn(user);

		mockMvc.perform(put("/users/{id}", userId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(content().string("User with ID " + userId + " updated successfully."));
	}

}
