package com.simpleproj.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPUser;
import com.simpleproj.service.SPProjectService;
import com.simpleproj.service.SPTaskService;
import com.simpleproj.service.SPUserService;
import com.simpleproj.web.requestmodel.SPUserLoginRequestModel;
import com.simpleproj.web.requestmodel.SPUserRegisterRequestModel;

@Controller
public class SPWelcomeController {
	@Autowired
	private SPUserService userService;

	@Autowired
	private SPProjectService projService;

	@Autowired
	private SPTaskService taskService;

	@RequestMapping(value = "/login")
	public ModelAndView loginPage() {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("user", new SPUserLoginRequestModel());
		return mav;
	}

	@RequestMapping(value = "/register")
	public ModelAndView registerPage() {
		ModelAndView mav = new ModelAndView("register");
		mav.addObject("user", new SPUserRegisterRequestModel());
		return mav;
	}

	@RequestMapping(value = "/tasklist")
	public ModelAndView welcome(HttpSession session, ModelMap model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("tasklistmain");
		SPUser user = (SPUser) session.getAttribute("user");
		Cookie userCookie = WebUtils.getCookie(request, "userId");
		if (user == null) {
			if (userCookie != null) {
				user = userService.getUserById(Long.parseLong(userCookie.getValue()));

			} else {
				model.addAttribute("user", new SPUserLoginRequestModel());
				return new ModelAndView("redirect:/login", model);
			}
		}

		mav.addObject("user", user);
		mav.addObject("projects", projService.getProjectsByUserId(user.getId()));

		for (SPProject proj : projService.getProjectsByUserId(user.getId())) {
			
		}
		return mav;
	}
}
