package devinc.dwitter.controller;

import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.entity.dto.TweetLikeDto;
import devinc.dwitter.entity.dto.UserDto;
import devinc.dwitter.service.SubscriptionService;
import devinc.dwitter.service.TweetService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final TweetService tweetService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @PutMapping("/user/users") // подписаться на юзера
    public ResponseEntity<UserDto> subscribe(@RequestBody UserDto dto, ServletRequest servletRequest) {
        HttpHeaders headers = new HttpHeaders();
        subscriptionService.subscribeWithToken(dto, servletRequest);
        return new ResponseEntity<>(dto, headers, HttpStatus.OK);
    }

    @GetMapping("/user/users/{userName}") // поиск юзера по никнейму
    public ResponseEntity<List<User>> getTweet(@PathVariable("userName") String userName) {
        List<User> dtoList = userService.getByUserName(userName);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping("/user/users/{id}") // юзер удаляет свой аккаунт
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") UUID id, ServletRequest servletRequest) {
        userService.deleteUserWithToken(id, servletRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
