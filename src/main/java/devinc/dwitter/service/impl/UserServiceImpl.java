package devinc.dwitter.service.impl;

import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.UserDto;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.exception.PasswordIncorrectException;
import devinc.dwitter.repository.UserRepository;
import devinc.dwitter.service.AuthService;
import devinc.dwitter.service.RoleService;
import devinc.dwitter.service.UserService;
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
    public void addToSubscribersList(UUID userId, UUID subscriberId) {
        User user = getById(userId);
        User subscriber = getById(subscriberId);
        if (user.getSubscribersList() == null) {
            Set<User> subscribersList = new HashSet<>();
            user.setSubscribersList(subscribersList);
        }
        Set<User> subscribersList = user.getSubscribersList();
        if (subscriber.getUsersSubscribedToList() == null) {
            Set<User> usersSubscribedToList = new HashSet<>();
            subscriber.setUsersSubscribedToList(usersSubscribedToList);
        }
        Set<User> usersSubscribedToList = subscriber.getUsersSubscribedToList();
        subscribersList.add(subscriber);
        usersSubscribedToList.add(user);

        saveUser(user);
        saveUser(subscriber);
    }

    @Override
    public void removeFromSubscribersList(UUID userId, UUID subscriberId) {
        User user = getById(userId);
        User subscriber = getById(subscriberId);
        Set<User> subscribersList = user.getSubscribersList();
        Set<User> usersSubscribedToList = subscriber.getUsersSubscribedToList();
        subscribersList.remove(subscriber);
        usersSubscribedToList.remove(user);

        saveUser(user);
        saveUser(subscriber);
    }

    @Override
    public User findByLogin(String login) {
        User entity= repository.findByLogin(login);
        if (entity == null) {
            throw new ObjectNotFoundException(User.class.getName() + " object with login " + login + " not found");
        }
        return entity;
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new PasswordIncorrectException("wrong password");
    }

    @Override
    public void subscribeWithToken(UserDto dto, ServletRequest servletRequest) {
        User subscriber = authService.getUserFromToken(servletRequest);
        addToSubscribersList(dto.getId(),subscriber.getId());

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
        repository.deleteById(id);
    }
}
