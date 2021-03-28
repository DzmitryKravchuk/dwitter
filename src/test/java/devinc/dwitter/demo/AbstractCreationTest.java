package devinc.dwitter.demo;

import devinc.dwitter.entity.*;
import devinc.dwitter.service.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
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

    protected Role createNewRole() {
        final Role entity = new Role("SUPER_ROLE");
        roleService.save(entity);
        return entity;
    }

    protected User createNewUser() {
        final User entity = new User();
        entity.setName("SuperUser" + getRandomInt());
        entity.setActive(true);
        entity.setLogin(entity.getName() + "@mail.com");
        entity.setPassword(entity.getName());
        entity.setRole(createNewRole());
        userService.save(entity);
        return entity;
    }

    protected Topic createNewTopic() {
        final Topic entity = new Topic();
        entity.setTopic("newTopic" + getRandomInt());
        topicService.save(entity);
        return entity;
    }

    protected Tweet createNewTweet(User user, String s, Topic topic, Tweet repostedTweet) {
        final Tweet entity = new Tweet();
        entity.setContent(s);
        entity.setUser(user);
        entity.setTweet(repostedTweet);
        tweetService.save(entity);
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
