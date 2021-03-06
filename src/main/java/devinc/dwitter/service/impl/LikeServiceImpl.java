package devinc.dwitter.service.impl;

import devinc.dwitter.entity.Like;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.repository.LikeRepository;
import devinc.dwitter.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class LikeServiceImpl implements LikeService {
    private final LikeRepository repository;

    @Override
    public Like getById(UUID id) {
        return repository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException(Like.class.getName() + " object with index " + id + " not found"));
    }

    @Override
    public void save(Like entity) {
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public List<Like> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Like> getAllByTweetId(UUID tweetId) {
        return repository.getAllByTweetId(tweetId);
    }
}

