package devinc.dwitter.service.impl;

import devinc.dwitter.entity.Role;
import devinc.dwitter.exception.NotFoundObjectException;
import devinc.dwitter.repository.RoleRepository;
import devinc.dwitter.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;


    @Override
    public Role getById(UUID id) {
        Role role = repository.findById(id).orElse(null);
        if (role == null) {
            throw new NotFoundObjectException(repository.getClass().getName() + " object with index " + id + " not found");
        }
        return role;
    }

    @Override
    public Role getByRoleName(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        Example<Role> example = Example.of(role);
        Role roleOptional = repository.findOne(example).get();

        return roleOptional;
    }

    @Override
    public void save(Role role) {
        repository.save(role);
    }

    @Override
    public List<Role> getAll() {
        return null;
    }

    @Override
    public void update(Role like) {

    }

    @Override
    public void delete(UUID id) {

    }
}
