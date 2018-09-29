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
public class IndexController extends BaseController {
	
	private static final int POSTS_PER_PAGE = 5;

	@Autowired
	private PostService ps;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(defaultValue = "1", value="page") int page, @RequestParam(defaultValue = "new", value="sort") String sort, @ModelAttribute("user") final User user)
	{
		final ModelAndView mav = new ModelAndView("index");

		if (user == null) {
			mav.addObject("posts", ps.findAll(POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort));
			mav.addObject("postsPage", page);
			mav.addObject("postsPageCount", (ps.findAllCount()+POSTS_PER_PAGE-1)/POSTS_PER_PAGE);
		}
		else {
			mav.addObject("posts", ps.findBySubscriptions(user, POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort));
			mav.addObject("postsPage", page);
			mav.addObject("postsPageCount", (ps.findBySubscriptionsCount(user)+POSTS_PER_PAGE-1)/POSTS_PER_PAGE);
		}
		return mav;
	}

	@RequestMapping("/all")
	public ModelAndView all(@RequestParam(defaultValue = "1", value="page") int page, @RequestParam(defaultValue = "new", value="sort") String sort)
	{
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("posts", ps.findAll(POSTS_PER_PAGE, (page-1)*POSTS_PER_PAGE, sort));
		mav.addObject("postsPage", page);
		mav.addObject("postsPageCount", (ps.findAllCount()+POSTS_PER_PAGE-1)/POSTS_PER_PAGE);
		return mav;
	}
}
