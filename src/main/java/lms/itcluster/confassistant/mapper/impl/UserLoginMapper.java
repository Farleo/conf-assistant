package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserLoginMapper extends AbstractMapper<User, UserDTO> {

    private final ModelMapper modelMapper;
    private final RolesRepository rolesRepository;

    @Autowired
    public UserLoginMapper(ModelMapper modelMapper, RolesRepository rolesRepository, UserRepository userRepository) {
        super(UserDTO.class, User.class, userRepository);
        this.modelMapper = modelMapper;
        this.rolesRepository = rolesRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(mapping -> mapping.skip(UserDTO::setRoles)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(mapping -> mapping.skip(User::setPassword))
                .addMappings(mapping -> mapping.skip(User::setRoles)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFieldsInEntity(User source, UserDTO destination) {
        destination.setRoles(getRoles(source));
    }

    private Set<String> getRoles(User user) {
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(role.getRole()));
        return roles;
    }

    @Override
    protected void mapSpecificFieldsInDto(UserDTO source, User destination) {
        destination.setRoles(getRoles(source));
        destination.setPassword(getPassword(source));
    }

    private Set<Roles> getRoles(UserDTO userLoginDTO) {
        Set<Roles> roles = new HashSet<>();
        userLoginDTO.getRoles().forEach(role -> roles.add(rolesRepository.findByRole(role)));
        return roles;
    }

    private String getPassword(UserDTO source) {
        if (source.getPassword() == null) {
            return "";
        }
        return source.getPassword();
    }
}
