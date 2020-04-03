package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.model.CurrentUser;

public interface SecurityService {
	boolean canManageConference (CurrentUser currentUser, Long confId);
}
