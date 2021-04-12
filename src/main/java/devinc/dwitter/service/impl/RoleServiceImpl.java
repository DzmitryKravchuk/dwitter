package devinc.dwitter.service.impl;

import devinc.dwitter.entity.Role;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.repository.RoleRepository;
import devinc.dwitter.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;


    @Override
    public Role getById(UUID id) {
        return repository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException(Role.class.getName() + " object with index " + id + " not found"));
    }

    @Override
    public Role getByRoleName(String roleName) {
        Role role = repository.findByName(roleName);
        if (role == null) {
            throw new ObjectNotFoundException(Role.class.getName() + " object with name " + roleName + " not found");
        }
        return role;
    }

    @Override
    public void save(Role entity) {
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
