package com.user.service.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.user.service.entities.User;
import com.user.service.repositories.UserRepository;
import com.user.service.responsedto.ApiResponse;
import com.user.service.services.impl.UserServiceImpl;

@SpringBootTest
@SuppressWarnings("unchecked")
public class UserServiceTest<T> {

	@InjectMocks
	private UserServiceImpl<T> userService;

	@Mock
	private UserRepository userRepository;

	private List<User> users;
	private User user;

	@BeforeEach
	public void setUp() {
		users = Stream
				.of(new User("1370af0e-d7b2-4fc4-ade4-bd75bee396db", "test1", "test1@dev.in", "test1"),
						new User("9ec9e3a5-6e27-4bf3-ae11-63083babcff7", "test2", "test2@dev.in", "test2"))
				.collect(Collectors.toList());
		user = new User("1370af0e-d7b2-4fc4-ade4-bd75bee396db", "test1", "test1@dev.in", "test1");
	}

	@Test
	@DisplayName("test_get_all_users")
	public void getAllUsersTest() {
		when(userRepository.findAll()).thenReturn(users);
		List<User> allUsers = (List<User>) this.userService.getAllUsers();
		assertEquals(2, allUsers.size());
	}

	@Test
	@DisplayName("test_get_user_By_id")
	public void getUserByIdTest() {
		String userId = "1370af0e-d7b2-4fc4-ade4-bd75bee396db";
		when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
		User user2 = (User) this.userService.getUserById(userId);

		assertEquals(user, user2);
	}

	@Test
	@DisplayName("test_delete_user")
	public void testDeleteUser() {
		
		when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		doNothing().when(this.userRepository).delete(user);
		ApiResponse actualResult = (ApiResponse) this.userService.deleteUser(user.getUserId());
		assertEquals(HttpStatus.OK, actualResult.getStatus());
		assertEquals("User Deleted Successfully !!!", actualResult.getMessage());
		assertEquals(true, actualResult.isSuccess());
		
	}

	@Test
	@DisplayName("test_create_user")
	public void testCreateUser() {
		when(userRepository.save(user)).thenReturn(user);
		User createUser = (User) this.userService.createUser(user);
		assertThat(createUser).isNotEqualTo(null);
//		assertThat(createUser.getUserId()).isGreaterThan("0");
		assertTrue(createUser.getUserId().length() > 0 );
	}

	@Test
	@DisplayName("test_update_user")
	public void testUpdateUser() {
		when(this.userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		when(this.userRepository.save(user)).thenReturn(user);
		User updateUser = (User) this.userService.updateUser(user, user.getUserId());
		assertEquals(user.getUserId(), updateUser.getUserId());
	}
}
