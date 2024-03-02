package ro.sd.titu.usermanagementapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.sd.titu.usermanagementapplication.controller.response.UserResponse;
import ro.sd.titu.usermanagementapplication.dto.UserDTO;
import ro.sd.titu.usermanagementapplication.dto.validator.group.FullValidation;
import ro.sd.titu.usermanagementapplication.dto.validator.group.UpdateValidation;

import java.math.BigInteger;
import java.util.List;

public interface UserController {
    @PostMapping()
    ResponseEntity<BigInteger> saveUser(@Validated(FullValidation.class) @RequestBody UserDTO userDTO);

    @PutMapping()
    ResponseEntity<BigInteger> updateUser(@Validated({UpdateValidation.class, FullValidation.class}) @RequestBody UserDTO userDTO);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteUserById(@PathVariable("id") BigInteger userId);

    @GetMapping(value = "/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") BigInteger userId);

    @GetMapping()
    ResponseEntity<List<UserResponse>> getUsers();

    @GetMapping(value = "/role/{role}")
    ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable("role") String role);
}
