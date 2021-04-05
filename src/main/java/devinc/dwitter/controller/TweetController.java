package devinc.dwitter.controller;

import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;


    @PostMapping("/user/tweets")
    public ResponseEntity<TweetDto> createTweet(@RequestBody TweetDto dto, ServletRequest servletRequest) {
        HttpHeaders headers = new HttpHeaders();
        tweetService.createTweetWithToken(dto, servletRequest);
        return new ResponseEntity<>(dto, headers, HttpStatus.OK);
    }

    @GetMapping("/user/tweets/{id}")
    public ResponseEntity<TweetDto> getTweet(@PathVariable("id") UUID id) {
        TweetDto dto = tweetService.getTweetDtoById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
