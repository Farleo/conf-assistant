package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    @Qualifier("topicMapper")
    private Mapper<Topic, TopicDTO> mapper;

    @Override
    public Topic findByName(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public Topic findById(Long id) {
        return topicRepository.findById(id).get();
    }

    @Override
    public TopicDTO getTopicDTOBuId(Long id) {
        return mapper.toDto(topicRepository.findById(id).get());
    }


}
