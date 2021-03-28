package devinc.dwitter.service.impl;

import devinc.dwitter.entity.User;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.exception.OperationForbiddenException;
import devinc.dwitter.repository.UserRepository;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
public class UserServiceImpl implements UserService {
    private static final int DEACTIVATION_PERIOD = 180;
    private final UserRepository repository;


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

    private List<User> filterActiveUsers(List<User> entityList) {
        return entityList.stream().filter(User::isActive).collect(Collectors.toList());
    }

    @Override
    public void deactivateAccount(UUID id) {
        User entity = getById(id);
        entity.setActive(false);
        save(entity);
    }

    @Override
    public void restoreAccount(UUID id) {
        User entity = getById(id);
        checkIfDeactivationTimeIsExpired(entity);
        entity.setActive(true);
        save(entity);
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

        save(user);
        save(subscriber);
    }

    @Override
    public void removeFromSubscribersList(UUID userId, UUID subscriberId) {
        User user = getById(userId);
        User subscriber = getById(subscriberId);
        Set<User> subscribersList = user.getSubscribersList();
        Set<User> usersSubscribedToList = subscriber.getUsersSubscribedToList();
        subscribersList.remove(subscriber);
        usersSubscribedToList.remove(user);

        save(user);
        save(subscriber);
    }

    private void checkIfDeactivationTimeIsExpired(User entity) {
        if (entity.isActive()) {
            return;
        }
        Date currentDate = new Date();
        long diff = currentDate.getTime() - entity.getUpdated().getTime();
        int daysPassed = (int) (diff / 1000 / 60 / 60 / 24);
        if (daysPassed > DEACTIVATION_PERIOD) {
            throw new OperationForbiddenException("Can't restore account for expiring term");
        }
    }

    @Override
    public void save(User entity) {
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public List<User> getAll() {
        List<User> entityList = repository.findAll();
        if (entityList.isEmpty()) {
            throw new ObjectNotFoundException(User.class.getName() + " not a single object was found");
        }
        return entityList;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
