package ro.sd.titu.usermanagementapplication.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ro.sd.titu.usermanagementapplication.dto.UserDTO;
import ro.sd.titu.usermanagementapplication.dto.builder.UserBuilder;
import ro.sd.titu.usermanagementapplication.entity.Role;
import ro.sd.titu.usermanagementapplication.entity.User;
import ro.sd.titu.usermanagementapplication.repository.UserRepository;
import ro.sd.titu.usermanagementapplication.security.config.JwtService;
import ro.sd.titu.usermanagementapplication.service.UserService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WebClient webClient;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, WebClient webClient, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.webClient = webClient;
        this.jwtService = jwtService;
    }

    public BigInteger save(UserDTO userDTO) {
        userDTO.setRole(Role.CLIENT);
        User user = UserBuilder.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        LOGGER.info("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public BigInteger update(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());
        if (optionalUser.isEmpty()) {
            LOGGER.error("User to be updated with id {} was not found in db", userDTO.getId());
            throw new ResourceNotFoundException("User with id " + userDTO.getId() + " does not exist");
        }
        User oldUser = optionalUser.get();
        User updatedUser = UserBuilder.toEntity(userDTO);
        updatedUser.setId(userDTO.getId());
        if (userDTO.getPassword() == null) {
            updatedUser.setPassword(oldUser.getPassword());
        } else {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (userDTO.getRole() == null) {
            updatedUser.setRole(oldUser.getRole());
        }
        updatedUser = userRepository.save(updatedUser);
        LOGGER.info("User with id {} was updated", updatedUser.getId());
        return updatedUser.getId();
    }

    public void deleteById(BigInteger id) {
        if (!userRepository.existsById(id)) {
            LOGGER.error("User to be deleted with id {} was not found in db", id);
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }
        try {
            deleteAllDevicesOfUserId(id);
        } catch (ResourceNotFoundException ignored) {
        }
        userRepository.deleteById(id);
        LOGGER.info("User with id {} was deleted from db", id);
    }

    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(BigInteger id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }
        return UserBuilder.toUserDTO(optionalUser.get());
    }

    public UserDTO findByUsername(String username) {
        Optional<User> personOptional = userRepository.findByUsername(username);
        if (personOptional.isEmpty()) {
            LOGGER.error("User with username {} was not found in db", username);
            throw new ResourceNotFoundException("User with username " + username + " does not exist");
        }
        return UserBuilder.toUserDTO(personOptional.get());
    }

    @Override
    public List<UserDTO> findAllByRole(Role role) {
        List<User> users = userRepository.findAllByRole(role);
        return users.stream().map(UserBuilder::toUserDTO).collect(Collectors.toList());
    }

    private void deleteAllDevicesOfUserId(BigInteger id) {
        webClient.delete()
                .uri("/devices/user/{id}", id)
                .header("Authorization", "Bearer " + jwtService.getCurrToken())
                .retrieve()
                .onStatus(httpStatusCode ->
                                !httpStatusCode.is2xxSuccessful(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("User with id " + id + " was not found in db"))
                )
                .bodyToMono(String.class)
                .block();
    }
}
