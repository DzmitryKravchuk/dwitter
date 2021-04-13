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
        return repository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException(Topic.class.getName() + " object with index " + id + " not found"));
    }

    @Override
    public Topic findByTopicOrCreate(String topic) {
        Topic tFromBase = repository.findByTopic(topic);
        if (tFromBase == null) {
            tFromBase = new Topic(topic, null);
            save(tFromBase);
        }
        return tFromBase;
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
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}

