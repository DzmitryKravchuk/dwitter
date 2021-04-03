package devinc.dwitter.controller;

import devinc.dwitter.entity.dto.AuthRequest;
import devinc.dwitter.entity.dto.AuthResponse;
import devinc.dwitter.entity.dto.RegistrationRequest;
import devinc.dwitter.entity.dto.UserDto;
import devinc.dwitter.service.AuthService;
import devinc.dwitter.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final AuthService authService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return registrationService.registerUser(registrationRequest);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        return authService.auth(request);
    }
}

