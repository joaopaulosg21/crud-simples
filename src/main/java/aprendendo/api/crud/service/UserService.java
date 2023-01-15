package aprendendo.api.crud.service;

import aprendendo.api.crud.entities.User;
import aprendendo.api.crud.entities.UserDTO;
import aprendendo.api.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService(){}

    public UserDTO create(User user) {
        return userRepository.save(user).toDTO();
    }

    public List<UserDTO> findUsers() {
        return userRepository.findAll().stream().map((user)->user.toDTO()).toList();
    }

}
