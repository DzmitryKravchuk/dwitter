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

    void addToSubscribersList(UUID userId, UUID subscriberId);

    void removeFromSubscribersList(UUID userId, UUID subscriberId);

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);

    void subscribeWithToken(UserDto dto, ServletRequest servletRequest);
}
