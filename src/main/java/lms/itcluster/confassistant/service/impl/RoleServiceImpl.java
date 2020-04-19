package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.RoleDTO;
import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.service.RoleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RolesRepository rolesRepository;


    @Override
    public List<RoleDTO> getAll() {
        List<Roles> roles = rolesRepository.findAll();
        Type listType = new TypeToken<List<RoleDTO>>() {
        }.getType();
        ModelMapper modelMapper = new ModelMapper();
        List<RoleDTO> roleDTOS = modelMapper.map(roles, listType);
        return roleDTOS;
    }

}
