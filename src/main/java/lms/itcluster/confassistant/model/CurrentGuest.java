package lms.itcluster.confassistant.model;

import lms.itcluster.confassistant.entity.Guest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class CurrentGuest extends User {
    public CurrentGuest(Guest guest) {
        super(guest.getEmail(), "", Collections.singleton(new SimpleGrantedAuthority(Constant.GUEST)));
    }
}
