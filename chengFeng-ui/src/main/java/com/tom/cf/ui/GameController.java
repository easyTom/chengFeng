package com.tom.cf.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tom/game")
public class GameController {

    private static final String VIEW = "backend/pages/game/";

    @GetMapping("/{simple}")
    public String simple(@PathVariable("simple") String simple) {
        return VIEW+simple;
    }




}
