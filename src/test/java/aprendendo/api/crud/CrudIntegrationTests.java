package aprendendo.api.crud;

import aprendendo.api.crud.entities.User;
import aprendendo.api.crud.entities.UserDTO;
import aprendendo.api.crud.entities.UserUpdateDTO;
import aprendendo.api.crud.exceptions.ExceptionsHandler;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrudIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private User user;

    @BeforeEach
    public void setup() {
        this.user = new User("teste","teste@email.com","123");
        this.user.setId(1);
    }
    @Test
    public void addUserRouteTest() {

       ResponseEntity<UserDTO> response = restTemplate.postForEntity("/user",user,UserDTO.class);

       Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
       Assertions.assertEquals(user.getEmail(),response.getBody().getEmail());
       Assertions.assertEquals(user.getName(),response.getBody().getName());
    }

    @Test
    public void findAllUsersTest() {
        List<UserDTO> users = new ArrayList<>();
        users.add(user.toDTO());

        restTemplate.postForEntity("/user",user,UserDTO.class);
        ResponseEntity<UserDTO[]> response = restTemplate.getForEntity("/user",UserDTO[].class);

        Assertions.assertEquals(users.get(0).getEmail(),response.getBody()[0].getEmail());
        Assertions.assertEquals(users.get(0).getName(),response.getBody()[0].getName());
    }

    @Test
    public void updateNameUserTest() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName("update");

        restTemplate.postForEntity("/user",user,UserDTO.class);
        HttpEntity<UserUpdateDTO> httpEntity = new HttpEntity<>(userUpdateDTO);
        ResponseEntity<UserDTO> response = restTemplate.exchange("/user/update/1", HttpMethod.PUT,httpEntity,UserDTO.class);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(userUpdateDTO.getName(),response.getBody().getName());
    }

    @Test
    public void updateUserNotFound() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName("update");

        HttpEntity<UserUpdateDTO> httpEntity = new HttpEntity<>(userUpdateDTO);
        ResponseEntity<UserDTO> response = restTemplate.exchange("/user/update/2", HttpMethod.PUT,httpEntity, UserDTO.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        Assertions.assertEquals(null,response.getBody().getName());
    }
}
