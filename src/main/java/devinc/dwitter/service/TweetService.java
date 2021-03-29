package devinc.dwitter.service;

import devinc.dwitter.entity.Tweet;

import java.util.List;
import java.util.UUID;

public interface TweetService {
    Tweet getById(UUID id);

    void save(Tweet entity);

    List<Tweet> getAll();

    void delete(UUID id);

    Tweet createTweet(UUID userId, String s, UUID topicId, UUID repostedTweetId);

    void likeTweet(UUID UserId, UUID tweetId);

    List<Tweet> getAllReposts(UUID tweetId);
}
