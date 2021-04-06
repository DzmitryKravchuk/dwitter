package devinc.dwitter.controller;

import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.entity.dto.TweetLikeDto;
import devinc.dwitter.entity.dto.UserDto;
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

    @PutMapping("/user/users") // подписаться на юзера
    public ResponseEntity<UserDto> subscribe(@RequestBody UserDto dto, ServletRequest servletRequest) {
        HttpHeaders headers = new HttpHeaders();
        userService.subscribeWithToken(dto, servletRequest);
        return new ResponseEntity<>(dto, headers, HttpStatus.OK);
    }

    @GetMapping("/user/users/{userName}") // поиск юзера по никнейму
    public ResponseEntity<List<TweetDto>> getTweet(@PathVariable("userName") String userName) {
        List<TweetDto> dtoList = null;
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping("/user/users/{id}") // юзер удаляет свой аккаунт
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") UUID id, ServletRequest servletRequest) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
