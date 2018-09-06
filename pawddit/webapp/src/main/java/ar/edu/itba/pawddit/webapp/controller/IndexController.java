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
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.PostNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateGroupForm;
import ar.edu.itba.pawddit.webapp.form.CreatePostForm;
import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;

@Controller
public class IndexController {

	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private PostService ps;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(value = "userId", required = false) final Integer id)
	{
		if (id == null) {
			final ModelAndView mav = new ModelAndView("welcome");
			mav.addObject("posts", ps.findAll());
			return mav;
		}
		else {
			final ModelAndView mav = new ModelAndView("index");
			mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
			mav.addObject("posts", ps.findAll());
			return mav;
		}
	}

}
