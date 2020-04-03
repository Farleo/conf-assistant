package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.StreamDTO;

import java.util.List;

public interface StreamService {
	List<StreamDTO> findAllByConfId(Long confId);
}