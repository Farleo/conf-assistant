package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.EditContactsDTO;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class EditContactMapper extends AbstractMapper<User, EditContactsDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public EditContactMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(EditContactsDTO.class, User.class, userRepository);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, EditContactsDTO.class);
        modelMapper.createTypeMap(EditContactsDTO.class, User.class)
                .addMappings(mapping -> mapping.skip(User::setActiveCode))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(User source, EditContactsDTO destination) {
    }

    @Override
    protected void mapSpecificFieldsInDto(EditContactsDTO source, User destination) {
        destination.setActiveCode(UUID.randomUUID().toString());
    }
}
