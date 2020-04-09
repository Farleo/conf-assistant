package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.dto.SignUpDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Collections;

@Component
public class SignUpMapper extends AbstractMapper<User, SignUpDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    public SignUpMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(SignUpDTO.class, User.class, userRepository);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, SignUpDTO.class);
        modelMapper.createTypeMap(SignUpDTO.class, User.class)
                .addMappings(mapping -> mapping.skip(User::setRoles))
                .addMappings(mapping -> mapping.skip(User::setPassword))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(User source, SignUpDTO destination) {
    }

    @Override
    protected void mapSpecificFieldsInDto(SignUpDTO source, User destination) {
        destination.setRoles(Collections.singleton(rolesRepository.findByRole(Constant.USER_ROLE)));
        destination.setPassword(Constant.USER_PASSWORD);
    }
}
