package ar.edu.itba.pawddit.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.services.exceptions.UserRepeatedDataException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.form.CreateUserForm;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService us;
	
	@RequestMapping("/createUser")
	public ModelAndView createUser(@ModelAttribute("createUserForm") final CreateUserForm form, final Boolean usernameExistsError, final Boolean emailExistsError) {
		final ModelAndView mav = new ModelAndView("admin/createUser");
		mav.addObject("usernameExistsError", usernameExistsError);
		mav.addObject("emailExistsError", emailExistsError);
		return mav;
	}
	
	@RequestMapping(value = "/createUser", method = { RequestMethod.POST })
	public ModelAndView createUserPost(final HttpServletRequest req, @Valid @ModelAttribute("createUserForm") final CreateUserForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return createUser(form, false, false);
		}

		final User user;

		try {
			user = us.create(form.getUsername(), form.getPassword(), form.getEmail(), true);
		}
		catch(UserRepeatedDataException e) {
			Boolean usernameExistsError = false;
			Boolean emailExistsError = false;
			if(e.isUsernameRepeated())
				usernameExistsError = true;
			if(e.isEmailRepeated())
				emailExistsError = true;
			return createUser(form, usernameExistsError, emailExistsError);
		}

		return new ModelAndView("redirect:/profile/" + user.getUsername());
	}
	
	@RequestMapping(value =  "/deleteUser/{username}", method = { RequestMethod.POST })
	public ModelAndView deletePost(@PathVariable final String username) {
		final User user = us.findByUsername(username).orElseThrow(UserNotFoundException::new);
		
		us.deleteUser(user);
		
		final ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}
	
}
