package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
	
	@RequestMapping("/invalidUrl")
	public ModelAndView invalidUrl() {
		return new ModelAndView("errorLink");
	}
	
}

