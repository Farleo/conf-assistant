package lms.itcluster.confassistant.service;


import lms.itcluster.confassistant.dto.RoleDTO;
import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.model.CurrentUser;

import java.util.List;


public interface RoleService {

    List<RoleDTO> getAll();

}
