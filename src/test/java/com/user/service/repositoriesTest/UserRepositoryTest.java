package com.user.service.repositoriesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.user.service.entities.User;
import com.user.service.repositories.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

	@Mock
	private UserRepository userRepository;
	private User user;
	private List<User> users;

	@BeforeEach
	void setUp() {
		user = new User("1370af0e-d7b2-4fc4-ade4-bd75bee396db","test1", "test1@dev.in" , "test1");
		users = Stream.of(new User("1370af0e-d7b2-4fc4-ade4-bd75bee396db", "test1", "test1@dev.in", "test1"), new User("9ec9e3a5-6e27-4bf3-ae11-63083babcff7", "test2", "test2@dev.in", "test2")).collect(Collectors.toList());
	}
	
	@Test
	@DisplayName("test_save")
	void testSave() {
		when(this.userRepository.save(user)).thenReturn(user);
		User savedUser = this.userRepository.save(user);
		assertThat(savedUser).isNotNull();
	}
	
	@Test
	@DisplayName("test_find_all")
	void testFindAll() {
		
		when(this.userRepository.findAll()).thenReturn(users);
		List<User> allUsersList = this.userRepository.findAll();
		assertThat(allUsersList).isNotNull();
		assertEquals(2, allUsersList.size());
		
	}
	
	@Test
	@DisplayName("test_find_by_id")
	void testFindbyId() {
	
		when(this.userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		User userById = this.userRepository.findById(user.getUserId()).get();
		assertThat(userById).isNotNull();
		assertEquals(user.getUserId(), userById.getUserId());
	
	}
	
	@Test
	@DisplayName("test_delete")
	void testDelete() {
	
		doNothing().when(this.userRepository).delete(user);
		this.userRepository.delete(user);
		Optional<User> user2 = this.userRepository.findById(user.getUserId());
		assertEquals(true, user2.isEmpty());
	}
}
