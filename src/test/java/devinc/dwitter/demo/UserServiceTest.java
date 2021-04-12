package devinc.dwitter.demo;

import devinc.dwitter.entity.Subscription;
import devinc.dwitter.entity.User;
import devinc.dwitter.exception.OperationForbiddenException;
import devinc.dwitter.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends AbstractCreationTest {
    @Autowired
    public UserServiceTest(RoleService roleService, UserService userService, LikeService likeService, TopicService topicService, TweetService tweetService, SubscriptionService subscriptionService) {
        super(roleService, userService, likeService, topicService, tweetService, subscriptionService);
    }

    @Test
    public void createUserTest() {
        final User firstUser = createNewUser();
        User entityFromBase = userService.getById(firstUser.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(firstUser.getName(), entityFromBase.getName());
        userService.delete(firstUser.getId());
    }

    @Test
    public void createModeratorTest() {
        final User firstUser = createNewModerator();
        User entityFromBase = userService.getById(firstUser.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(firstUser.getName(), entityFromBase.getName());
        userService.delete(firstUser.getId());
    }

    @Test
    public void findUserByNameTest() {
        final User firstUser = createNewUser();
        final List<User> entityList = userService.getByUserName("Super");
        assertEquals(entityList.size(), 1);
    }

    @Test
    public void subscribeToUserTest() {
        final User firstUser = createNewUser();
        final User secondUser = createNewUser();
        assertThrows(OperationForbiddenException.class, () -> {
            subscriptionService.refreshSubscription(firstUser.getId(), firstUser.getId()); // throws exception
        });
        subscriptionService.refreshSubscription(firstUser.getId(), secondUser.getId());
        List<Subscription> subscriptionList = subscriptionService.getUserSubscriptions(secondUser);
        assertEquals(subscriptionList.size(), 1);
        List<Subscription> subscribersList = subscriptionService.getSubscribers(firstUser);
        assertEquals(subscribersList.size(), 1);
        userService.delete(secondUser.getId());
    }
}
