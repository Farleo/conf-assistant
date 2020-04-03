package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StreamServiceImpl implements StreamService {
	
	@Autowired
	private StreamRepository streamRepository;

	@Autowired
	@Qualifier("streamMapper")
	private Mapper<Stream, StreamDTO> mapper;

		@Override
		public List<StreamDTO> findAllByConfId(Long confId) {
			List<Stream> streamList = streamRepository.findAllByConferenceId(confId);
			return streamList.stream().map(s->mapper.toDto(s)).collect(Collectors.toList());
		}
}
