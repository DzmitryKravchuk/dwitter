package devinc.dwitter.service.impl;

import devinc.dwitter.entity.Subscription;
import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.User;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.exception.OperationForbiddenException;
import devinc.dwitter.repository.UserRepository;
import devinc.dwitter.service.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
@Setter
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
//    private final TweetService tweetService;
//    private final SubscriptionService subscriptionService;

    @Override
    public User getById(UUID id) {
        User entity = repository.findById(id).orElse(null);
        if (entity == null) {
            throw new ObjectNotFoundException(User.class.getName() + " object with index " + id + " not found");
        }
        return entity;
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
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
 //       List <Tweet> tweetList=tweetService.getTweetListByUserId(id);
 //       tweetList.forEach(t->tweetService.delete(t.getId()));

 //       List <Subscription> subscriptionList =subscriptionService.getUserSubscriptions()

        repository.deleteById(id);
    }

    @Override
    public void deleteUserWithToken(UUID id, ServletRequest servletRequest) {
        User user = authService.getUserFromToken(servletRequest);
        if (user.getId().equals(id)) {
            delete(id);
        } else throw new OperationForbiddenException("You can't delete other users");
    }

}
