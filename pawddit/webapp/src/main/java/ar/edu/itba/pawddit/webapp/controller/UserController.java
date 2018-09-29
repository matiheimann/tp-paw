package ar.edu.itba.pawddit.webapp.controller;

import java.util.Optional;

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
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.MailSenderService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.services.exceptions.UserRepeatedDataException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.VerificationTokenNotFoundException;
import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;

@Controller
public class UserController extends GlobalController{

	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;

	@Autowired
	private PostService ps;

	@Autowired
	private MailSenderService mss;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserRegisterForm form) {
		return new ModelAndView("register");
	}

	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public ModelAndView registerPost(@Valid @ModelAttribute("registerForm") final UserRegisterForm form, final BindingResult errors, final HttpServletRequest request) {
		if(errors.hasErrors()) {
			return register(form);
		}

		final User user;
		final ModelAndView mav = new ModelAndView("register");

		try {
			user = us.create(form.getUsername(), form.getPassword(), form.getEmail(), 0);
		}
		catch(UserRepeatedDataException e) {
			if(e.isUsernameRepeated())
				mav.addObject("usernameExistsError", new Boolean(true));
			if(e.isEmailRepeated())
				mav.addObject("emailExistsError", new Boolean(true));
			return mav;
		}

		final VerificationToken token = us.createToken(user);
		mss.sendVerificationToken(user, token);

		/* Auto Login */
//		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//	    authToken.setDetails(new WebAuthenticationDetails(request));
//	    Authentication authentication = authenticationManager.authenticate(authToken);
//	    SecurityContextHolder.getContext().setAuthentication(authentication);

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

	@RequestMapping("/profile/{username}")
	public ModelAndView profile(@PathVariable final String username, @RequestParam(defaultValue = "1", value="page") int page) {
		final User userProfile = us.findByUsername(username).orElseThrow(UserNotFoundException::new);
		final ModelAndView mav = new ModelAndView("profile");
		mav.addObject("groups", gs.getSuscribed(loggedUser()));
		mav.addObject("userProfile", userProfile);
		mav.addObject("posts", ps.findByUser(userProfile, 5, (page-1)*5, null));

		return mav;
	}

	@RequestMapping("/registrationConfirm")
	public ModelAndView confirmRegistration(@RequestParam("token") String token) {    
	    final VerificationToken verificationToken = us.findToken(token).orElseThrow(VerificationTokenNotFoundException::new);
	    final User user = verificationToken.getUser();
	    us.enableUser(user);
	    return new ModelAndView("confirmedAccount");
	}
}
