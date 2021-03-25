package devinc.dwitter.demo;

import devinc.dwitter.entity.Role;
import devinc.dwitter.entity.User;
import devinc.dwitter.service.RoleService;
import devinc.dwitter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@SpringBootTest
public class UserServiceTest {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public UserServiceTest(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    private final Role role = new Role("SuperRole");
    private final Set<User> subscribersListFirst = new HashSet<>();
    private final Set<User> usersSubscribedToListFirst = new HashSet<>();
    private final User firstUser = new User("SuperUserOne", "super1@mail.com", "super1", null, role, true, usersSubscribedToListFirst, subscribersListFirst);
    private final Set<User> subscribersListSecond = new HashSet<>();
    private final Set<User> usersSubscribedToListSecond = new HashSet<>();
    private final User secondUser = new User("SuperUserTwo", "super2@mail.com", "super2", null, role, true, usersSubscribedToListSecond, subscribersListSecond);

    @Test
    void CRUDTest() {
        createRoleTest(role);
        createUserTest(firstUser);
        createUserTest(secondUser);

        findUserByNameTest("super");

        deactivateUserTest(firstUser);
        restoreUserTest(firstUser);

        subscribeToUserTest(firstUser, secondUser);

        deleteUserTest(firstUser);
        deleteUserTest(secondUser);
        deleteRoleTest(role);
    }

    private void subscribeToUserTest(User firstEntity, User secondEntity) {
        System.out.println("Второй пользователь подписывается на первого");
        userService.addToSubscribersList(firstEntity.getId(), secondEntity.getId());
        User entityFromBase1 = userService.getById(firstEntity.getId());
        User entityFromBase2 = userService.getById(secondEntity.getId());
        System.out.println("entityFromBase1.getSubscribersList(): " + entityFromBase1.getSubscribersList());
        System.out.println("entityFromBase2.getUsersSubscribedToList(): " +entityFromBase2.getUsersSubscribedToList());
    }

    private void restoreUserTest(User entity) {
        System.out.println("Восстановление аккаунта");
        userService.restoreAccount(entity.getId());
        User entityFromBase = userService.getById(entity.getId());
        System.out.println(entityFromBase);
    }

    private void deactivateUserTest(User entity) {
        System.out.println("Деактивация аккаунта");
        userService.deactivateAccount(entity.getId());
        User entityFromBase = userService.getById(entity.getId());
        System.out.println(entityFromBase);
    }

    private void findUserByNameTest(String name) {
        System.out.println("Поиск юзера по никнейму");
        List<User> entityList = userService.getByUserName(name);
        System.out.println(entityList);
    }

    private void createRoleTest(Role entity) {
        System.out.println("Создаем новую роль");
        roleService.save(entity);
        Role entityFromBase = roleService.getById(entity.getId());
        System.out.println(entityFromBase);
    }

    private void createUserTest(User entity) {
        System.out.println("Создаем нового пользователя");
        userService.save(entity);
        User entityFromBase = userService.getById(entity.getId());
        System.out.println(entityFromBase);
    }

    private void deleteRoleTest(Role entity) {
        roleService.delete(entity.getId());
    }

    private void deleteUserTest(User entity) {
        userService.delete(entity.getId());
    }
}
