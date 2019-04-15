package com.tom.cf.api;

import com.tom.cf.api.dto.DataTableRequest;
import com.tom.cf.api.dto.DataTableResponse;
import com.tom.cf.api.dto.HttpCode;
import com.tom.cf.api.dto.ResultDTO;
import com.tom.cf.core.entity.FcCol;
import com.tom.cf.core.service.StudyColService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @method:分类
 */
@RestController
@RequestMapping("/tom/api/study/col")
public class ColApi {

    @Autowired
    public StudyColService studyService;

    @RequestMapping("/list")
    public DataTableResponse list(DataTableRequest dr) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");//排序规则   多条件
        Page<FcCol> page = studyService.getAll(new PageRequest(dr.getPage(), dr.getIDisplayLength(), sort));
        return dr.createDataTableResponse(page);
    }
    @PostMapping("/init")
    public ResponseEntity list(){
        List<FcCol> list =  studyService.selectInit();
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        result.setData(list);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResultDTO getColById(@PathVariable("id") String id) {
        ResultDTO result = ResultDTO.newInstance();
        if (StringUtils.isNotBlank(id)) {
            Optional<FcCol> notice = studyService.getById(id);
            result.setData(notice.get());
        }
        result.setResultCode(HttpCode.OK);
        return result;
    }

    @PostMapping(value = "del")
    public ResponseEntity del(FcCol fc) {
        studyService.del(fc);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity save(FcCol fcCol){
        ResultDTO result = ResultDTO.newInstance();
        studyService.save(fcCol);
        result.setResultCode(HttpCode.BAD_REQUEST);
        return ResponseEntity.ok(result);
    }
}
