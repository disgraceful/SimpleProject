package com.simpleproj.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.simpleproj.service.SPUserService;
import com.simpleproj.web.requestmodel.SPUserRegisterRequestModel;

@Component
public class SPUserFormValidator implements Validator {
	@Autowired
	@Qualifier("emailValidator")
	SPEmailValidator emailValidator;

	@Autowired
	SPUserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return SPUserRegisterRequestModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		SPUserRegisterRequestModel user = (SPUserRegisterRequestModel) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty.user.login");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.user.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.user.confirmPassword");

		if (!emailValidator.valid(user.getLogin())) {
			errors.rejectValue("login", "Pattern.user.email");
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Diff.userform.confirmPassword");
		}
	}
}