package devinc.dwitter.repository;

import devinc.dwitter.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    @Query(value = "select * from subscription where subscriber_id=?1", nativeQuery = true)
    List<Subscription> findUsersBySubscriberId(UUID id);

    @Query(value = "select * from subscription where user_account_id=?1", nativeQuery = true)
    List<Subscription> findSubscribersByUserId(UUID id);
}
