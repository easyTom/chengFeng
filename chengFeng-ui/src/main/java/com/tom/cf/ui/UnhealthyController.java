package com.tom.cf.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nmis/quality/unhealthy")
public class UnhealthyController {
	
	private static final String URL = "backend/pages/unhealthy/";
	
	@GetMapping("/report")
	public String unhealthyReport(){
		return URL + "unhealthy_report";
	}
	
	@GetMapping("/list")
	public String unhealthyList(){
		return URL + "unhealthy_list";
	}
}
