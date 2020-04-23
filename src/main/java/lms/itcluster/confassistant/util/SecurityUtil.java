package lms.itcluster.confassistant.util;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.impl.RememberMeService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public final class SecurityUtil {

    public static Authentication authenticate(User user) {
        CurrentUser currentUser = new CurrentUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public static void authentificateWithRememberMe(User user) {
        Authentication authentication = authenticate(user);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        WebApplicationContext ctx = (WebApplicationContext) request.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        RememberMeService rememberMeService = ctx.getBean(RememberMeService.class);
        rememberMeService.createAutoLoginToken(request, requestAttributes.getResponse(), authentication);
    }

    public static boolean canEditConference(CurrentUser currentUser, ConferenceDTO conferenceDTO) {
        if (userHasConfOwnerRole(currentUser)) {
            return conferenceDTO.getOwner().equals(currentUser.getId());
        }
        return userHasAdminRole(currentUser);
    }

    public static boolean userHasAdminRole(CurrentUser currentUser) {
        return userHasAuthority(currentUser, "ROLE_ADMIN");
    }

    public static boolean userHasConfOwnerRole(CurrentUser currentUser) {
        return userHasAuthority(currentUser, "ROLE_CONFOWNER");
    }

    public static boolean userHasUserRole(CurrentUser currentUser) {
        return userHasAuthority(currentUser, "ROLE_USER");
    }

    public static boolean userHasConfAdminRole(CurrentUser currentUser) {
        return userHasAuthority(currentUser, "admin");
    }

    public static boolean userHasConfModerRole(CurrentUser currentUser) {
        return userHasAuthority(currentUser, "moder");
    }

    public static boolean userHasConfSpeakerRole(CurrentUser currentUser) {
        return userHasAuthority(currentUser, "speaker");
    }

    public static boolean userHasConfVisitorRole(CurrentUser currentUser) {
        return userHasAuthority(currentUser, "visitor");
    }

    public static boolean userHasAuthority(CurrentUser currentUser, String role) {
        if (currentUser == null) {
            return false;
        }
        return currentUser.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(role));
    }

    public static boolean userHasAccessToConf(Conference conference, CurrentUser currentUser) {
        return (conference != null
                && (SecurityUtil.userHasConfOwnerRole(currentUser) && conference.getOwner().getUserId() == currentUser.getId()));
    }
}
