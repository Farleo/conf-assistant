package lms.itcluster.confassistant.model;

import lms.itcluster.confassistant.entity.Participants;
import lms.itcluster.confassistant.entity.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

public class CurrentUser extends User {
    private Long id;

    public CurrentUser(lms.itcluster.confassistant.entity.User user) {
        super(user.getEmail(), user.getPassword(), user.getActive(), true, true, true, getAllRoles(user));
        this.id = user.getUserId();
    }

    private static Collection<GrantedAuthority> getAllRoles(lms.itcluster.confassistant.entity.User user) {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        for (Roles roles : user.getRoles()) {
            collection.add(new SimpleGrantedAuthority(roles.getRole()));
        }
        for (Participants participants : user.getParticipants()) {
            collection.add(new SimpleGrantedAuthority(participants.getParticipantsKey().getParticipantType().getName()));
        }
        return collection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CurrentGuest{" +
                "id=" + id +
                '}';
    }
}
