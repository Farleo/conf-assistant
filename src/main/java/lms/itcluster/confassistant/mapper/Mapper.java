package lms.itcluster.confassistant.mapper;

import lms.itcluster.confassistant.dto.DTO;

public interface Mapper<E, D extends DTO> {

    E toEntity(D dto);

    D toDto(E entity);
}
