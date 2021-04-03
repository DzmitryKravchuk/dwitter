package devinc.dwitter.demo;

import devinc.dwitter.entity.*;
import devinc.dwitter.entity.dto.NewTweetDto;
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

    @Autowired
    public AbstractCreationTest(RoleService roleService, UserService userService, LikeService likeService, TopicService topicService, TweetService tweetService) {
        this.roleService = roleService;
        this.userService = userService;
        this.likeService = likeService;
        this.topicService = topicService;
        this.tweetService = tweetService;
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

    protected Tweet createNewTweet(User user, String s, Topic topic, Tweet repostedTweet) {
        UUID topicId = null;
        UUID repostedTweetId = null;
        if (topic != null) {
            topicId = topic.getId();
        }
        if (repostedTweet != null) {
            repostedTweetId = repostedTweet.getId();
        }
        final NewTweetDto tweetDto = new NewTweetDto(s,repostedTweetId,topicId);
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
