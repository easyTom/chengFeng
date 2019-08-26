package com.tom.cf.mobile.api;

import org.springframework.validation.BindingResult;

public abstract class BaseController {
	
	/**
	 * 验证通过返回true
	 */
	protected boolean validateDTO(BindingResult bindingResult){
		return !bindingResult.hasErrors();
	}
}
