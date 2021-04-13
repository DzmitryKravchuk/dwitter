package devinc.dwitter.controller;

import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.entity.dto.TweetLikeDto;
import devinc.dwitter.entity.dto.UserDto;
import devinc.dwitter.service.SubscriptionService;
import devinc.dwitter.service.TweetService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final TweetService tweetService;

    @GetMapping("/user/tweetFeed")
    public List<TweetDto> getTweetFeedByUserId(ServletRequest servletRequest) {
        return tweetService.getTweetFeedDtoWithToken(servletRequest);
    }

    @PostMapping("/user/tweets")
    public TweetDto createTweet(@RequestBody TweetDto dto, ServletRequest servletRequest) {
        tweetService.createTweetWithToken(dto, servletRequest);
        return dto;
    }

    @PutMapping("/user/tweets")
    public TweetLikeDto likeTweet(@RequestBody TweetLikeDto dto, ServletRequest servletRequest) {
        tweetService.likeTweetWithToken(dto, servletRequest);
        return dto;
    }

    @DeleteMapping("/user/tweets/{id}")
    public void deleteTweet(@PathVariable("id") UUID id, ServletRequest servletRequest) {
        tweetService.deleteTweetWithToken(id, servletRequest);
    }

    @PutMapping("/user/users") // подписаться на юзера
    public UserDto subscribe(@RequestBody UserDto dto, ServletRequest servletRequest) {
        subscriptionService.subscribeWithToken(dto, servletRequest);
        return dto;
    }

    @GetMapping("/user/users/{userName}") // поиск юзера по нику
    public List<User> getUserByNick(@PathVariable("userName") String userName) {
        return userService.getByUserName(userName);
    }

    @DeleteMapping("/user/users/{id}") // юзер удаляет свой аккаунт
    public void deleteUser(@PathVariable("id") UUID id, ServletRequest servletRequest) {
        userService.deleteUserWithToken(id, servletRequest);
    }
}
