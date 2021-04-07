package devinc.dwitter.service;

import devinc.dwitter.entity.Subscription;
import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.UserDto;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    void refreshSubscription(UUID userId, UUID subscriberId);

    List<Subscription> getUserSubscriptions(User subscriber);

    List<Subscription> getSubscribers(User user);

    void subscribeWithToken(UserDto dto, ServletRequest servletRequest);
}
