package com.tom.cf.ui.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tom/study")
public class StudyController {

    private static final String VIEW = "backend/pages/study/";
    private static final String DEMO_VIEW = "backend/pages/study/demo/";

    /*
     * @Author: Tom     通用跳转
     * @Date: 2019/4/9 13:52
     */

    @GetMapping("/{simple}")
    public String simple(@PathVariable("simple") String simple) {
        return VIEW+simple;
    }

    @GetMapping("/demo/{simple}")
    public String simpleDemo(@PathVariable("simple") String simple) {
        return DEMO_VIEW+simple;
    }




}
