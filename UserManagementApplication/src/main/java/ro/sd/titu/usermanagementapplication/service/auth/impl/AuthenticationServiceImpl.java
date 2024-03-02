package ro.sd.titu.usermanagementapplication.service.auth.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.sd.titu.usermanagementapplication.controller.auth.request.AuthRequest;
import ro.sd.titu.usermanagementapplication.controller.auth.request.RegisterRequest;
import ro.sd.titu.usermanagementapplication.controller.auth.response.AuthResponse;
import ro.sd.titu.usermanagementapplication.controller.handlers.exceptions.model.ResourceNotFoundException;
import ro.sd.titu.usermanagementapplication.entity.Role;
import ro.sd.titu.usermanagementapplication.entity.User;
import ro.sd.titu.usermanagementapplication.repository.UserRepository;
import ro.sd.titu.usermanagementapplication.security.config.JwtService;
import ro.sd.titu.usermanagementapplication.service.auth.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        User newUser = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getAddress(),
                request.getDob(),
                Role.CLIENT
        );
        newUser = userRepository.save(newUser);
        LOGGER.info("User with id {} was inserted in db", newUser.getId());
        return new AuthResponse(jwtService.generateToken(getMapId(newUser), newUser));
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + request.getUsername() + " was not found in db"));
        LOGGER.info("User with id {} has logged in", user.getId());
        return new AuthResponse(jwtService.generateToken(getMapId(user), user));
    }

    private Map<String, Object> getMapId(User newUser) {
        Map<String, Object> mapId = new HashMap<>();
        mapId.put("id", newUser.getId());
        return mapId;
    }
}
