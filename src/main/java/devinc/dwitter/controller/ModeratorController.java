package devinc.dwitter.controller;

import devinc.dwitter.entity.dto.RegistrationRequest;
import devinc.dwitter.entity.dto.UserDto;
import devinc.dwitter.service.RegistrationService;
import devinc.dwitter.service.TweetService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ModeratorController {
    private final UserService userService;
    private final TweetService tweetService;
    private final RegistrationService registrationService;

    @PostMapping("/admin/users/") // регистрация модератора
    public UserDto registerModerator(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return registrationService.registerModerator(registrationRequest);
    }

    @DeleteMapping("/admin/users/{id}") // удаляет аккаунт
    public void deleteUser(@PathVariable("id") UUID id, ServletRequest servletRequest) {
        userService.deleteUserByModerator(id, servletRequest);
    }

    @DeleteMapping("/admin/tweets/{id}")
    public void deleteTweet(@PathVariable("id") UUID id, ServletRequest servletRequest) {
        tweetService.deleteTweetByModerator(id, servletRequest);
    }
}