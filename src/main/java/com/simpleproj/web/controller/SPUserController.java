package com.simpleproj.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.simpleproj.model.SPUser;
import com.simpleproj.service.SPUserService;
import com.simpleproj.utils.SPUserFormValidator;
import com.simpleproj.web.requestmodel.SPUserLoginRequestModel;
import com.simpleproj.web.requestmodel.SPUserRegisterRequestModel;

@Controller
public class SPUserController {

	@Autowired
	private SPUserService userService;
	
	@Autowired
	private SPUserFormValidator userFormValidator;
	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.setValidator(userFormValidator);
//	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute("user") SPUserRegisterRequestModel userModel, BindingResult result,
			HttpSession session, HttpServletResponse response, HttpServletRequest request) {

		SPUser user = userService.createUser(userModel);
		session.setAttribute("user", user);
		Cookie userCookie = WebUtils.getCookie(request, "userId");
		if (userCookie != null) {
			userCookie.setMaxAge(0);
		}
		userCookie = new Cookie("userId", Long.toString(user.getId()));
		userCookie.setMaxAge(1800 * 1800);
		response.addCookie(userCookie);
		return new ModelAndView("redirect:/tasklist");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("user") SPUserLoginRequestModel userModel, BindingResult result, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {

		SPUser user = userService.getUserByLogin(userModel.getLogin());
		session.setAttribute("user", user);
		Cookie userCookie = WebUtils.getCookie(request, "userId");
		if (userCookie != null) {
			userCookie.setMaxAge(0);
		}
		userCookie = new Cookie("userId", Long.toString(user.getId()));
		userCookie.setMaxAge(1800 * 1800);
		response.addCookie(userCookie);
		return new ModelAndView("redirect:/tasklist");
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		session.invalidate();
		Cookie userCookie = WebUtils.getCookie(request, "userId");
		if (userCookie != null) {
			userCookie.setMaxAge(0);
		}
		return "redirect:/login";
	}
}
