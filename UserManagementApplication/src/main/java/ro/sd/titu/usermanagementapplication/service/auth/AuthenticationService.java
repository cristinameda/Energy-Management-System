package ro.sd.titu.usermanagementapplication.service.auth;

import ro.sd.titu.usermanagementapplication.controller.auth.request.AuthRequest;
import ro.sd.titu.usermanagementapplication.controller.auth.request.RegisterRequest;
import ro.sd.titu.usermanagementapplication.controller.auth.response.AuthResponse;

public interface AuthenticationService {
    AuthResponse register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);
}
