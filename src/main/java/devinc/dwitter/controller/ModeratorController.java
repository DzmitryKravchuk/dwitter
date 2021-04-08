package devinc.dwitter.controller;

import devinc.dwitter.entity.dto.RegistrationRequest;
import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.entity.dto.UserDto;
import devinc.dwitter.service.RegistrationService;
import devinc.dwitter.service.TweetService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public  UserDto registerModerator(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return registrationService.registerModerator(registrationRequest);
    }

    @DeleteMapping("/admin/users/{id}") // удаляет аккаунт
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") UUID id, ServletRequest servletRequest) {
        userService.deleteUserByModerator(id, servletRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/admin/tweets/{id}")
    public ResponseEntity<TweetDto> deleteTweet(@PathVariable("id") UUID id, ServletRequest servletRequest) {
        tweetService.deleteTweetByModerator(id, servletRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
