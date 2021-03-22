package devinc.dwitter.service;

import devinc.dwitter.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getById(UUID id);

    void save(User like);

    List<User> getAll();

    void update(User like);

    void delete(UUID id);
}
