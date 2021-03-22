package devinc.dwitter.service;

import devinc.dwitter.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role getById(UUID id);

    Role getByRoleName(String roleName);

    void save(Role role);

    List<Role> getAll();

    void update(Role role);

    void delete(UUID id);
}
