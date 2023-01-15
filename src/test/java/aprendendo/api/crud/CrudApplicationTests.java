package aprendendo.api.crud;

import aprendendo.api.crud.entities.User;
import aprendendo.api.crud.entities.UserDTO;
import aprendendo.api.crud.repository.UserRepository;
import aprendendo.api.crud.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
		user.setName("teste");
		user.setEmail("teste@email.com");
		user.setPassword("123");

		List<User> users = new ArrayList<>();
		users.add(user);

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		Mockito.when(userRepository.findAll()).thenReturn(users);
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
}
