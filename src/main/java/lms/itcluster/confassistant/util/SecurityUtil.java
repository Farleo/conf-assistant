package lms.itcluster.confassistant.util;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public final class SecurityUtil {

    public static void authenticate(User user) {
        CurrentUser currentUser = new CurrentUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static boolean canCurrentUserEditTopic(CurrentUser currentUser, Stream stream) {
        for (GrantedAuthority authority : currentUser.getAuthorities()) {
            if (authority.getAuthority().equals("moder")) {
                if (stream.getModerator().getUserId() == currentUser.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean userHasAdminRole(CurrentUser currentUser){
        return userHasAuthority(currentUser, "ROLE_ADMIN");
    }

    public static boolean userHasConfOwnerRole(CurrentUser currentUser){
        return userHasAuthority(currentUser, "ROLE_CONFOWNER");
    }

    public static boolean userHasUserRole(CurrentUser currentUser){
        return userHasAuthority(currentUser, "ROLE_USER");
    }

    public static boolean userHasConfAdminRole(CurrentUser currentUser){
        return userHasAuthority(currentUser, "admin");
    }

    public static boolean userHasConfModerRole(CurrentUser currentUser){
        return userHasAuthority(currentUser, "moder");
    }

    public static boolean userHasConfSpeakerRole(CurrentUser currentUser){
        return userHasAuthority(currentUser, "speaker");
    }

    public static boolean userHasConfVisitorRole(CurrentUser currentUser){
        return userHasAuthority(currentUser, "visitor");
    }

    public static boolean userHasAuthority(CurrentUser currentUser, String role) {
        if(currentUser==null){
            return false;
        }
        return  currentUser.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(role));
    }

    public static boolean userHasAccessToConf(Conference conference, CurrentUser currentUser){
        return (conference!= null
                        && (SecurityUtil.userHasConfOwnerRole(currentUser)  && conference.getOwner().getUserId()==currentUser.getId()));
    }
}
