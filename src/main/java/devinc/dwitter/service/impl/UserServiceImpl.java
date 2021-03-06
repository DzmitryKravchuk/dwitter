package devinc.dwitter.service.impl;

import devinc.dwitter.entity.User;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.exception.OperationForbiddenException;
import devinc.dwitter.repository.SubscriptionRepository;
import devinc.dwitter.repository.TweetRepository;
import devinc.dwitter.repository.UserRepository;
import devinc.dwitter.service.AuthService;
import devinc.dwitter.service.RoleService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final TweetRepository tweetRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public User getById(UUID id) {
        return repository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException(User.class.getName() + " object with index " + id + " not found"));
    }

    @Override
    public List<User> getByUserName(String name) {
        List<User> entityList = repository.findAllByNameContainingIgnoreCase(name);
        if (entityList.isEmpty()) {
            throw new ObjectNotFoundException(User.class.getName() + " object with name " + name + " not found");
        }
        return entityList;
    }

    @Override
    public void saveUser(User entity) {
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
            entity.setRole(roleService.getByRoleName("ROLE_USER"));
            entity.setActive(true);
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        repository.save(entity);
    }

    @Override
    public void saveModerator(User entity) {
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
            entity.setRole(roleService.getByRoleName("ROLE_MODERATOR"));
            entity.setActive(true);
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        repository.save(entity);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        tweetRepository.deleteTweetsByUserId(id);
        subscriptionRepository.deleteSubscriptionByUserId(id);
        repository.deleteById(id);
    }

    @Override
    public void deleteUserWithToken(UUID id, ServletRequest servletRequest) {
        User user = authService.getUserFromToken(servletRequest);
        if (user.getId().equals(id)) {
            delete(id);
        } else throw new OperationForbiddenException("You can't delete other users");
    }

    @Override
    public void deleteUserByModerator(UUID id, ServletRequest servletRequest) {
        delete(id);
    }

}
