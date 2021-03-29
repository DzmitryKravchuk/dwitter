package devinc.dwitter.demo;

import devinc.dwitter.entity.Topic;
import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.User;
import devinc.dwitter.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TweetServiceTest extends AbstractCreationTest {
    @Autowired
    public TweetServiceTest(RoleService roleService, UserService userService, LikeService likeService, TopicService topicService, TweetService tweetService) {
        super(roleService, userService, likeService, topicService, tweetService);
    }

    @Test
    void CRUDTest() {
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

    //TODO like a tweet and see what happens
    //TODO repost a tweet with comment
    //TODO repost a tweet without comment
    //TODO get reposts of a tweet
    //TODO get all tweets of a thread
    //TODO get all tweets of usersSubscribedToList
    private void likeTweetTest(Tweet tweet1, User secondUser) {
    }


}
