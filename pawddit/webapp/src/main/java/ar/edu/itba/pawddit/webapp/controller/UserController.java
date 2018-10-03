package ar.edu.itba.pawddit.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;
import ar.edu.itba.pawddit.services.CommentService;
import ar.edu.itba.pawddit.services.MailSenderService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.services.exceptions.UserRepeatedDataException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.VerificationTokenNotFoundException;
import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;

@Controller
public class UserController {

	@Autowired
	private UserService us;

	@Autowired
	private PostService ps;
	
	@Autowired
	private CommentService cs;

	@Autowired
	private MailSenderService mss;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserRegisterForm form, final Boolean usernameExistsError, final Boolean emailExistsError) {
		final ModelAndView mav = new ModelAndView("register");
		mav.addObject("usernameExistsError", usernameExistsError);
		mav.addObject("emailExistsError", emailExistsError);
		return mav;
	}

	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public ModelAndView registerPost(final HttpServletRequest req, @Valid @ModelAttribute("registerForm") final UserRegisterForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return register(form, false, false);
		}

		final User user;

		try {
			user = us.create(form.getUsername(), form.getPassword(), form.getEmail(), false);
		}
		catch(UserRepeatedDataException e) {
			Boolean usernameExistsError = false;
			Boolean emailExistsError = false;
			if(e.isUsernameRepeated())
				usernameExistsError = true;
			if(e.isEmailRepeated())
				emailExistsError = true;
			return register(form, usernameExistsError, emailExistsError);
		}
		
		final StringBuffer url = req.getRequestURL();
		final String uri = req.getRequestURI();
		final String ctx = req.getContextPath();
		final String base = url.substring(0, url.length() - uri.length() + ctx.length());
		
		final VerificationToken token = us.createToken(user);
		mss.sendVerificationToken(user, token, base);

		return new ModelAndView("verifyAccount");
	}

	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(value = "error", required=false) final boolean error) {
		ModelAndView mav = new ModelAndView("login");

		if(error) {
			mav.addObject("loginError", new Boolean(true));
			return mav;
		}

		mav.addObject("loginError", new Boolean(false));
		return mav;
	}

	@RequestMapping("/profile/{profile}")
	public ModelAndView profile(@PathVariable final String profile, @ModelAttribute("user") final User user) {
		final User userProfile = us.findByUsername(profile).orElseThrow(UserNotFoundException::new);
		final ModelAndView mav = new ModelAndView("profile");
		mav.addObject("userProfile", userProfile);
		mav.addObject("posts", ps.findByUser(userProfile, 5, 0, null));
		mav.addObject("comments", cs.findByUser(userProfile, 5, 0));

		return mav;
	}

	@RequestMapping("/registrationConfirm")
	public ModelAndView confirmRegistration(final HttpServletRequest request, @RequestParam(value = "token", required = false) String token) {
		if (token == null)
			throw new VerificationTokenNotFoundException();
	    final VerificationToken verificationToken = us.findToken(token).orElseThrow(VerificationTokenNotFoundException::new);
	    final User user = verificationToken.getUser();
	    us.enableUser(user);
	    
		/* Auto Login */
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
	    authToken.setDetails(new WebAuthenticationDetails(request));
	    Authentication authentication = authenticationManager.authenticate(authToken);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	  	final ModelAndView mav = new ModelAndView("confirmedAccount");
	    mav.addObject("user", user);
	    return mav;
	}
}
