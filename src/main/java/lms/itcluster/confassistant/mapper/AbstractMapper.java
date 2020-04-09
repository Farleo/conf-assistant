package lms.itcluster.confassistant.mapper;

import lms.itcluster.confassistant.dto.DTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

public abstract class AbstractMapper<E, D extends DTO> implements Mapper<E, D> {

    @Autowired
    private ModelMapper modelMapper;

    private JpaRepository<E, Long> jpaRepository;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    public AbstractMapper(Class<D> dtoClass, Class<E> entityClass, JpaRepository<E, Long> jpaRepository) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public E toEntity(D dto) {
        E entity;
        if (dto.getId() != null) {
            entity = jpaRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
            modelMapper.map(dto, entity);
        }
        else {
            entity = modelMapper.map(dto, entityClass);
        }
        return entity;
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, dtoClass);
    }

    protected Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFieldsInEntity(source, destination);
            return context.getDestination();
        };
    }

    protected Converter<D, E> toEntityConverter() {
        return new Converter<D, E>() {
            @Override
            public E convert(MappingContext<D, E> context) {
                D source = context.getSource();
                E destination = context.getDestination();
                AbstractMapper.this.mapSpecificFieldsInDto(source, destination);
                return context.getDestination();
            }
        };
    }

    protected abstract void mapSpecificFieldsInEntity(E source, D destination);

    protected abstract void mapSpecificFieldsInDto(D source, E destination);
}
