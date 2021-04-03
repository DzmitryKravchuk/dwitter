package devinc.dwitter.controller;

import devinc.dwitter.entity.dto.NewTweetDto;
import devinc.dwitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@RestController
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;


    @PostMapping("/user/tweets")
    public ResponseEntity<NewTweetDto> createTweet(@RequestBody NewTweetDto dto, ServletRequest servletRequest) {
        HttpHeaders headers = new HttpHeaders();
        tweetService.createTweetWithToken(dto, servletRequest);
        return new ResponseEntity<>(dto,headers, HttpStatus.OK);
    }
}
