package com.user.service.controllersTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.service.entities.User;
import com.user.service.responsedto.ApiResponse;
import com.user.service.services.UserService;

@SpringBootTest
public class UserControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	private User user;
	private List<User> users;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private UserService userService;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		user = new User("9ec9e3a5-6e27-4bf3-ae11-63083babcff7", "test1", "test1@dev.in", "test1");
		users = Stream
				.of(new User("1370af0e-d7b2-4fc4-ade4-bd75bee396db", "test1", "test1@dev.in", "test1"),
						new User("9ec9e3a5-6e27-4bf3-ae11-63083babcff7", "test2", "test2@dev.in", "test2"))
				.collect(Collectors.toList());
	}

	@Test
	@DisplayName("test_create_user")
	public void createUserTest() throws Exception {

		String jsonRequest = mapper.writeValueAsString(user);

		when(this.userService.createUser(user)).thenReturn(user);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/users/create").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.jsonPath("$.userEmail", Matchers.equalTo(user.getUserEmail())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.notNullValue()))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("test_update_user")
	public void updateUserTest() throws Exception {

		User updatedUser = new User("9ec9e3a5-6e27-4bf3-ae11-63083babcff7", "test2", "test2@dev.in", "test2");

		String jsonRequest = mapper.writeValueAsString(user);

		when(this.userService.updateUser(updatedUser, updatedUser.getUserId())).thenReturn(updatedUser);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put("/api/v1/users/edit/9ec9e3a5-6e27-4bf3-ae11-63083babcff7").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andDo(print())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.equalTo(updatedUser.getUserId())))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.equalTo("test2")))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	@DisplayName("test_delete_user")
	public void deleteUserTest() throws Exception {
		
		when(this.userService.deleteUser(user.getUserId())).thenReturn(new ApiResponse("User Deleted Successfully !!!", true, HttpStatus.OK));
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/users/remove/9ec9e3a5-6e27-4bf3-ae11-63083babcff7");
		mockMvc.perform(request)
			.andDo(print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("User Deleted Successfully !!!")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.equalTo(true)))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("test_get_all_users")
	public void getAllUsersTest() throws Exception {
		
		when(this.userService.getAllUsers()).thenReturn(List.of(users.get(0), users.get(1)));
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/users/get-all").accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
			.andDo(print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].userEmail").value("test1@dev.in"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].userName").value("test1"))
            .andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		
	}
	
	@Test
	@DisplayName("test_get_user_by_id")
	public void getUserById() throws Exception {
		
		when(this.userService.getUserById(user.getUserId())).thenReturn(users.get(0));
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/users/get/9ec9e3a5-6e27-4bf3-ae11-63083babcff7").accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
			.andDo(print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.userEmail").value("test1@dev.in"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("test1"))
            .andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		
	}
}
