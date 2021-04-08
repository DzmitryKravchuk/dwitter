package devinc.dwitter.repository;

import devinc.dwitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByNameContainingIgnoreCase(String name);

    User findByLogin(String login);
}
