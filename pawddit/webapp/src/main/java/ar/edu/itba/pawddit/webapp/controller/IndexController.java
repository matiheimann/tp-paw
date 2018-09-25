package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;

@Controller
public class IndexController {

	@Autowired
	private UserService us;

	@Autowired
	private PostService ps;

	@RequestMapping("/")
	public ModelAndView index()
	{
		final ModelAndView mav = new ModelAndView("index");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			mav.addObject("posts", ps.findAll());
		}
		else {
			final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
			mav.addObject("user", u);
			mav.addObject("posts", ps.findBySubscriptions(u));
		}
		return mav;
	}

	@RequestMapping("/all")
	public ModelAndView all()
	{
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final ModelAndView mav = new ModelAndView("index");
		final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		mav.addObject("user", u);
		mav.addObject("posts", ps.findAll());
		return mav;
	}

}
