package devinc.dwitter.service;

import devinc.dwitter.entity.Topic;

import java.util.List;
import java.util.UUID;

public interface TopicService {
    Topic getById(UUID id);

    void save(Topic entity);

    List<Topic> getAll();

    void delete(UUID id);
}
