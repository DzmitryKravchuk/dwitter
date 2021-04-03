package devinc.dwitter.service;

import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.AuthRequest;
import devinc.dwitter.entity.dto.AuthResponse;

import javax.servlet.ServletRequest;

public interface AuthService {
    AuthResponse auth(AuthRequest request);
    User getUserFromToken(ServletRequest servletRequest);
}
