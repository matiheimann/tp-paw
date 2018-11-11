package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.PostService;

@Controller
public class IndexController {
	
	private static final int POSTS_PER_PAGE = 5;

	@Autowired
	private PostService ps;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(defaultValue = "1", value="page") int page, @RequestParam(defaultValue = "new", value="sort") String sort, @RequestParam(defaultValue = "all", value="time") String time, @ModelAttribute("user") final User user)
	{
		ModelAndView mav;

		if (user == null || user.getSubscribedGroups().isEmpty()) {
			mav = new ModelAndView("redirect:/all");
		}
		else {
			mav = new ModelAndView("index");
			mav.addObject("posts", ps.findBySubscriptions(user, POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort, time));
			mav.addObject("postsPage", page);
			mav.addObject("postsPageCount", (ps.findBySubscriptionsCount(user, time)+POSTS_PER_PAGE-1)/POSTS_PER_PAGE);
		}
		return mav;
	}

	@RequestMapping("/all")
	public ModelAndView all(@RequestParam(defaultValue = "1", value="page") int page, @RequestParam(defaultValue = "new", value="sort") String sort, @RequestParam(defaultValue = "all", value="time") String time)
	{
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("posts", ps.findAll(POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort, time));
		mav.addObject("postsPage", page);
		mav.addObject("postsPageCount", (ps.findAllCount(time)+POSTS_PER_PAGE-1)/POSTS_PER_PAGE);
		return mav;
	}
}
