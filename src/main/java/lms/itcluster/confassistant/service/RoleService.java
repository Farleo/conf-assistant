package lms.itcluster.confassistant.service;


import lms.itcluster.confassistant.dto.RoleDTO;
import lms.itcluster.confassistant.entity.Roles;

import java.util.List;


public interface RoleService {

List<RoleDTO> getAll();
}
