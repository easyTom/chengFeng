package com.tom.cf.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @method: 工具
 */
@Controller
@RequestMapping("/tom/util")
public class UtilController {

    private static final String PDF="backend/pages/util/pdf";

    @GetMapping("/pdf")
    public String pdf(String url,Model model){
        model.addAttribute("url",url);
        return PDF;
    }
}
