package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.SignUpDTO;
import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class SignUpMapper extends AbstractMapper<User, SignUpDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .addMappings(mapping -> mapping.skip(User::setActiveCode))
                .addMappings(mapping -> mapping.skip(User::setCreated))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(User source, SignUpDTO destination) {
    }

    @Override
    protected void mapSpecificFieldsInDto(SignUpDTO source, User destination) {
        destination.setRoles(Collections.singleton(rolesRepository.findByRole("ROLE_USER")));
        destination.setPassword(passwordEncoder.encode(Constant.USER_PASSWORD));
        destination.setActiveCode(UUID.randomUUID().toString());
        destination.setActive(false);
        destination.setCreated(LocalDate.now());
    }
}
