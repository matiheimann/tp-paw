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
import ar.edu.itba.pawddit.webapp.form.CreatePostForm;
import ar.edu.itba.pawddit.webapp.form.CreatePostNoGroupForm;

@Controller
public class PostController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private PostService ps;
	
	@RequestMapping("/createPost")
	public ModelAndView createPost(@RequestParam(value = "userId", required = true) final Integer id, @ModelAttribute("postForm") final CreatePostNoGroupForm form) {
		final ModelAndView mav = new ModelAndView("createPost");
		mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
		return mav;
	}
	
	@RequestMapping(value = "/createPost", method = { RequestMethod.POST })
	public ModelAndView createPostPost(@RequestParam(value = "userId", required = true) final Integer id, @Valid @ModelAttribute("postForm") final CreatePostNoGroupForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return createPost(id, form);
		}
		
		final User u = us.findById(id).orElseThrow(UserNotFoundException::new);
		final Group g = gs.findByName(form.getGroupName()).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.create(form.getTitle(), form.getContent(), new Timestamp(System.currentTimeMillis()), g, u);
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid() + "?userId=" + u.getUserid());
		return mav;
	}
	
	@RequestMapping("/group/{groupName}/createPost")
	public ModelAndView createPost(@PathVariable final String groupName, @RequestParam(value = "userId", required = true) final Integer id, @ModelAttribute("postForm") final CreatePostForm form) {
		final ModelAndView mav = new ModelAndView("createPost");
		mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
		mav.addObject("group", gs.findByName(groupName).orElseThrow(GroupNotFoundException::new));
		return mav;
	}
	
	@RequestMapping(value = "/group/{groupName}/createPost", method = { RequestMethod.POST })
	public ModelAndView createPostPost(@PathVariable final String groupName, @RequestParam(value = "userId", required = true) final Integer id, @Valid @ModelAttribute("postForm") final CreatePostForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return createPost(groupName, id, form);
		}
		
		final User u = us.findById(id).orElseThrow(UserNotFoundException::new);
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.create(form.getTitle(), form.getContent(), new Timestamp(System.currentTimeMillis()), g, u);
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid() + "?userId=" + u.getUserid());
		return mav;
	}
	
	@RequestMapping("/group/{groupName}/{postId}")
	public ModelAndView showPost(@PathVariable final String groupName, @PathVariable final Integer postId, @RequestParam(value = "userId", required = false) final Integer id) {
		final ModelAndView mav = new ModelAndView("post");
		if (id != null) {
			mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
		}
		mav.addObject("group", gs.findByName(groupName).orElseThrow(GroupNotFoundException::new));
		mav.addObject("post", ps.findById(postId).orElseThrow(PostNotFoundException::new));
		return mav;
	}
}
