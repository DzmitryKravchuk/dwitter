package devinc.dwitter.repository;

import devinc.dwitter.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    @Query(value = "select * FROM topic t where t.topic=?1", nativeQuery = true)
    Topic findByTopic (String topic);
}
