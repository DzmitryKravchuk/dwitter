package devinc.dwitter.service;

import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.entity.dto.TweetLikeDto;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.UUID;

public interface TweetService {
    Tweet getById(UUID id);

    TweetDto getTweetDtoById(UUID id);

    void save(Tweet entity);

    List<Tweet> getAll();

    void delete(UUID id);

    Tweet createTweet(TweetDto tweetDto, UUID UserId);

    void likeTweet(TweetLikeDto dto, UUID UserId);

    List<Tweet> getAllReposts(UUID tweetId);

    List<Tweet> getAllTweetsOfTopic(UUID topicId);

    List<Tweet> getTweetListByUserId(UUID id);

    List<Tweet> getTweetFeed(UUID subscriberId);

    void createTweetWithToken(TweetDto dto, ServletRequest servletRequest);

    void likeTweetWithToken(TweetLikeDto dto, ServletRequest servletRequest);

    void deleteTweetWithToken(UUID id, ServletRequest servletRequest);

    void deleteTweetByModerator(UUID id, ServletRequest servletRequest);

    List<TweetDto> getTweetDtoListByUserId(UUID id);

    List<TweetDto> getAllRepostsDto(UUID id);

    List<TweetDto> getTweetFeedDtoWithToken(ServletRequest servletRequest);
}
