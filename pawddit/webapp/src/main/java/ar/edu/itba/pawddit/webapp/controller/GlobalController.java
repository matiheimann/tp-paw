package ar.edu.itba.pawddit.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;
import ar.edu.itba.pawddit.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.PostNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.VerificationTokenNotFoundException;

@ControllerAdvice
public class GlobalController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalController.class);
	
	@ModelAttribute("user")
	public User loggedUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			return null;
		}
		
		final User user = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		LOGGER.debug("Currently logged user is {}", user.getUserid());
		return user;
	}
	
	@ModelAttribute("groups")
	public List<Group> groups(@ModelAttribute("user") final User user) {
		if (user == null)
			return null;

		return gs.findSubscribedByUser(user, 5, 0);
	} 
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView noHandlerFound() {
		final ModelAndView mav = new ModelAndView("redirect:/invalidUrl");
		return mav;
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public RedirectView userNotFound(HttpServletRequest request) {
	    RedirectView rw = new RedirectView(request.getContextPath() + "/invalidUrl");
	    rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("errorUserNotFound", new Boolean(true));
	    }
	    return rw;
	}

	@ExceptionHandler(GroupNotFoundException.class)
	public RedirectView groupNotFound(HttpServletRequest request) {
	    RedirectView rw = new RedirectView(request.getContextPath() + "/invalidUrl");
	    rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("errorGroupNotFound", new Boolean(true));
	    }
	    return rw;
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public RedirectView postNotFound(HttpServletRequest request) {
	    RedirectView rw = new RedirectView(request.getContextPath() + "/invalidUrl");
	    rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("errorPostNotFound", new Boolean(true));
	    }
	    return rw;
	}
	
	@ExceptionHandler(CommentNotFoundException.class)
	public RedirectView commentNotFound(HttpServletRequest request) {
	    RedirectView rw = new RedirectView(request.getContextPath() + "/invalidUrl");
	    rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("errorCommentNotFound", new Boolean(true));
	    }
	    return rw;
	}
	
	@ExceptionHandler(VerificationTokenNotFoundException.class)
	public RedirectView verificationTokenNotFound(HttpServletRequest request) {
	    RedirectView rw = new RedirectView(request.getContextPath() + "/invalidUrl");
	    rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("errorVerificationTokenNotFound", new Boolean(true));
	    }
	    return rw;
	}
	
	@ExceptionHandler(ImageNotFoundException.class)
	public RedirectView imageNotFound(HttpServletRequest request) {
	    RedirectView rw = new RedirectView(request.getContextPath() + "/invalidUrl");
	    rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("errorImageNotFound", new Boolean(true));
	    }
	    return rw;
	}
	
	@ExceptionHandler(NoPermissionsException.class)
	public RedirectView noPermissions(HttpServletRequest request) {
	    RedirectView rw = new RedirectView(request.getContextPath() + "/invalidUrl");
	    rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("errorNoPermissions", new Boolean(true));
	    }
	    return rw;
	}
	
}
