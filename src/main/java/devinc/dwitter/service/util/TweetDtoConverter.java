package devinc.dwitter.service.util;

import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.dto.TweetDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TweetDtoConverter {
    public TweetDto convertToDto(Tweet tweet) {
        UUID repostedTweetId = null;
        String topic = null;
        if (tweet.getRepostedTweet() != null) {
            repostedTweetId = tweet.getRepostedTweet().getId();
        }
        if (tweet.getTopic() != null) {
            topic = tweet.getTopic().getTopic();
        }
        return new TweetDto(tweet.getContent(), repostedTweetId, topic);
    }
}
