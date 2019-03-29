package com.tom.cf.api;

import com.tom.cf.api.dto.DataTableRequest;
import com.tom.cf.api.dto.DataTableResponse;
import com.tom.cf.api.dto.HttpCode;
import com.tom.cf.api.dto.ResultDTO;
import com.tom.cf.core.dao.config.WebUtil;
import com.tom.cf.core.entity.Mistake;
import com.tom.cf.core.service.StudyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/tom/api/study/mistake")
public class MistakeApi {

    @Autowired
    public StudyService studyService;


    @PostMapping("/add")
    public ResponseEntity save(Mistake m){
        studyService.save(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "update")
    public ResponseEntity update(Mistake m) {
        studyService.update(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "updateImportant")
    public ResponseEntity updateImportant(Mistake m) {
        studyService.updateImportant(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResultDTO getMistakesById(@PathVariable("id") String id) {
        ResultDTO result = ResultDTO.newInstance();
        if (StringUtils.isNotBlank(id)) {
            Optional<Mistake> notice = studyService.getById(id);
            result.setData(notice.get());
        }
        result.setResultCode(HttpCode.OK);
        return result;
    }

    @RequestMapping("/list")
    public DataTableResponse list(DataTableRequest dr) {
        Map<String, Object> conditions = dr.getConditions();
        Sort sort = new Sort(Sort.Direction.ASC, "important").and(new Sort(Sort.Direction.DESC, "updateAt"));//排序规则   多条件
        Page<Mistake> page = studyService.findPageByUserid(new Specification<Mistake>() {
            //root 里的是类里的属性
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                String type = (String) conditions.get("type");
                if (StringUtils.isNotEmpty(type)) {
                    Predicate predicate = criteriaBuilder.like(root.get("type").as(String.class), "%" + type + "%");
                    list.add(predicate);
                }
                String content = (String) conditions.get("content");
                if (StringUtils.isNotEmpty(content)) {
                    Predicate predicate = criteriaBuilder.like(root.get("content").as(String.class), "%" + content + "%");
                    list.add(predicate);
                }
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