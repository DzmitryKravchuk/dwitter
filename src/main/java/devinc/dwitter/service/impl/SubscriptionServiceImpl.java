package devinc.dwitter.service.impl;

import devinc.dwitter.entity.Subscription;
import devinc.dwitter.entity.User;
import devinc.dwitter.entity.dto.UserDto;
import devinc.dwitter.exception.OperationForbiddenException;
import devinc.dwitter.repository.SubscriptionRepository;
import devinc.dwitter.service.AuthService;
import devinc.dwitter.service.SubscriptionService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
@Setter
public class SubscriptionServiceImpl implements SubscriptionService {
    private final UserService userService;
    private final AuthService authService;
    private final SubscriptionRepository repository;

    @Override
    public void refreshSubscription(UUID userId, UUID subscriberId) {
        if (userId == subscriberId) {
            throw new OperationForbiddenException("You can't subscribe to your own account");
        }
        User subscriber = userService.getById(subscriberId);
        List<Subscription> subscriptionsList = getUserSubscriptions(subscriber);

        Subscription presentSubscription = presentSubscription(userId, subscriptionsList);
        if (presentSubscription != null) {
            cancelSubscription(presentSubscription);
        } else {
            saveSubscription(userId, subscriberId);
        }
    }

    private void saveSubscription(UUID userId, UUID subscriberId) {
        Subscription entity = new Subscription();
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
            entity.setUserAccount(userService.getById(userId));
            entity.setSubscriber(userService.getById(subscriberId));
        }
        repository.save(entity);
    }

    private void cancelSubscription(Subscription presentSubscription) {
        repository.deleteById(presentSubscription.getId());
    }

    private Subscription presentSubscription(UUID userId, List<Subscription> subscriptionsList) {
        Optional<Subscription> result = subscriptionsList.stream().filter(sub -> sub.getUserAccount().getId().equals(userId)).findAny();
        return result.orElse(null);
    }

    @Override
    public List<Subscription> getUserSubscriptions(User subscriber) {
        List<Subscription> subscriptions;
        if (repository.findUsersSubscribedTo(subscriber.getId()) == null) {
            subscriptions = new ArrayList<>();
        } else {
            subscriptions = repository.findUsersSubscribedTo(subscriber.getId());
        }
        return subscriptions;
    }

    @Override
    public void subscribeWithToken(UserDto dto, ServletRequest servletRequest) {

    }
}
