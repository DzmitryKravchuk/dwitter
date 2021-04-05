package devinc.dwitter.service;

import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.dto.TweetDto;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.UUID;

public interface TweetService {
    Tweet getById(UUID id);

    void save(Tweet entity);

    List<Tweet> getAll();

    void delete(UUID id);

    Tweet createTweet(TweetDto tweetDto, UUID UserId);

    void likeTweet(UUID UserId, UUID tweetId);

    List<Tweet> getAllReposts(UUID tweetId);

    List<Tweet> getAllTweetsOfTopic(UUID topicId);

    List<Tweet> getAllTweetsOfUsersSubscribedToList(UUID subscriberId);

    void createTweetWithToken(TweetDto dto, ServletRequest servletRequest);

    TweetDto getTweetDtoById(UUID id);
}
