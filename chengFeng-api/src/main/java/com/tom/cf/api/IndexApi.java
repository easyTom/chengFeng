package com.tom.cf.api;

import com.tom.cf.core.entity.ResultTwo;
import com.tom.cf.core.service.StudyCodeService;
import com.tom.cf.core.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class IndexApi {

    @Autowired
    public StudyService studyService;

    @Autowired
    public StudyCodeService studyCodeService;

    @PostMapping("/index/getCounts")
    @ResponseBody
    public HashMap<String, Object> getCounts() {
        HashMap<String, Object> map = new HashMap<>();

        ResultTwo javaCount = studyService.getCount("Java知识点");

        ResultTwo mistakeCount = studyService.getCount("错误记录");

        ResultTwo rememberCount = studyService.getCount("备忘录");

        int codeCount = (int) studyCodeService.getCount();
        map.put("jc",javaCount);
        map.put("mc",mistakeCount);
        map.put("rc",rememberCount);
        map.put("cc",codeCount);
        return map;
    }


}
