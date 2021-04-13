package devinc.dwitter.repository;

import devinc.dwitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
    @Query(value = "select * from tweet where tweet.reposted_tweet_id=?1", nativeQuery = true)
    List<Tweet> getAllReposts(UUID tweetId);

    @Query(value = "select * from tweet where tweet.topic_id=?1", nativeQuery = true)
    List<Tweet> getAllTweetsOfTopic(UUID topicId);

    @Query(value = "select * from tweet where tweet.user_account_id=?1", nativeQuery = true)
    List<Tweet> findTweetsByUserId(UUID id);

    @Modifying
    @Query(value ="delete from tweet where tweet.user_account_id=?1", nativeQuery = true)
    void deleteTweetsByUserId(UUID id);

    @Modifying
    @Query(value ="delete from tweet where tweet.reposted_tweet_id=?1", nativeQuery = true)
    void deleteRepostsByTweetId(UUID tweetId);
}
