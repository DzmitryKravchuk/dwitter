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
        Like entity = repository.findById(id).orElse(null);
        if (entity == null) {
            throw new ObjectNotFoundException(Like.class.getName() + " object with index " + id + " not found");
        }
        return entity;
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
        List<Like> entityList = repository.findAll();
        if (entityList.isEmpty()) {
            throw new ObjectNotFoundException(Like.class.getName() + " not a single object was found");
        }
        return entityList;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}

