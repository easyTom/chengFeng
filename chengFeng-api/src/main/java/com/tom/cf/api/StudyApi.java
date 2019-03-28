package com.tom.cf.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tom/api/study")
public class StudyApi {

    //@Autowired
    //public StudyService studyService;


    /*@RequestMapping("/list")
    public DataTableResponse list(DataTableRequest dr) {
        Page<User> page = studyService.findAll(dr.currentPage(new Sort(Sort.Direction.DESC, "createdAt")));
        return dr.createDataTableResponse(page);
    }

    @PostMapping("/get/{id}")
    public ResultDTO detail(@PathVariable String id) {
        ResultDTO res = ResultDTO.newInstance();
        User user = studyService.findUserByUserId(id);
        res.setData(user);
        return res;
    }

    @PostMapping("/insert")
    public ResultDTO add(AuthorityDTO authorityDTO) {
        ResultDTO res = ResultDTO.newInstance();
        studyService.insertOrupdateUser(authorityDTO.valueOf(authorityDTO));
        userRightService.DeleteByUserId(authorityDTO.getUserId());
        for (String authority : authorityDTO.getAuthority()) {
            UseRright useRright = new UseRright();
            useRright.setUserId(authorityDTO.getUserId());
            useRright.setActionAlias(authority);
            userRightService.insertOrUpdate(useRright);
        }
        return res;

    }*/
}