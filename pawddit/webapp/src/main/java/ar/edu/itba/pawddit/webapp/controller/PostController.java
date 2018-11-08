package ar.edu.itba.pawddit.webapp.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.CommentService;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.ImageService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.PostVoteService;
import ar.edu.itba.pawddit.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.PostNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateCommentForm;
import ar.edu.itba.pawddit.webapp.form.CreatePostForm;

@Controller
public class PostController {
	
	private static final int COMMENTS_PER_PAGE = 5;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private PostService ps;
	
	@Autowired
	private CommentService cs;
	
	@Autowired
	private PostVoteService pvs;
	
	@Autowired
	private ImageService is;
	
	@RequestMapping("/createPost")
	public ModelAndView createPost(@ModelAttribute("createPostForm") final CreatePostForm form, @RequestParam(value = "error", required = false) final Boolean imageUploadError) {
		final ModelAndView mav = new ModelAndView("createPost");
		mav.addObject("imageUploadError", imageUploadError);
		return mav;
	}
	
	@RequestMapping(value = "/createPost", method = { RequestMethod.POST })
	public ModelAndView createPostPost(@Valid @ModelAttribute("createPostForm") final CreatePostForm form, final BindingResult errors, @ModelAttribute("user") final User user) {
		if(errors.hasErrors()) {
			return createPost(form, false);
		}
		
		final Group g = gs.findByName(form.getGroupName()).orElseThrow(GroupNotFoundException::new);
		
		String imageId = null;
		try {
			final MultipartFile file = form.getFile();
			if (!file.isEmpty()) {

				ByteArrayInputStream in = new ByteArrayInputStream(file.getBytes());
				BufferedImage img = ImageIO.read(in);
				
				int width = img.getWidth();
				int height = img.getHeight();
				int maxW = 770;
				int maxH = 500;
				if (width > maxW && height < maxH) {
					height = (maxW * height) / width; 
					width = maxW;
				}
				else if (height > maxH && width < maxW) {
					width = (maxH * width) / height;
					height = maxH;
				}
				else if (width > maxW && height > maxH) {
					if (width-maxW > height-maxH) {
						height = (maxW * height) / width; 
						width = maxW;
					}
					else {
						width = (maxH * width) / height;
						height = maxH;
					}
				}
				
				Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

	            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	            if (file.getContentType().equals("image/png"))
	            	ImageIO.write(imageBuff, "png", buffer);
	            else
	            	ImageIO.write(imageBuff, "jpg", buffer);
	            
				imageId = is.saveImage(buffer.toByteArray());
			}
		}
		catch (IOException e) {
			return createPost(form, true);
		}

		final Post p = ps.create(form.getTitle(), form.getContent(), new Timestamp(System.currentTimeMillis()), g, user, imageId);
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		return mav;
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView maxUploadSizeExceededException() {
		return new ModelAndView("redirect:/createPost?error=true");	
	}
	
	@RequestMapping(value =  "/group/{groupName}/{postId}/delete", method = { RequestMethod.POST })
	public ModelAndView deletePost(@PathVariable final String groupName, @PathVariable final Integer postId, @ModelAttribute("user") final User user) {
		final Group group = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post post = ps.findById(group, postId).orElseThrow(PostNotFoundException::new);
		if (user != null) {
			ps.delete(user, group, post);
		}
		final ModelAndView mav = new ModelAndView("redirect:/group/" + group.getName());
		return mav;
	}
	
	@RequestMapping("/group/{groupName}/{postId}")
	public ModelAndView showPost(@PathVariable final String groupName, @PathVariable final Integer postId, @RequestParam(defaultValue = "1", value="page") int page, @ModelAttribute("createCommentForm") final CreateCommentForm form, @ModelAttribute("user") final User user) {
		final ModelAndView mav = new ModelAndView("post");
		final Group group = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post post = ps.findById(group, postId).orElseThrow(PostNotFoundException::new);
		if (user != null) {
			final Integer vote = pvs.checkVote(user, post);
			mav.addObject("vote", vote);
		}
		mav.addObject("group", group);
		mav.addObject("post", post);
		mav.addObject("comments", cs.findByPost(post, COMMENTS_PER_PAGE, (page-1)*COMMENTS_PER_PAGE));
		mav.addObject("commentsPage", page);
		mav.addObject("commentsPageCount", (cs.findByPostCount(post)+COMMENTS_PER_PAGE-1)/COMMENTS_PER_PAGE);
		return mav;
	}
	
	@RequestMapping(value = "/group/{groupName}/{postId}", method = { RequestMethod.POST })
	public ModelAndView createComment(@PathVariable final String groupName, @PathVariable final Integer postId, @Valid @ModelAttribute("createCommentForm") final CreateCommentForm form, final BindingResult errors, @ModelAttribute("user") final User user) {
		if(errors.hasErrors()) {
			return showPost(groupName, postId, 1, form, user);
		}

		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.findById(g, postId).orElseThrow(PostNotFoundException::new);
		cs.create(form.getContent(), p, null, user, new Timestamp(System.currentTimeMillis()));
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		return mav;
	}
	
	@RequestMapping(value =  "/group/{groupName}/{postId}/comment/{commentId}/delete", method = { RequestMethod.POST })
	public ModelAndView deleteComment(@PathVariable final String groupName, @PathVariable final Integer postId, @PathVariable final Integer commentId, @ModelAttribute("user") final User user) {
		final Group group = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post post = ps.findById(group, postId).orElseThrow(PostNotFoundException::new);
		final Comment comment = cs.findById(post, commentId).orElseThrow(CommentNotFoundException::new);
		if (user != null) {
			cs.delete(user, group, post, comment);
		}
		final ModelAndView mav = new ModelAndView("redirect:/group/" + group.getName()  + "/" + post.getPostid());
		return mav;
	}
	
	@RequestMapping(value="/group/{groupName}/{postId}/upvote", method = {RequestMethod.POST})
	public ModelAndView upvotePost(@PathVariable final Integer postId, @PathVariable final String groupName, @ModelAttribute("user") final User user) {
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.findById(g, postId).orElseThrow(PostNotFoundException::new);
		pvs.upVote(user, p);
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		
		return mav;
	}
	
	@RequestMapping(value="/group/{groupName}/{postId}/downvote", method = {RequestMethod.POST})
	public ModelAndView downvotePost(@PathVariable final Integer postId, @PathVariable final String groupName, @ModelAttribute("user") final User user) {
		final Group g = gs.findByName(groupName).orElseThrow(GroupNotFoundException::new);
		final Post p = ps.findById(g, postId).orElseThrow(PostNotFoundException::new);
		pvs.downVote(user, p);
		
		final ModelAndView mav = new ModelAndView("redirect:/group/" + g.getName() + "/" + p.getPostid());
		
		return mav;
	}
	
}
