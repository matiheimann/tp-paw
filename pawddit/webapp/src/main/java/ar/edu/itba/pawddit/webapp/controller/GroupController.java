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
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateGroupForm;

@Controller
public class GroupController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private PostService ps;
	
	@RequestMapping("/createGroup")
	public ModelAndView createGroup(@RequestParam(value = "userId", required = true) final Integer id, @ModelAttribute("groupForm") final CreateGroupForm form) {
		final ModelAndView mav = new ModelAndView("createGroup");
		final User u = us.findById(id).orElseThrow(UserNotFoundException::new);
		mav.addObject("user", u);
		return mav;
	}
	
	@RequestMapping(value = "/createGroup", method = { RequestMethod.POST })
	public ModelAndView createGroupPost(@RequestParam(value = "userId", required = true) final Integer id, @Valid @ModelAttribute("groupForm") final CreateGroupForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return createGroup(id, form);
		}
		
		final User u = us.findById(id).orElseThrow(UserNotFoundException::new);
		final Group g = gs.create(form.getTitle(), new Timestamp(System.currentTimeMillis()), form.getDescription(), u);
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "?userId=" + u.getUserid());
		return mav;
	}
	
	@RequestMapping("/group/{groupName}")
	public ModelAndView showGroup(@PathVariable final String groupName, @RequestParam(value = "userId", required = false) final Integer id) {
		final ModelAndView mav = new ModelAndView("index");
		if (id != null) {
			mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
		}
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		mav.addObject("group", g);
		mav.addObject("posts", ps.findByGroup(g));

		return mav;
	}
}
