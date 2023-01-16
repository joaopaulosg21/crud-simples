package aprendendo.api.crud.service;

import aprendendo.api.crud.entities.User;
import aprendendo.api.crud.entities.UserDTO;
import aprendendo.api.crud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO create(User user) {
        return userRepository.save(user).toDTO();
    }

    public List<UserDTO> findUsers() {
        return userRepository.findAll().stream().map((user)->user.toDTO()).toList();
    }

    public UserDTO deleteUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            userRepository.deleteById(id);
            return optionalUser.get().toDTO();
        }
        throw new RuntimeException("User not found");
    }

}
