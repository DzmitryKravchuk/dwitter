package devinc.dwitter.controller;

import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @GetMapping("/tweets/{id}")
    public ResponseEntity<TweetDto> getTweet(@PathVariable("id") UUID id) {
        TweetDto tweet = tweetService.getTweetDtoById(id);
        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }

    @GetMapping("/tweets/reposts/{id}")
    public ResponseEntity<List<TweetDto>> getRepostsOfTweet(@PathVariable("id") UUID id) {
        List<TweetDto> tweetList = tweetService.getAllRepostsDto(id);
        return new ResponseEntity<>(tweetList, HttpStatus.OK);
    }

    @GetMapping("/tweets/user/{id}")
    public ResponseEntity<List<TweetDto>> getTweetsOfUser(@PathVariable("id") UUID id) {
        List<TweetDto> tweetList = tweetService.getTweetDtoListByUserId(id);
        return new ResponseEntity<>(tweetList, HttpStatus.OK);
    }

}
