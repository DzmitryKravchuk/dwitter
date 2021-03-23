package devinc.dwitter.service;

import devinc.dwitter.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getById(UUID id);

    void save(User entity);

    List<User> getAll();

    void delete(UUID id);
}
