package lms.itcluster.confassistant.mapper;

import lms.itcluster.confassistant.dto.AbstractDTO;

public interface Mapper<E, D extends AbstractDTO> {

    E toEntity(D dto);

    D toDto(E entity);
}
