package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.StreamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StreamServiceImpl implements StreamService {

    @Autowired
    @Qualifier("streamMapper")
    private Mapper<Stream, StreamDTO> mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreamRepository streamRepository;

    @Override
    public List<StreamDTO> findAllByConfId(Long confId) {
        List<Stream> streamList = streamRepository.findAllByConferenceId(confId);
        return streamList.stream().map(s->mapper.toDto(s)).collect(Collectors.toList());
    }

    @Override
    public List<StreamDTO> getAllStreamDTOFromCurrentConference(CurrentUser currentUser, Long confId) {
        List<StreamDTO> streamDTOS = new ArrayList<>();
        for (Stream stream : streamRepository.findAllByModerator_UserIdAndConference_ConferenceId(currentUser.getId(), confId)) {
            streamDTOS.add(mapper.toDto(stream));
        }
        return streamDTOS;
    }

    @Override
    public StreamDTO getStreamDTOById(Long id) {
        return mapper.toDto(streamRepository.findById(id).get());
    }

    @Override
    public StreamDTO getStreamDTOByName(String name) {
        return mapper.toDto(streamRepository.findByName(name));
    }

    @Override
    public List<StreamDTO> getAllStreamDTOForCurrentModer(CurrentUser currentUser) {
        List<StreamDTO> list = new ArrayList<>();
        List<Stream> streams = streamRepository.findAllByModerator(userRepository.findById(currentUser.getId()).get());
        for (Stream stream : streams) {
            list.add(mapper.toDto(stream));
        }
        return list;
    }

    @Override
    public void deleteStream(Long streamId) {
        Optional<Stream> optionalStream = streamRepository.findById(streamId);
        streamRepository.delete(optionalStream.get());
    }

    @Override
    public void updateStream(StreamDTO streamDTO) {
        Optional<Stream> stream = streamRepository.findById(streamDTO.getStreamId());
        if(stream.isPresent()){
            Stream realStream = mapper.toEntity(streamDTO);
            streamRepository.save(realStream);
        }
    }

    @Override
    public void addNewStream(StreamDTO streamDTO) {
        Stream stream = mapper.toEntity(streamDTO);
        streamRepository.save(stream);
    }

    @Override
    public List<StreamDTO> getAllStreamDtoByConference(Conference conference) {
        List<Stream> list = streamRepository.findAllByConference(conference);
        List<StreamDTO> streamDTOS = new ArrayList<>();
        for (Stream stream : list) {
            streamDTOS.add(mapper.toDto(stream));
        }
        return streamDTOS;
    }

}
