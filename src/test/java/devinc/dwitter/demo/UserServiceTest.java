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
    private final User firstUser = new User("SuperUserOne", "super1@mail.com", "super1", role, true, usersSubscribedToListFirst, subscribersListFirst);
    private final Set<User> subscribersListSecond = new HashSet<>();
    private final Set<User> usersSubscribedToListSecond = new HashSet<>();
    private final User secondUser = new User("SuperUserTwo", "super2@mail.com", "super2", role, true, usersSubscribedToListSecond, subscribersListSecond);

    @Test
    void CRUDTest() {
        System.out.println("Создаем новую роль");
        createRoleTest(role);

        System.out.println("Создаем первого пользователя");
        createUserTest(firstUser);
        System.out.println("Создаем второго пользователя");
        createUserTest(secondUser);

        System.out.println("Поиск юзера по никнейму");
        findUserByNameTest("SuperUserTwo");

        System.out.println("Удаляем объекты");
        deleteUserTest(firstUser);
        deleteUserTest(secondUser);
        deleteRoleTest(role);
    }

    private void findUserByNameTest(String name) {
        User user = userService.getByUserName(name);
        System.out.println(user);
    }

    private void createRoleTest(Role entity) {
        roleService.save(entity);
        Role entityFromBase = roleService.getById(entity.getId());
        System.out.println(entityFromBase);
    }

    private void createUserTest(User entity) {
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
