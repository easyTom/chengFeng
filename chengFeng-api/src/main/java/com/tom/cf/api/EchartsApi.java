package com.tom.cf.api;

import com.tom.cf.core.entity.ResultTwo;
import com.tom.cf.core.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @method:Echarts
 */
@Controller
@RequestMapping("/tom/api/study/echarts")
public class EchartsApi {

    @Autowired
    private EchartsService echartsService;

    @RequestMapping("/getZZData")
    public ResponseEntity<?> getZZData() {
        List<Map<String, Object>> list = echartsService.getZZData();
        return ResponseEntity.ok(list);
    }
    @RequestMapping("/getBZData")
    public ResponseEntity<?> getBZData() {
        List<Map<String, Object>> list = echartsService.getBZData();
        return ResponseEntity.ok(list);
    }
    @RequestMapping("/getZSData")
    public ResponseEntity<?> getZSData() {
        List<ResultTwo> list = echartsService.getZSData();
        return ResponseEntity.ok(list);
    }
}
