package devinc.dwitter.service;

import devinc.dwitter.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getById(UUID id);

    void save(User entity);

    List<User> getAll();

    void delete(UUID id);

    User getByUserName(String name);

    void deactivateAccount(UUID id);

    void restoreAccount(UUID id);

    void addToSubscribersList(UUID userId, UUID subscribeToUserId);

    void removeFromSubscribersList(UUID userId, UUID subscribeToUserId);
}
