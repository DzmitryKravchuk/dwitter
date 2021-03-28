package devinc.dwitter.demo;

import devinc.dwitter.entity.Role;
import devinc.dwitter.entity.Topic;
import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.User;
import devinc.dwitter.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@SpringBootTest
public class TweetServiceTest {
    private final RoleService roleService;
    private final UserService userService;
    private final LikeService likeService;
    private final TopicService topicService;
    private final TweetService tweetService;

    @Autowired
    public TweetServiceTest(RoleService roleService, UserService userService, LikeService likeService, TopicService topicService, TweetService tweetService) {
        this.roleService = roleService;
        this.userService = userService;
        this.likeService = likeService;
        this.topicService = topicService;
        this.tweetService = tweetService;
    }

    private final Role role = new Role("SuperRole");
    private final Set<User> subscribersListFirst = new HashSet<>();
    private final Set<User> usersSubscribedToListFirst = new HashSet<>();
    private final List<Tweet> tweetListFirst = new ArrayList<>();
    private final User firstUser = new User("SuperUserOne", "super1@mail.com", "super1", tweetListFirst, role, true, usersSubscribedToListFirst, subscribersListFirst);
    private final Set<User> subscribersListSecond = new HashSet<>();
    private final Set<User> usersSubscribedToListSecond = new HashSet<>();
    private final List<Tweet> tweetListSecond = new ArrayList<>();
    private final User secondUser = new User("SuperUserTwo", "super2@mail.com", "super2", tweetListSecond, role, true, usersSubscribedToListSecond, subscribersListSecond);
    private final Topic topic = new Topic("New topic", null);
    private Tweet tweet1= new Tweet();;
    private Tweet tweet2 = new Tweet();
    private Tweet reTweet1 = new Tweet();
    private Tweet reTweet2 = new Tweet();

    @Test
    void CRUDTest() {
        createRoleTest(role);
        createUserTest(firstUser);
        createUserTest(secondUser);

        createTopicTest(topic);
        createTweetTest(firstUser, tweet1,null,"Hello world!!!");
        createTweetTest(firstUser, tweet2,topic, "This is the 1-st tweet for a thread");
        likeTweetTest(tweet1, secondUser);

        //TODO like a tweet and see what happens
        //TODO repost a tweet with comment
        //TODO repost a tweet without comment
        //TODO get reposts of a tweet
        //TODO get all tweets of a thread
        //TODO get all tweets of usersSubscribedToList

        deleteTweetTest(tweet1);
        deleteTweetTest(tweet2);
        deleteUserTest(firstUser);
        deleteUserTest(secondUser);
        deleteRoleTest(role);
    }

    private void likeTweetTest(Tweet tweet1, User secondUser) {
    }

    private void createTweetTest(User user, Tweet tweet, Topic topic, String s) {
        System.out.println("Создаем новый твит");
        tweet = tweetService.createTweet(user, s, topic, null);
        Tweet entityFromBase = tweetService.getById(tweet.getId());
        System.out.println(entityFromBase);
        System.out.println("Topic: " + entityFromBase.getTopic());
    }

    private void createTopicTest(Topic entity) {
        System.out.println("Создаем новый трэд");
        topicService.save(entity);
        Topic entityFromBase = topicService.getById(entity.getId());
        System.out.println(entityFromBase);
    }

    private void createRoleTest(Role entity) {
        roleService.save(entity);
    }

    private void createUserTest(User entity) {
        userService.save(entity);
    }

    private void deleteTweetTest(Tweet entity) {
        tweetService.delete(entity.getId());
    }

    private void deleteRoleTest(Role entity) {
        roleService.delete(entity.getId());
    }

    private void deleteUserTest(User entity) {
        userService.delete(entity.getId());
    }
}
