package devinc.dwitter.security;

import devinc.dwitter.entity.User;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.exception.PasswordIncorrectException;
import devinc.dwitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new PasswordIncorrectException("wrong password");
    }

    public User findByLogin(String login) {
        User entity = userRepository.findByLogin(login);
        if (entity == null) {
            throw new ObjectNotFoundException(User.class.getName() + " object with login " + login + " not found");
        }
        return entity;
    }
}


