package lms.itcluster.confassistant.util;

import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

    public static void authenticate(User user) {
        CurrentUser currentUser = new CurrentUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
