package devinc.dwitter.repository;

import devinc.dwitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
    @Query(value = "select * from tweet where tweet.reposted_tweet_id=?1", nativeQuery = true)
    List<Tweet> getAllReposts(UUID tweetId);
}
