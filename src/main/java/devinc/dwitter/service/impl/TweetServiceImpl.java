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
        entity.setUser(user);
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

        if (topicId != null) {
            Topic topic = topicService.getById(topicId);
            entity.setTopic(topic);
        }
        save(entity);
        return entity;
    }

    @Override
    public synchronized void likeTweet(UUID userId, UUID tweetId) {
        User user = userService.getById(userId);
        Tweet tweet = getById(tweetId);
        if (tweet.getUser().getId().equals(user.getId())) {
            throw new OperationForbiddenException("You can't put like on your own tweet");
        }
        if (tweet.getLikesList() == null) {
            List<Like> likesList = new ArrayList<>();
            tweet.setLikesList(likesList);
        }
        Like likeToDelete = null;
        List<Like> likesList = tweet.getLikesList();
        for (Like like : likesList) {
            if (like.getUser().getId().equals(userId)) {
                likeToDelete = like;
            }
        }
        if (likeToDelete != null) {
            tweet.setLikesCount(tweet.getLikesCount() - 1);
            likesList.remove(likeToDelete);
            likeService.delete(likeToDelete.getId());
        } else {
            Like like = new Like(user, tweet);
            likeService.save(like);
            likesList.add(like);
            tweet.setLikesCount(tweet.getLikesCount() + 1);
        }

        save(tweet);
    }

    @Override
    public List<Tweet> getAllReposts(UUID tweetId) {
        return repository.getAllReposts(tweetId);
    }
}

