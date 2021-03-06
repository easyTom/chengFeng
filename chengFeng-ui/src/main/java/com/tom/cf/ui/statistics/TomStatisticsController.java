package com.tom.cf.ui.statistics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tom/statistics")
public class TomStatisticsController {

    private static final String VIEW = "backend/pages/statistics/";

    /*
     * @Author: Tom     通用跳转
     * @Date: 2019/4/9 13:52
     */

    @GetMapping("/{simple}")
    public String simple(@PathVariable("simple") String simple) {
        return VIEW+"statistics_"+simple;
    }




}
