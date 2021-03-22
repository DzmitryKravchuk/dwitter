package devinc.dwitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ThreadRepository extends JpaRepository<devinc.dwitter.entity.Thread, UUID> {
}
