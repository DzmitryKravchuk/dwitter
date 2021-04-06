package devinc.dwitter.service.impl;

import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.RegistrationRequest;
import devinc.dwitter.entity.dto.UserDto;
import devinc.dwitter.exception.NotExistingEMailException;
import devinc.dwitter.exception.NotUniqueUserLoginException;
import devinc.dwitter.exception.NotUniqueUserNameException;
import devinc.dwitter.service.RegistrationService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;

    @Override
    public UserDto registerUser(RegistrationRequest registrationRequest) throws NotExistingEMailException,
            NotUniqueUserLoginException, NotUniqueUserNameException {
        User u = new User();
        validate(registrationRequest);
        u.setPassword(registrationRequest.getPassword());
        u.setLogin(registrationRequest.getLogin());
        u.setName(registrationRequest.getName());
        userService.saveUser(u);

        UserDto uDto = new UserDto(u.getId());
        return uDto;
    }

    private void validate(RegistrationRequest registrationRequest) {
        final String login = registrationRequest.getLogin();
        final String name = registrationRequest.getName();
        if (!EmailValidator.getInstance().isValid(login)) {
            throw new NotExistingEMailException("login must be a valid eMail-address");
        }

        List<User> users = userService.getAll();
        List<String> registeredUserLogins = users.stream().map(u -> u.getLogin()).collect(Collectors.toList());
        if (registeredUserLogins.contains(login)) {
            throw new NotUniqueUserLoginException("login must be unique");
        }

        List<String> registeredUsernames = users.stream().map(u -> u.getName()).collect(Collectors.toList());
        if (registeredUsernames.contains(name)) {
            throw new NotUniqueUserNameException("userName must be unique");
        }
    }

}
