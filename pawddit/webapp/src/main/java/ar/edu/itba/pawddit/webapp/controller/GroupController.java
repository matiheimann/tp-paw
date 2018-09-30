package ar.edu.itba.pawddit.webapp.controller;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.SubscriptionService;
import ar.edu.itba.pawddit.services.exceptions.GroupAlreadyExists;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateGroupForm;

@Controller
public class GroupController {
	
	private static final int POSTS_PER_PAGE = 5;

	@Autowired
	private GroupService gs;

	@Autowired
	private PostService ps;
	
	@Autowired
	private SubscriptionService ss;

	@RequestMapping("/createGroup")
	public ModelAndView createGroup(@ModelAttribute("createGroupForm") final CreateGroupForm form, boolean isGroupRepeated) {
		final ModelAndView mav = new ModelAndView("createGroup");
		if(isGroupRepeated)
			mav.addObject("groupAlreadyExistsError", new Boolean(true));
		return mav;
	}

	@RequestMapping(value = "/createGroup", method = { RequestMethod.POST })
	public ModelAndView createGroupPost(@Valid @ModelAttribute("createGroupForm") final CreateGroupForm form, final BindingResult errors, @ModelAttribute("user") final User user) {
		if(errors.hasErrors()) {
			return createGroup(form, false);
		}
		
		final Group group;
		
		try {
			group = gs.create(form.getName(), new Timestamp(System.currentTimeMillis()), form.getDescription(), user);
		}
		catch(GroupAlreadyExists e) {
			return createGroup(form, true);
		}
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + group.getName());
		return mav;
	}

	@RequestMapping("/group/{groupName}")
	public ModelAndView showGroup(@PathVariable final String groupName, @RequestParam(defaultValue = "1", value="page") int page, @RequestParam(defaultValue = "new", value="sort") String sort, @ModelAttribute("user") final User user) {
		final ModelAndView mav = new ModelAndView("index");
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		mav.addObject("group", g);
		
		if (user != null) {
			mav.addObject("subscription", ss.isUserSub(user, g));
		}
		
		mav.addObject("posts", ps.findByGroup(g, POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort));
		mav.addObject("postsPage", page);
		mav.addObject("postsPageCount", (ps.findByGroupCount(g)+POSTS_PER_PAGE-1)/POSTS_PER_PAGE);
		return mav;
	}
	
	@RequestMapping(value = "/group/{groupName}/subscribe", method = { RequestMethod.POST })
	public ModelAndView groupSubscribe(@PathVariable final String groupName, @ModelAttribute("user") final User user) {
		final Group group = gs.findByName(groupName).orElseThrow(UserNotFoundException::new);
	
		ss.suscribe(user, group);
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + groupName);
		
		return mav;
	}
	
	@RequestMapping(value = "/group/{groupName}/unsubscribe", method = { RequestMethod.POST })
	public ModelAndView groupUnsubscribe(@PathVariable final String groupName, @ModelAttribute("user") final User user) {
		final Group group = gs.findByName(groupName).orElseThrow(UserNotFoundException::new);
	
		ss.unsuscribe(user, group);

		final ModelAndView mav = new ModelAndView("redirect:/group/" + groupName);
		
		return mav;
	}
	
}
