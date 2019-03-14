package com.tom.cf.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nmis/supervise")
public class SuperviseController {

    private static final String SUPERVISE_RULE_ADD="backend/pages/supervise/rule_add";
    private static final String SUPERVISE_RULE_LIST="backend/pages/supervise/rule_list";


    @GetMapping("/supervise_rule_add")
    public String supervise_rule_add(String status, String userId, Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("status", status);
        return SUPERVISE_RULE_ADD;
    }
    @GetMapping("/supervise_rule_list")
    public String supervise_rule_list(String status, String userId, Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("status", status);
        return SUPERVISE_RULE_LIST;
    }
}
