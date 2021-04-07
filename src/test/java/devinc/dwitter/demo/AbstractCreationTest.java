package devinc.dwitter.demo;

import devinc.dwitter.entity.*;
import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@SpringBootTest
public class AbstractCreationTest {
    protected static final Random RANDOM = new Random();
    protected final RoleService roleService;
    protected final UserService userService;
    protected final LikeService likeService;
    protected final TopicService topicService;
    protected final TweetService tweetService;
    protected final SubscriptionService subscriptionService;

    @Autowired
    public AbstractCreationTest(RoleService roleService, UserService userService, LikeService likeService, TopicService topicService, TweetService tweetService, SubscriptionService subscriptionService) {
        this.roleService = roleService;
        this.userService = userService;
        this.likeService = likeService;
        this.topicService = topicService;
        this.tweetService = tweetService;
        this.subscriptionService = subscriptionService;
    }

    protected Integer getRandomInt() {
        return RANDOM.nextInt(99999);
    }

    protected User createNewUser() {
        final User entity = new User();
        entity.setName("SuperUser" + getRandomInt());
        entity.setActive(true);
        entity.setLogin(entity.getName() + "@mail.com");
        entity.setPassword(entity.getName());
        userService.saveUser(entity);
        return entity;
    }

    protected Topic createNewTopic() {
        final Topic entity = new Topic();
        entity.setTopic("newTopic" + getRandomInt());
        topicService.save(entity);
        return entity;
    }

    protected Tweet createNewTweet(User user, String s, String topic, Tweet repostedTweet) {
        UUID repostedTweetId = null;
        if (repostedTweet != null) {
            repostedTweetId = repostedTweet.getId();
        }
        final TweetDto tweetDto = new TweetDto(s,repostedTweetId,topic);
        final Tweet entity = tweetService.createTweet(tweetDto,user.getId());
        return entity;
    }

    protected Like createLike(User user, Tweet tweet) {
        final Like entity = new Like();
        entity.setTweet(tweet);
        entity.setUser(user);
        likeService.save(entity);
        return entity;
    }
}
