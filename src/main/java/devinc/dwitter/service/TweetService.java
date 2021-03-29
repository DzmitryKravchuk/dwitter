package devinc.dwitter.service;

import devinc.dwitter.entity.Topic;
import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.User;

import java.util.List;
import java.util.UUID;

public interface TweetService {
    Tweet getById(UUID id);

    void save(Tweet entity);

    List<Tweet> getAll();

    void delete(UUID id);

    Tweet createTweet(UUID userId, String s, UUID topicId, UUID repostedTweetId);
}
