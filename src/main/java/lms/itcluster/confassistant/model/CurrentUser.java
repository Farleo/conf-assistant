package lms.itcluster.confassistant.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class CurrentUser extends User {
    public CurrentUser(lms.itcluster.confassistant.entity.User user) {
        super(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(Constant.ADMIN)));
    }
}
