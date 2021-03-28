package devinc.dwitter.service.impl;

import devinc.dwitter.entity.Like;
import devinc.dwitter.entity.Topic;
import devinc.dwitter.entity.Tweet;
import devinc.dwitter.entity.User;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.repository.LikeRepository;
import devinc.dwitter.repository.TweetRepository;
import devinc.dwitter.service.LikeService;
import devinc.dwitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class TweetServiceImpl implements TweetService {
    private final TweetRepository repository;

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
    public Tweet createTweet(User user, String s, Topic topic, Tweet repostedTweet) {
        Tweet entity = new Tweet();
        entity.setUser(user);
        if (repostedTweet != null && s == null) {
            entity.setTweet(repostedTweet);
            entity.setContent("No comment");
        } else if (repostedTweet != null) {
            entity.setTweet(repostedTweet);
            entity.setContent(s);
        } else {
            entity.setContent(s);
        }
        if (topic != null) {
            entity.setTopic(topic);
        }
        save(entity);
        return entity;
    }
}

