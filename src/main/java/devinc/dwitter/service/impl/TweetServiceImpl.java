package devinc.dwitter.service.impl;

import devinc.dwitter.entity.Like;
import devinc.dwitter.entity.Topic;
import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.User;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.exception.OperationForbiddenException;
import devinc.dwitter.repository.TweetRepository;
import devinc.dwitter.service.LikeService;
import devinc.dwitter.service.TopicService;
import devinc.dwitter.service.TweetService;
import devinc.dwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Setter
public class TweetServiceImpl implements TweetService {
    private final TweetRepository repository;
    private final UserService userService;
    private final TopicService topicService;
    private final LikeService likeService;

    @Override
    public Tweet getById(UUID id) {
        Tweet entity = repository.findById(id).orElse(null);
        if (entity == null) {
            throw new ObjectNotFoundException(Tweet.class.getName() + " object with index " + id + " not found");
        }
        return entity;
    }

    @Override
    public void save(Tweet entity) {
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public List<Tweet> getAll() {
        List<Tweet> entityList = repository.findAll();
        if (entityList.isEmpty()) {
            throw new ObjectNotFoundException(Like.class.getName() + " not a single object was found");
        }
        return entityList;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Tweet createTweet(UUID userId, String s, UUID topicId, UUID repostedTweetId) {
        Tweet entity = new Tweet();
        User user = userService.getById(userId);
        entity.setUserAccount(user);
        checkIsRepost(s, repostedTweetId, entity);
        checkHasTopic(topicId, entity);
        save(entity);
        addToTweetList(entity, user);
        return entity;
    }

    private void addToTweetList(Tweet entity, User user) {
        if (user.getTweetList() == null) {
            List<Tweet> tweetList = new ArrayList<>();
            user.setTweetList(tweetList);
        }
        user.getTweetList().add(entity);
        userService.save(user);
    }

    private void checkHasTopic(UUID topicId, Tweet entity) {
        if (topicId != null) {
            Topic topic = topicService.getById(topicId);
            entity.setTopic(topic);
        }
    }

    private void checkIsRepost(String s, UUID repostedTweetId, Tweet entity) {
        if (repostedTweetId != null && s == null) {
            Tweet repostedTweet = getById(repostedTweetId);
            entity.setRepostedTweet(repostedTweet);
            entity.setContent("No comment");
        } else if (repostedTweetId != null) {
            Tweet repostedTweet = getById(repostedTweetId);
            entity.setRepostedTweet(repostedTweet);
            entity.setContent(s);
        } else {
            entity.setContent(s);
        }
    }

    @Override
    public void likeTweet(UUID userId, UUID tweetId) {
        User user = userService.getById(userId);
        Tweet tweet = getById(tweetId);
        if (tweet.getUserAccount().getId().equals(user.getId())) {
            throw new OperationForbiddenException("You can't put like on your own tweet");
        }
        checkIfLikeListIsNull(tweet);
        List<Like> likesList = tweet.getLikesList();
        Like likeToDelete = getLikeToDeleteIfExists(userId, likesList);

        if (likeToDelete != null) {
            reduseLikesCount(tweet, likesList, likeToDelete);
        } else {
            increaseLikesCount(user, tweet, likesList);
        }
        save(tweet);
    }

    private void increaseLikesCount(User user, Tweet tweet, List<Like> likesList) {
        Like like = new Like(user, tweet);
        likeService.save(like);
        likesList.add(like);
        tweet.setLikesCount(tweet.getLikesCount() + 1);
    }

    private void reduseLikesCount(Tweet tweet, List<Like> likesList, Like likeToDelete) {
        tweet.setLikesCount(tweet.getLikesCount() - 1);
        likesList.remove(likeToDelete);
        likeService.delete(likeToDelete.getId());
    }

    private Like getLikeToDeleteIfExists(UUID userId, List<Like> likesList) {
        Like likeToDelete = null;
        for (Like like : likesList) {
            if (like.getUser().getId().equals(userId)) {
                likeToDelete = like;
            }
        }
        return likeToDelete;
    }

    private void checkIfLikeListIsNull(Tweet tweet) {
        if (tweet.getLikesList() == null) {
            List<Like> likesList = new ArrayList<>();
            tweet.setLikesList(likesList);
        }
    }

    @Override
    public List<Tweet> getAllReposts(UUID tweetId) {
        return repository.getAllReposts(tweetId);
    }

    @Override
    public List<Tweet> getAllTweetsOfTopic(UUID topicId) {
        return repository.getAllTweetsOfTopic(topicId);
    }

    @Override
    public List<Tweet> getAllTweetsOfUsersSubscribedToList(UUID subscriberId) {
        User subscriber = userService.getById(subscriberId);
        if (subscriber.getUsersSubscribedToList() == null) {
            throw new ObjectNotFoundException(User.class.getName() + " " + subscriber.getName() + "is not subscribed to anyone");
        }
        Set<User> usersSubscribedToList = subscriber.getUsersSubscribedToList();
        List<Tweet> allTweetsOfUsersSubscribedToList = new ArrayList<>();
        for (User user : usersSubscribedToList) {
            allTweetsOfUsersSubscribedToList.addAll(user.getTweetList());
        }
        allTweetsOfUsersSubscribedToList.sort(Comparator.comparing(Tweet::getUpdated).reversed());
        return allTweetsOfUsersSubscribedToList;
    }
}

