package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.model.CurrentUser;

import java.util.List;

public interface StreamService {
	List<StreamDTO> findAllByConfId(Long confId);

    List<StreamDTO> getAllStreamDTOFromCurrentConference(CurrentUser currentUser, Long confId);

    StreamDTO getStreamDTOById(Long id);

    StreamDTO getStreamDTOByName(String name);

    List<StreamDTO> getAllStreamDTOForCurrentModer(CurrentUser currentUser);

	void deleteStream(Long streamId);

	void updateStream(StreamDTO streamDTO);

	void addNewStream(StreamDTO streamDTO);

	List<StreamDTO> getAllStreamDtoByConference(Conference conference);
}
