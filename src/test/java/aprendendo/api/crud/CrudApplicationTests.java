package aprendendo.api.crud;

import aprendendo.api.crud.entities.User;
import aprendendo.api.crud.entities.UserDTO;
import aprendendo.api.crud.entities.UserUpdateDTO;
import aprendendo.api.crud.repository.UserRepository;
import aprendendo.api.crud.service.UserService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CrudApplicationTests {

	private UserService userService;
	@Mock
	private UserRepository userRepository;

	private User user;

	@BeforeEach
	void init() {
		userService = new UserService(userRepository);
		this.user = new User();
		user.setId(1);
		user.setName("teste");
		user.setEmail("teste@email.com");
		user.setPassword("123");

		List<User> users = new ArrayList<>();
		users.add(user);

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		Mockito.when(userRepository.findAll()).thenReturn(users);
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
	}

	@Test
	public void createUserTest(){
		UserDTO expected = userService.create(user);

		Assertions.assertEquals(expected.getEmail(),user.getEmail());
	}

	@Test
	public void allUsersTest() {

		List<UserDTO> expected = userService.findUsers();

		Assertions.assertEquals(expected.get(0).getEmail(),user.getEmail());
	}

	@Test
	public void deleteUserById() {
		UserDTO expected = userService.deleteUserById(1);

		Assertions.assertEquals(expected.getEmail(),user.getEmail());
	}

	@Test
	public void deleteUserByIdNotFound() {

		RuntimeException exception = Assertions.assertThrows(RuntimeException.class,() -> userService.deleteUserById(2));
		Assertions.assertEquals("User not found",exception.getMessage());
	}

	@Test
	public void updateUserById() {
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
		userUpdateDTO.setName("update");

		UserDTO expected = userService.updateUserById(1,userUpdateDTO);

		Assertions.assertEquals(expected.getName(),user.getName());
	}

	@Test
	public void updateUserByIdNotFound() {
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
		userUpdateDTO.setName("update");

		RuntimeException exception = Assertions.assertThrows(RuntimeException.class,() -> userService.updateUserById(2,userUpdateDTO));
		Assertions.assertEquals("User not found",exception.getMessage());
	}
}
