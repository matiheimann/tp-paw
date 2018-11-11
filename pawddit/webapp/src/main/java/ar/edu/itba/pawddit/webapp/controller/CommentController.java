package ar.edu.itba.pawddit.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.CommentService;
import ar.edu.itba.pawddit.services.CommentVoteService;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.PostNotFoundException;

@Controller
public class CommentController {
	
	private static final int REPLIES_PER_LOAD = 5;
	
	@Autowired
	private CommentVoteService cvs;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private CommentService cs;
	
	@Autowired
	private PostService ps;
	
	@RequestMapping(value = "/group/{groupName}/{postId}/{commentId}", method = { RequestMethod.GET })
	public @ResponseBody List<Comment> loadReplies(@PathVariable final String groupName, @PathVariable final Integer postId, @PathVariable  final Integer commentId, @RequestParam(value = "offset", required = true) final int offset, @ModelAttribute("user") final User user) {
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.findById(user, g, postId).orElseThrow(PostNotFoundException::new);
		final Comment c = cs.findById(user, p, commentId).orElseThrow(CommentNotFoundException::new);
		
		final List<Comment> replies = cs.findRepliesByComment(user, c, REPLIES_PER_LOAD, offset);
		return replies;
	}
	
	@RequestMapping(value="/group/{groupName}/{postId}/{commentId}/upvote", method = {RequestMethod.POST})
	public ModelAndView upvoteComment(@PathVariable final String groupName, @PathVariable final Integer postId, @PathVariable  final Integer commentId, @ModelAttribute("user") final User user) {
		
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.findById(user, g, postId).orElseThrow(PostNotFoundException::new);
		final Comment c = cs.findById(user, p, commentId).orElseThrow(CommentNotFoundException::new);
		cvs.upVote(user, c);
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		
		return mav;
		
	}
	
	@RequestMapping(value="/group/{groupName}/{postId}/{commentId}/downvote", method = {RequestMethod.POST})
	public ModelAndView downvoteComment(@PathVariable final String groupName, @PathVariable final Integer postId, @PathVariable  final Integer commentId, @ModelAttribute("user") final User user) {
		
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.findById(user, g, postId).orElseThrow(PostNotFoundException::new);
		final Comment c = cs.findById(user, p, commentId).orElseThrow(CommentNotFoundException::new);
		cvs.downVote(user, c);
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		
		return mav;
		
	}

}
