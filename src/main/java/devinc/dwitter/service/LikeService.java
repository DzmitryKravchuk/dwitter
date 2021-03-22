package devinc.dwitter.service;

import devinc.dwitter.entity.Like;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    Like getById(UUID id);

    void save(Like like);

    List<Like> getAll();

    void update(Like like);

    void delete(UUID id);
}
