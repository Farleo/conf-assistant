package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.EditProfileDTO;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditProfileMapper extends AbstractMapper<User, EditProfileDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public EditProfileMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(EditProfileDTO.class, User.class, userRepository);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, EditProfileDTO.class);
        modelMapper.createTypeMap(EditProfileDTO.class, User.class)
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(User source, EditProfileDTO destination) {
    }

    @Override
    protected void mapSpecificFieldsInDto(EditProfileDTO source, User destination) {
    }
}
