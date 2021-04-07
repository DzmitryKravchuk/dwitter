package devinc.dwitter.repository;

import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.UserDto;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByNameContainingIgnoreCase(String name);

    User findByLogin(String login);




}
