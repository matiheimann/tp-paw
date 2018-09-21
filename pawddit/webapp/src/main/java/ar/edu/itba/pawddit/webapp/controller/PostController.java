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
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.CommentService;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.PostNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateCommentForm;
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
	
	@Autowired
	private CommentService cs;
	
	@RequestMapping("/createPost")
	public ModelAndView createPost(@ModelAttribute("createPostForm") final CreatePostNoGroupForm form) {
		final ModelAndView mav = new ModelAndView("createPost");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("user", us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new));
		mav.addObject("groups", gs.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/createPost", method = { RequestMethod.POST })
	public ModelAndView createPostPost(@Valid @ModelAttribute("createPostForm") final CreatePostNoGroupForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return createPost(form);
		}
		
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		final Group g = gs.findByName(form.getGroupName()).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.create(form.getTitle(), form.getContent(), new Timestamp(System.currentTimeMillis()), g, u);
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		return mav;
	}
	
	@RequestMapping("/group/{groupName}/createPost")
	public ModelAndView createPost(@PathVariable final String groupName, @ModelAttribute("createPostForm") final CreatePostForm form) {
		final ModelAndView mav = new ModelAndView("createPost");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("user", us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new));
		mav.addObject("group", gs.findByName(groupName).orElseThrow(GroupNotFoundException::new));
		return mav;
	}

	
	@RequestMapping(value = "/group/{groupName}/createPost", method = { RequestMethod.POST })
	public ModelAndView createPostPost(@PathVariable final String groupName, @Valid @ModelAttribute("createPostForm") final CreatePostForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return createPost(groupName, form);
		}
		
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.create(form.getTitle(), form.getContent(), new Timestamp(System.currentTimeMillis()), g, u);
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		return mav;
	}
	
	@RequestMapping("/group/{groupName}/{postId}")
	public ModelAndView showPost(@PathVariable final String groupName, @PathVariable final Integer postId, @ModelAttribute("createCommentForm") final CreateCommentForm form) {
		final ModelAndView mav = new ModelAndView("post");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			mav.addObject("user", us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new));
		}
		final Group group = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post post = ps.findById(group, postId).orElseThrow(PostNotFoundException::new);
		mav.addObject("group", group);
		mav.addObject("post", post);
		mav.addObject("comments", cs.findByPost(post));
		return mav;
	}
	
	@RequestMapping(value = "/group/{groupName}/{postId}/createComment", method = { RequestMethod.POST })
	public ModelAndView showPost(@PathVariable final String groupName, @PathVariable final Integer postId, @Valid @ModelAttribute("createCommentForm") final CreateCommentForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return showPost(groupName, postId, form);
		}
		
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.findById(g, postId).orElseThrow(PostNotFoundException::new);
		cs.create(form.getContent(), p, null, u, new Timestamp(System.currentTimeMillis()));
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		return mav;
	}
}
