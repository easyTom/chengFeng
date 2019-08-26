package com.tom.cf.api.study;

import com.tom.cf.api.dto.*;
import com.tom.cf.core.dao.config.WebUtil;
import com.tom.cf.core.entity.FcExample;
import com.tom.cf.core.service.StudyExampleService;
import com.tom.cf.core.utils.FcFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/tom/api/study/example")
public class ExampleFileController {

    @Autowired
    public StudyExampleService studyService;
    @Value("${tom.files.path}")
    private String dirPath;

    @PostMapping(value = "del")
    public ResponseEntity del(FcExample m) {
        studyService.del(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity save(ExampleDTO exampleDTO){
        ResultDTO result = ResultDTO.newInstance();
        try {
            exampleDTO.storageFile(dirPath);
            studyService.save(exampleDTO.convert());
        } catch (IOException e) {
            e.printStackTrace();
            result.setResultCode(HttpCode.BAD_REQUEST);
        }


        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/update")
    public ResponseEntity update(FcExample m) {
        //修改文件 带实现
        studyService.update(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResultDTO getExamplesById(@PathVariable("id") String id) {
        ResultDTO result = ResultDTO.newInstance();
        if (StringUtils.isNotBlank(id)) {
            Optional<FcExample> notice = studyService.getById(id);
            result.setData(notice.get());
        }
        result.setResultCode(HttpCode.OK);
        return result;
    }

    @GetMapping("/download")
    public void download(String id, HttpServletResponse response){
        FcExample e =  studyService.findByUserId(id).get();
        File file = ExampleDTO.getFile(dirPath,e.getUserId(),e.getFileName());
        if(!file.exists()){
            return;
        }
        try {
            FcFileUtil.download(file,response);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @RequestMapping("/list")
    public DataTableResponse list(DataTableRequest dr) {
        Map<String, Object> conditions = dr.getConditions();
        Sort sort = new Sort(Sort.Direction.DESC, "count").and(new Sort(Sort.Direction.DESC, "updateAt"));//排序规则   多条件
        Page<FcExample> page = studyService.findPageByUserid(new Specification<FcExample>() {
            //root 里的是类里的属性
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                String name = (String) conditions.get("name");
                if (StringUtils.isNotEmpty(name)) {
                    Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%");
                    list.add(predicate);
                }
                String sdate = (String) conditions.get("sdate");
                String edate = (String) conditions.get("edate");
                if (StringUtils.isNotEmpty(sdate) && StringUtils.isNotEmpty(edate)) {
                    Predicate predicate = criteriaBuilder.between(root.get("updateAt").as(String.class), sdate, edate);
                    list.add(predicate);
                }
                Predicate predicate = criteriaBuilder.equal(root.get("userId").as(String.class), WebUtil.getCurrentUser().getUserId());
                list.add(predicate);
                Predicate[] ps = new Predicate[list.size()];
                criteriaQuery.where(criteriaBuilder.and(list.toArray(ps)));
                return criteriaQuery.getRestriction();
            }
        },new PageRequest(dr.getPage(), dr.getIDisplayLength(), sort));
        return dr.createDataTableResponse(page);
    }
}