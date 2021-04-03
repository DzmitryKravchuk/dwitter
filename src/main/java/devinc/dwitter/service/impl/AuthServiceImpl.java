package devinc.dwitter.service.impl;

import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.AuthRequest;
import devinc.dwitter.entity.dto.AuthResponse;
import devinc.dwitter.security.jvt.JwtFilter;
import devinc.dwitter.security.jvt.JwtProvider;
import devinc.dwitter.service.AuthService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;
    private final UserService userService;


    @Override
    public AuthResponse auth(AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }

    @Override
    public User getUserFromToken(ServletRequest servletRequest) {
        String token = jwtFilter.getTokenFromRequest((HttpServletRequest) servletRequest);
        User user = null;
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            user = userService.findByLogin(userLogin);
        }
        return user;
    }
}
