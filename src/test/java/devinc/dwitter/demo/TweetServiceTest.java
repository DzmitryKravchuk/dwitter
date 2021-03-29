package devinc.dwitter.demo;

import devinc.dwitter.entity.Topic;
import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.User;
import devinc.dwitter.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TweetServiceTest extends AbstractCreationTest {
    @Autowired
    public TweetServiceTest(RoleService roleService, UserService userService, LikeService likeService, TopicService topicService, TweetService tweetService) {
        super(roleService, userService, likeService, topicService, tweetService);
    }

    @Test
    public void createTest() {
        final User user = createNewUser();
        Tweet tweet = createNewTweet(user, "Hello world!!!", null, null);
        Tweet entityFromBase = tweetService.getById(tweet.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(tweet.getContent(), entityFromBase.getContent());
        assertEquals(user.getName(), entityFromBase.getUser().getName());

        final Topic topic = createNewTopic();
        tweet.setTopic(topic);
        tweetService.save(tweet);
        entityFromBase = tweetService.getById(tweet.getId());
        assertEquals(entityFromBase.getTopic().getTopic(), topic.getTopic());

        tweetService.delete(tweet.getId());
        topicService.delete(topic.getId());
        userService.delete(user.getId());
    }

    @Test
    public void likeTweetTest() {
        final User firstUser = createNewUser();
        final User secondUser = createNewUser();
        Tweet tweet = createNewTweet(firstUser, "Hello world!!!", null, null);
        //tweetService.likeTweet(firstUser.getId(), tweet.getId()); // throws exception
        tweetService.likeTweet(secondUser.getId(), tweet.getId());
        Tweet entityFromBase = tweetService.getById(tweet.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entityFromBase.getUser().getId(), firstUser.getId());
        assertEquals(entityFromBase.getLikesCount(), 1);
        assertEquals(entityFromBase.getLikesList().get(0).getUser().getId(), secondUser.getId());

        tweetService.likeTweet(secondUser.getId(), tweet.getId());
        entityFromBase = tweetService.getById(tweet.getId());
        assertEquals(entityFromBase.getLikesCount(), 0);
        assertEquals(entityFromBase.getLikesList().size(), 0);
    }

    @Test
    public void repostTweetWithComment() {
        final User firstUser = createNewUser();
        final User secondUser = createNewUser();
        Tweet tweet1 = createNewTweet(firstUser, "Hello world!!!", null, null);
        Tweet tweet2 = createNewTweet(secondUser, "Hi, bro!!!", null, tweet1);
        Tweet entityFromBase1 = tweetService.getById(tweet1.getId());
        Tweet entityFromBase2 = tweetService.getById(tweet2.getId());
        assertEquals(entityFromBase1.getId(), entityFromBase2.getRepostedTweet().getId());
        assertEquals(entityFromBase2.getContent(), "Hi, bro!!!");
    }
    @Test
    public void repostTweetNoComment() {
        final User firstUser = createNewUser();
        final User secondUser = createNewUser();
        Tweet tweet1 = createNewTweet(firstUser, "Hello world!!!", null, null);
        Tweet tweet2 = createNewTweet(secondUser, null, null, tweet1);
        Tweet entityFromBase1 = tweetService.getById(tweet1.getId());
        Tweet entityFromBase2 = tweetService.getById(tweet2.getId());
        assertEquals(entityFromBase1.getId(), entityFromBase2.getRepostedTweet().getId());
        assertEquals(entityFromBase2.getContent(), "No comment");
    }

    @Test
    public void getRepostsForTweet() {
        final User firstUser = createNewUser();
        final User secondUser = createNewUser();
        Tweet tweet1 = createNewTweet(firstUser, "Hello world!!!", null, null);
        Tweet tweet2 = createNewTweet(firstUser, "One more time", null, tweet1);
        Tweet tweet3 = createNewTweet(secondUser, null, null, tweet1);
        List<Tweet> getAllReposts = tweetService.getAllReposts(tweet1.getId());
        assertEquals(getAllReposts.size(), 2);
    }

    //TODO get all tweets of a thread
    //TODO get all tweets of usersSubscribedToList

}
