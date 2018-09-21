package ar.edu.itba.pawddit.webapp.controller;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.SubscriptionService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateGroupForm;
import ar.edu.itba.pawddit.webapp.form.SubscriptionForm;

@Controller
public class GroupController {

	@Autowired
	private UserService us;

	@Autowired
	private GroupService gs;

	@Autowired
	private PostService ps;
	
	@Autowired
	private SubscriptionService ss;

	@RequestMapping("/createGroup")
	public ModelAndView createGroup(@ModelAttribute("createGroupForm") final CreateGroupForm form) {
		final ModelAndView mav = new ModelAndView("createGroup");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		mav.addObject("user", u);
		return mav;
	}

	@RequestMapping(value = "/createGroup", method = { RequestMethod.POST })
	public ModelAndView createGroupPost(@Valid @ModelAttribute("createGroupForm") final CreateGroupForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return createGroup(form);
		}

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		final Group g = gs.create(form.getName(), new Timestamp(System.currentTimeMillis()), form.getDescription(), u);
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName());
		return mav;
	}

	@RequestMapping("/group/{groupName}")
	public ModelAndView showGroup(@PathVariable final String groupName) {
		final ModelAndView mav = new ModelAndView("index");
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		mav.addObject("group", g);
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			User user = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
			mav.addObject("user", user);
			mav.addObject("subscription", ss.checkIfItsSuscribed(user, g));
		}
		mav.addObject("posts", ps.findByGroup(g));
		return mav;
	}
	
	@RequestMapping(value = "/group/{groupName}", method = { RequestMethod.POST })
	public ModelAndView groupsSubscriptions(@PathVariable final String groupName, @ModelAttribute("SubscriptionForm") final SubscriptionForm form) {

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		Group group = gs.findByName(groupName).orElseThrow(UserNotFoundException::new);
		
		if(form.getValue() == 1) {
			ss.suscribe(user, group);
		}
		else {
			ss.unsuscribe(user, group);
		}
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + groupName);
		
		return mav;
	}
	
}
