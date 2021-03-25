package devinc.dwitter.repository;

import devinc.dwitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
 List<User> findByNameContainingIgnoreCase (String name);
}
