package devinc.dwitter.repository;

import devinc.dwitter.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    @Query(value = "select * from like_tweet where like_tweet.tweet_id=?1", nativeQuery = true)
    List<Like> getAllByTweetId(UUID tweetId);
}
