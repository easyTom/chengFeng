package com.tom.cf.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Roy
 * @Date: 2019-03-06 11:06
 */
@Controller
@RequestMapping("/nmis/quality/complaint")
public class ComplaintController {
	
	private static final String URL = "backend/pages/complaint/";
	
	@GetMapping
	public String complaintList() {
		return URL + "complaint_list";
	}
	
	@GetMapping("/complaintDetail/{id}")
	public String complaintDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("id", id);
		return URL + "complaint_detail";
	}
}
