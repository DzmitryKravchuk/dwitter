package devinc.dwitter.service;

import devinc.dwitter.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getById(UUID id);

    void save(User entity);

    List<User> getAll();

    void delete(UUID id);

    List<User> getByUserName(String name);

    void deactivateAccount(UUID id);

    void restoreAccount(UUID id);

    void addToSubscribeList(UUID userId, UUID subscribeToUserId);

    void removeFromSubscribeList(UUID userId, UUID subscribeToUserId);
}
