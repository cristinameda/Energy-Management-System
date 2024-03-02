package ro.sd.titu.usermanagementapplication.controller.auth.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sd.titu.usermanagementapplication.controller.auth.AuthenticationController;
import ro.sd.titu.usermanagementapplication.controller.auth.request.AuthRequest;
import ro.sd.titu.usermanagementapplication.controller.auth.request.RegisterRequest;
import ro.sd.titu.usermanagementapplication.controller.auth.response.AuthResponse;
import ro.sd.titu.usermanagementapplication.service.auth.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationControllerImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AuthResponse> authenticate(AuthRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }
}
