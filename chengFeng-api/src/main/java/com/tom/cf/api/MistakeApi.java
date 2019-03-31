package com.tom.cf.api;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.tom.cf.api.dto.DataTableRequest;
import com.tom.cf.api.dto.DataTableResponse;
import com.tom.cf.api.dto.HttpCode;
import com.tom.cf.api.dto.ResultDTO;
import com.tom.cf.core.dao.config.WebUtil;
import com.tom.cf.core.entity.Mistake;
import com.tom.cf.core.service.StudyService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@RequestMapping("/tom/api/study/mistake")
public class MistakeApi {

    // 状态
    private static final String STATE = "state";
    // 上传成功
    private static final String SUCCESS = "SUCCESS";
    // URL
    private static final String URL = "url";
    // 原URL
    private static final String SRC_URL = "srcUrl";
    // 文件原名
    private static final String ORIGINAL = "original";
    // 文件类型
    private static final String FILETYPE = "fileType";
    // 在线图片管理图片分隔符
    private static final String UE_SEPARATE_UE = "ue_separate_ue";
    // 提示信息
    private static final String TIP = "tip";

    @Autowired
    public StudyService studyService;


    @PostMapping("/add")
    public ResponseEntity save(Mistake m){
        studyService.save(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/update")
    public ResponseEntity update(Mistake m) {
        studyService.update(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }
    @RequestMapping(value = "/image")
    public void upload(@RequestParam(value = "Type", required = false) String typeStr, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        if (Utils.isEmpty(typeStr)) {
            typeStr = "File";
        }
        JSONObject json = new JSONObject();
        JSONObject ob = validateUpload(request, typeStr);
            json = ob;
        ResponseUtils.renderJson(response, json.toString());
    }

    /**
     * 验证是否能够上传
     *
     * @param request
     * @param typeStr
     * @return
     * @throws JSONException
     */
    private JSONObject validateUpload(HttpServletRequest request, String typeStr) throws JSONException {
        JSONObject result = new JSONObject();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile uplFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();
        String filename = FilenameUtils.getName(uplFile.getOriginalFilename());
        int fileSize = (int) (uplFile.getSize() / 1024);//单位KB
        String ext = FilenameUtils.getExtension(filename).toLowerCase(Locale.ENGLISH);

        // 此处可根据系统业务定义的配置项再次进行验证

            return result;
    }


    @PostMapping(value = "updateImportant")
    public ResponseEntity updateImportant(Mistake m) {
        studyService.updateImportant(m);
        ResultDTO result = ResultDTO.newInstance();
        result.setResultCode(HttpCode.OK);
        return ResponseEntity.ok(result);
    }

   /* @GetMapping("/{id}")
    public ResultDTO getMistakesById(@PathVariable("id") String id) {
        ResultDTO result = ResultDTO.newInstance();
        if (StringUtils.isNotBlank(id)) {
            Optional<Mistake> notice = studyService.getById(id);
            result.setData(notice.get());
        }
        result.setResultCode(HttpCode.OK);
        return result;
    }*/

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