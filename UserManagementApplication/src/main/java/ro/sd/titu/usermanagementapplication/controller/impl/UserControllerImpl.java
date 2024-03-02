package ro.sd.titu.usermanagementapplication.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sd.titu.usermanagementapplication.controller.UserController;
import ro.sd.titu.usermanagementapplication.controller.response.UserResponse;
import ro.sd.titu.usermanagementapplication.dto.UserDTO;
import ro.sd.titu.usermanagementapplication.dto.builder.UserBuilder;
import ro.sd.titu.usermanagementapplication.entity.Role;
import ro.sd.titu.usermanagementapplication.service.UserService;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<BigInteger> saveUser(UserDTO userDTO) {
        BigInteger userId = userService.save(userDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BigInteger> updateUser(UserDTO userDTO) {
        BigInteger userId = userService.update(userDTO);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(BigInteger userId) {
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(BigInteger userId) {
        UserDTO userDTO = userService.findById(userId);
        return new ResponseEntity<>(UserBuilder.toUserResponse(userDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserDTO> userDTOS = userService.findAll();
        List<UserResponse> userResponses = userDTOS.stream().map(UserBuilder::toUserResponse).collect(Collectors.toList());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsersByRole(String role) {
        List<UserDTO> userDTOS = userService.findAllByRole(Role.valueOf(role));
        List<UserResponse> userResponses = userDTOS.stream().map(UserBuilder::toUserResponse).collect(Collectors.toList());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }
}
