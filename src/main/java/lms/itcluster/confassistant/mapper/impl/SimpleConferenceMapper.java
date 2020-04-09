package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.StreamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleConferenceMapper extends AbstractMapper<Conference, ConferenceDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public SimpleConferenceMapper(ModelMapper modelMapper, ConferenceRepository conferenceRepository) {
        super(ConferenceDTO.class, Conference.class, conferenceRepository);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.getTypeMap(Conference.class, ConferenceDTO.class);
        modelMapper.getTypeMap(ConferenceDTO.class, Conference.class);
    }


    @Override
    protected void mapSpecificFieldsInEntity(Conference source, ConferenceDTO destination) {
    }

    @Override
    protected void mapSpecificFieldsInDto(ConferenceDTO source, Conference destination) {
    }

}
