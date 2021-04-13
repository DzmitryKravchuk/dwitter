package devinc.dwitter.controller;

import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @GetMapping("/tweets/{id}")
    public TweetDto getTweet(@PathVariable("id") UUID id) {
        return tweetService.getTweetDtoById(id);
    }

    @GetMapping("/tweets/reposts/{id}")
    public List<TweetDto> getRepostsOfTweet(@PathVariable("id") UUID id) {
        return tweetService.getAllRepostsDto(id);
    }

    @GetMapping("/tweets/user/{id}")
    public List<TweetDto> getTweetsOfUser(@PathVariable("id") UUID id) {
        return tweetService.getTweetDtoListByUserId(id);
    }
}
