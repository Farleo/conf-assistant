package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {
	
	@Autowired
	private ConferenceService conferenceService;

	@RequestMapping(value = "dashboard/conferences")
	public String indexConfDashboard (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
		if(SecurityUtil.userHasAdminRole(currentUser)){
			model.addAttribute("conferences", conferenceService.getAllConferencesDTO());
			return "dashboard/main";
		}
		else if(SecurityUtil.userHasConfOwnerRole(currentUser)){
			model.addAttribute("conferences", conferenceService.getConferencesDTOByOwnerId(currentUser.getId()));
			return "dashboard/main";
		}
		return "redirect:/";
	}
	
}
