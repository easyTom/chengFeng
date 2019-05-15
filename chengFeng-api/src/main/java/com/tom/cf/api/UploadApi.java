package com.tom.cf.api;

import com.tom.cf.api.dto.DataTableRequest;
import com.tom.cf.api.dto.DataTableResponse;
import com.tom.cf.api.dto.HttpCode;
import com.tom.cf.api.dto.ResultDTO;
import com.tom.cf.core.entity.FcUpload;
import com.tom.cf.core.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/tom/api/study/upload")
public class UploadApi {

    @Autowired
    public UploadService uploadService;


    @PostMapping(value = "del")
    public ResponseEntity del(FcUpload m) {
        uploadService.del(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity save(FcUpload m){
        uploadService.save(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResultDTO getCodesById(@PathVariable("id") String id) {
        ResultDTO result = ResultDTO.newInstance();
        if (StringUtils.isNotBlank(id)) {
            Optional<FcUpload> notice = uploadService.getById(id);
            result.setData(notice.get());
        }
        result.setResultCode(HttpCode.OK);
        return result;
    }

    @ResponseBody
    @RequestMapping("/ecgImages")
    public Optional<FcUpload> ecgImages(String id){
        return uploadService.getById(id);
    }

    @RequestMapping("/list")
    public DataTableResponse list(DataTableRequest dr) {
        Map<String, Object> conditions = dr.getConditions();
        Page<FcUpload> page = uploadService.findPageByUserid(new Specification<FcUpload>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                String type = (String) conditions.get("type");
                if (StringUtils.isNotEmpty(type)) {
                    Predicate predicate = criteriaBuilder.like(root.get("type").as(String.class), "%" + type + "%");
                    list.add(predicate);
                }
                Predicate[] ps = new Predicate[list.size()];
                criteriaQuery.where(criteriaBuilder.and(list.toArray(ps)));
                return criteriaQuery.getRestriction();
            }
        },new PageRequest(dr.getPage(), dr.getIDisplayLength()));
        return dr.createDataTableResponse(page);
    }
}