package devinc.dwitter.service.impl;

import devinc.dwitter.entity.Topic;
import devinc.dwitter.exception.ObjectNotFoundException;
import devinc.dwitter.repository.TopicRepository;
import devinc.dwitter.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class TopicServiceImpl implements TopicService {
    private final TopicRepository repository;

    @Override
    public Topic getById(UUID id) {
        Topic entity = repository.findById(id).orElse(null);
        if (entity == null) {
            throw new ObjectNotFoundException(Topic.class.getName() + " object with index " + id + " not found");
        }
        return entity;
    }

    @Override
    public void save(Topic entity) {
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public List<Topic> getAll() {
        List<Topic> entityList = repository.findAll();
        if (entityList.isEmpty()) {
            throw new ObjectNotFoundException(Topic.class.getName() + " not a single object was found");
        }
        return entityList;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}

