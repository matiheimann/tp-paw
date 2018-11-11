package ar.edu.itba.pawddit.webapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.SubscriptionService;
import ar.edu.itba.pawddit.services.exceptions.GroupAlreadyExists;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateGroupForm;

@Controller
public class GroupController {

	private static final int POSTS_PER_PAGE = 5;
	private static final int GROUPS_PER_PAGE = 7;

	@Autowired
	private GroupService gs;

	@Autowired
	private PostService ps;

	@Autowired
	private SubscriptionService ss;

	@RequestMapping(value = "/searchGroup", method = { RequestMethod.GET })
	public @ResponseBody List<String> searchGroups(@RequestParam(value = "name", required = false) final String groupName) {
		List<String> groups = gs.search5NamesByString(groupName);
		return groups;
	}

	@RequestMapping("/createGroup")
	public ModelAndView createGroup(@ModelAttribute("createGroupForm") final CreateGroupForm form, boolean isGroupRepeated) {
		final ModelAndView mav = new ModelAndView("createGroup");
		mav.addObject("groupAlreadyExistsError", isGroupRepeated);
		return mav;
	}

	@RequestMapping(value = "/createGroup", method = { RequestMethod.POST })
	public ModelAndView createGroupPost(@Valid @ModelAttribute("createGroupForm") final CreateGroupForm form, final BindingResult errors, @ModelAttribute("user") final User user) {
		if(errors.hasErrors()) {
			return createGroup(form, false);
		}

		final Group group;

		try {
			group = gs.create(form.getName(), LocalDateTime.now(), form.getDescription(), user);
		}
		catch(GroupAlreadyExists e) {
			return createGroup(form, true);
		}

		final ModelAndView mav = new ModelAndView("redirect:/group/" + group.getName());
		return mav;
	}

	@RequestMapping(value =  "/group/{groupName}/delete", method = { RequestMethod.POST })
	public ModelAndView deletePost(@PathVariable final String groupName, @ModelAttribute("user") final User user) {
		final Group group = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		if (user != null) {
			gs.delete(user, group);
		}
		final ModelAndView mav = new ModelAndView("redirect:/");
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
		final Group group = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);

		ss.suscribe(user, group);

		final ModelAndView mav = new ModelAndView("redirect:/group/" + groupName);

		return mav;
	}

	@RequestMapping(value = "/group/{groupName}/unsubscribe", method = { RequestMethod.POST })
	public ModelAndView groupUnsubscribe(@PathVariable final String groupName, @ModelAttribute("user") final User user) {
		final Group group = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);

		ss.unsuscribe(user, group);

		final ModelAndView mav = new ModelAndView("redirect:/group/" + groupName);

		return mav;
	}

	@RequestMapping(value = "/groups")
	public ModelAndView groups(@RequestParam(defaultValue = "1", value="page") int page, @RequestParam(defaultValue = "", value="search") final String search) {
		final ModelAndView mav = new ModelAndView("groups");
		mav.addObject("groups", gs.searchGroupsByString(search, GROUPS_PER_PAGE, (page-1)*GROUPS_PER_PAGE));
		mav.addObject("search", search);
		mav.addObject("groupsPage", page);
		mav.addObject("groupsPageCount", (gs.searchGroupsByStringCount(search)+GROUPS_PER_PAGE-1)/GROUPS_PER_PAGE);
		return mav;
	}

	@RequestMapping(value = "/myGroups")
	public ModelAndView myGroups(@RequestParam(defaultValue = "1", value="page") int page, @ModelAttribute("user") final User user) {
		final ModelAndView mav = new ModelAndView("groups");
		mav.addObject("groups", gs.findSubscribedByUser(user, GROUPS_PER_PAGE, (page-1)*GROUPS_PER_PAGE));
		mav.addObject("groupsPage", page);
		mav.addObject("groupsPageCount", (gs.findSubscribedByUserCount(user)+GROUPS_PER_PAGE-1)/GROUPS_PER_PAGE);
		return mav;
	}

	@RequestMapping(value = "/recommendedGroups")
	public ModelAndView recommendedGroups(@ModelAttribute("user") final User user) {
		final ModelAndView mav = new ModelAndView("groups");
		mav.addObject("groups", gs.findRecommendedByUser(user, GROUPS_PER_PAGE));
		return mav;
	}


}
