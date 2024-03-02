package ro.sd.titu.usermanagementapplication.controller.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.sd.titu.usermanagementapplication.controller.auth.request.AuthRequest;
import ro.sd.titu.usermanagementapplication.controller.auth.request.RegisterRequest;
import ro.sd.titu.usermanagementapplication.controller.auth.response.AuthResponse;

@RequestMapping(path = "/auth")
public interface AuthenticationController {
    @PostMapping(path = "/register")
    ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request);

    @PostMapping(path = "/login")
    ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request);
}
