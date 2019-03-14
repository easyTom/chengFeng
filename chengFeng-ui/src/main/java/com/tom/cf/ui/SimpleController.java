package com.tom.cf.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tom")
public class SimpleController {

    private static final String APPLY_INDEX="backend/pages/apply/apply_index";

    @GetMapping("")
    public String index(){
        return APPLY_INDEX;
    }

}
