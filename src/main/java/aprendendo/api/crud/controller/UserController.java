package aprendendo.api.crud.controller;

import aprendendo.api.crud.entities.User;
import aprendendo.api.crud.entities.UserDTO;
import aprendendo.api.crud.entities.UserUpdateDTO;
import aprendendo.api.crud.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.create(user));
    }

    @GetMapping()
    public  ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findUsers());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUserById(id,userUpdateDTO));
    }
}
