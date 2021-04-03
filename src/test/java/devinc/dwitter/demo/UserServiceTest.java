package devinc.dwitter.demo;

import devinc.dwitter.entity.User;
import devinc.dwitter.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest extends AbstractCreationTest {
    @Autowired
    public UserServiceTest(RoleService roleService, UserService userService, LikeService likeService, TopicService topicService, TweetService tweetService) {
        super(roleService, userService, likeService, topicService, tweetService);
    }

    @Test
    public void createTest() {
        final User firstUser = createNewUser();
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
        userService.addToSubscribersList(firstUser.getId(), secondUser.getId());
        User entityFromBase1 = userService.getById(firstUser.getId());
        User entityFromBase2 = userService.getById(secondUser.getId());
        assertEquals(entityFromBase1.getSubscribersList().size(), 1);
        assertEquals(entityFromBase2.getUsersSubscribedToList().size(), 1);
    }
}
