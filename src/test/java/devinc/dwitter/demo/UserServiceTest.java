package devinc.dwitter.demo;

import devinc.dwitter.entity.Role;
import devinc.dwitter.service.RoleService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class UserServiceTest {
    private final RoleService roleService;

    @Autowired
    public UserServiceTest(RoleService roleService) {
        this.roleService = roleService;
    }

    @Test
    void CRUDTest() {
        Role role = new Role("SuperAdmin");
        System.out.println("Создаем новый объект");
        createEntityTest(role);
        role.setName("NEW ROLE");
        System.out.println("Обновили объект");
        updateEntityTest(role);
        System.out.println("Удаляем объект");
        deleteEntityTest(role);
    }

    @Test
    void GetByNameTest() {
        Role role = new Role("SuperAdmin");
        System.out.println("Создаем новый объект");
        createEntityTest(role);
        Role roleFromBase=roleService.getByRoleName("SuperAdmin");
        System.out.println("Достаем объект из базы по имени");
        System.out.println(roleFromBase);
        deleteEntityTest(role);
    }

    private void createEntityTest(Role role) {
        roleService.save(role);
        Role roleFromBase = roleService.getById(role.getId());
        System.out.println(roleFromBase);
    }

    private void updateEntityTest(Role role) {
        roleService.save(role);
        Role roleFromBase = roleService.getById(role.getId());
        System.out.println(roleFromBase);
    }

    private void deleteEntityTest(Role role) {
        roleService.delete(role.getId());
    }
}
