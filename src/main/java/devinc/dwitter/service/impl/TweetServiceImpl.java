package devinc.dwitter.service.impl;

import devinc.dwitter.entity.*;
import devinc.dwitter.entity.dto.TweetDto;
import devinc.dwitter.entity.dto.TweetLikeDto;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.exception.OperationForbiddenException;
import devinc.dwitter.repository.TweetRepository;
import devinc.dwitter.service.*;
import devinc.dwitter.service.util.TweetDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
public class TweetServiceImpl implements TweetService {
    private final TweetRepository repository;
    private final UserService userService;
    private final TopicService topicService;
    private final LikeService likeService;
    private final AuthService authService;
    private final TweetDtoConverter tweetDtoConverter;
    private final SubscriptionService subscriptionService;

    @Override
    public Tweet getById(UUID id) {
        return repository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException(Tweet.class.getName() + " object with index " + id + " not found"));
    }

    @Override
    public TweetDto getTweetDtoById(UUID id) {
        Tweet entity = getById(id);
        return tweetDtoConverter.convertToDto(entity);
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
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        List<Tweet> reposts = getAllReposts(id);
        reposts.forEach(repository::delete);
        if (likeService.getAllByTweetId(id) != null) {
            likeService.getAllByTweetId(id).forEach(l -> likeService.delete(l.getId()));
        }
        repository.deleteById(id);
    }

    @Override
    public Tweet createTweet(TweetDto tweetDto, UUID userId) {
        Tweet entity = new Tweet();
        User user = userService.getById(userId);
        entity.setUserAccount(user);
        checkIsRepost(tweetDto.getContent(), tweetDto.getRepostedTweetId(), entity);
        checkHasTopic(tweetDto.getTopic(), entity);
        save(entity);

        return entity;
    }

    private void checkHasTopic(String topic, Tweet entity) {
        if (topic != null) {
            Topic tFromBase = topicService.findByTopicOrCreate(topic);
            entity.setTopic(tFromBase);
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
    public void likeTweet(TweetLikeDto dto, UUID userId) {
        User user = userService.getById(userId);
        Tweet tweet = getById(dto.getTweetId());
        if (tweet.getUserAccount().getId().equals(user.getId())) {
            throw new OperationForbiddenException("You can't put like on your own tweet");
        }
        checkIfLikeListIsNull(tweet);
        List<Like> likesList = likeService.getAllByTweetId(tweet.getId());
        Like likeToDelete = getLikeToDeleteIfExists(userId, likesList);

        if (likeToDelete != null) {
            reduceLikesCount(tweet, likesList, likeToDelete);
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

    private void reduceLikesCount(Tweet tweet, List<Like> likesList, Like likeToDelete) {
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
    public List<TweetDto> getAllRepostsDto(UUID id) {
        List<Tweet> tweetList = getAllReposts(id);
        return tweetList.stream().map(tweetDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<Tweet> getAllTweetsOfTopic(UUID topicId) {
        return repository.getAllTweetsOfTopic(topicId);
    }

    @Override
    public List<Tweet> getTweetFeed(UUID subscriberId) {
        User subscriber = userService.getById(subscriberId);
        List<Subscription> subscriptionList = subscriptionService.getUserSubscriptions(subscriber);

        List<Tweet> tweetFeed = new ArrayList<>();
        for (Subscription sub : subscriptionList) {
            tweetFeed.addAll(getTweetListByUserId(sub.getUserAccount().getId()));
        }
        tweetFeed.sort(Comparator.comparing(Tweet::getUpdated).reversed());
        return tweetFeed;
    }

    @Override
    public List<TweetDto> getTweetFeedDtoWithToken(ServletRequest servletRequest) {
        List<Tweet> tweetList = getTweetFeed(authService.getUserFromToken(servletRequest).getId());
        return tweetList.stream().map(tweetDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<Tweet> getTweetListByUserId(UUID id) {
        return repository.findTweetsByUserId(id);
    }

    @Override
    public List<TweetDto> getTweetDtoListByUserId(UUID id) {
        List<Tweet> tweetList = getTweetListByUserId(id);
        return tweetList.stream().map(tweetDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void createTweetWithToken(TweetDto dto, ServletRequest servletRequest) {
        createTweet(dto, authService.getUserFromToken(servletRequest).getId());
    }

    @Override
    public void likeTweetWithToken(TweetLikeDto dto, ServletRequest servletRequest) {
        likeTweet(dto, authService.getUserFromToken(servletRequest).getId());
    }

    @Override
    public void deleteTweetWithToken(UUID id, ServletRequest servletRequest) {
        Tweet tweet = getById(id);
        User user = authService.getUserFromToken(servletRequest);
        if (tweet.getUserAccount().getId() != user.getId()) {
            throw new OperationForbiddenException("you can delete only your own tweet");
        }
        delete(id);
    }

    @Override
    public void deleteTweetByModerator(UUID id, ServletRequest servletRequest) {
        delete(id);
    }
}

