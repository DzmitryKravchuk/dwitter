package devinc.dwitter.service;

import devinc.dwitter.entity.Like;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    Like getById(UUID id);

    void save(Like entity);

    List<Like> getAll();

    void delete(UUID id);

    List<Like> getAllByTweetId(UUID tweetId);
}
