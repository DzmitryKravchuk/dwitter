package devinc.dwitter.demo;

import devinc.dwitter.entity.Topic;
import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.User;
import devinc.dwitter.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        assertEquals(user.getName(), entityFromBase.getUserAccount().getName());

        final Topic topic = createNewTopic();
        tweet.setTopic(topic);
        tweetService.save(tweet);
        entityFromBase = tweetService.getById(tweet.getId());
        assertEquals(entityFromBase.getTopic().getTopic(), topic.getTopic());

        final User userFromBase = userService.getById(user.getId());
        assertEquals(userFromBase.getTweetList().size(),1);

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
        assertEquals(entityFromBase.getUserAccount().getId(), firstUser.getId());
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
    @Test
    public void getAllTweetsOfTopic() {
        final User firstUser = createNewUser();
        final Topic topic = createNewTopic();
        Tweet tweet1 = createNewTweet(firstUser, "Hello world!!!", topic, null);
        Tweet tweet2 = createNewTweet(firstUser, "One more time", topic, null);
        List<Tweet> getAllTweetsOfTopic = tweetService.getAllTweetsOfTopic(topic.getId());
        assertEquals(getAllTweetsOfTopic.size(), 2);
    }

    @Test
    public void getAllTweetsOfUsersSubscribedToList() throws InterruptedException {
        final User firstUser = createNewUser();
        final User secondUser = createNewUser();
        final User subscriber = createNewUser();
        Tweet tweet1 = createNewTweet(firstUser, "Hello world!!!", null, null);
        Tweet tweet2 = createNewTweet(secondUser, "One more time", null, null);
        TimeUnit.SECONDS.sleep(1);
        Tweet tweet3 = createNewTweet(firstUser, "comment", null, tweet2);
        userService.addToSubscribersList(firstUser.getId(),subscriber.getId());
        userService.addToSubscribersList(secondUser.getId(),subscriber.getId());
        List<Tweet> allTweetsOfUsersSubscribedToList = tweetService.getAllTweetsOfUsersSubscribedToList(subscriber.getId());
        assertEquals(allTweetsOfUsersSubscribedToList.size(), 3);
        assertEquals(allTweetsOfUsersSubscribedToList.get(0).getId(), tweet3.getId());
    }
}