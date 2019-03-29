package com.tom.cf.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tom/study")
public class StudyController {

    private static final String VIEW = "backend/pages/study/";

    private static final String MISTAKE = VIEW+"mistake";

    @GetMapping("/mistake")
    public String mistakeList() {
        return MISTAKE;
    }



}
