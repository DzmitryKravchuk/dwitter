package devinc.dwitter.service;

import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.UserDto;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User getById(UUID id);

    void saveUser(User entity);

    List<User> getAll();

    void delete(UUID id);

    List<User> getByUserName(String name);


}
