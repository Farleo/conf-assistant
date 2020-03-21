package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

@Autowired
private RolesRepository rolesRepository;


@Override
public List<Roles> getAll() {
	return rolesRepository.findAll();
	}
}
