package com.tom.cf.api;

import com.tom.cf.api.dto.AuthorityDTO;
import com.tom.cf.api.dto.DataTableRequest;
import com.tom.cf.api.dto.DataTableResponse;
import com.tom.cf.api.dto.ResultDTO;
import com.tom.cf.core.entity.UseRright;
import com.tom.cf.core.entity.User;
import com.tom.cf.core.service.UserRightService;
import com.tom.cf.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tom/api/user")
public class UserApi {

    @Autowired
    public UserService nmUserService;

    @Autowired
    public UserRightService userRightService;

    @RequestMapping("/list")
    public DataTableResponse list(DataTableRequest dr) {
        Page<User> page = nmUserService.findAll(dr.currentPage(new Sort(Sort.Direction.DESC, "createdAt")));
        return dr.createDataTableResponse(page);
    }

    @PostMapping("/get/{id}")
    public ResultDTO detail(@PathVariable String id) {
        ResultDTO res = ResultDTO.newInstance();
        User user = nmUserService.findUserByUserId(id);
        res.setData(user);
        return res;
    }

    @PostMapping("/insert")
    public ResultDTO add(AuthorityDTO authorityDTO) {
        ResultDTO res = ResultDTO.newInstance();
        nmUserService.insertOrupdateUser(authorityDTO.valueOf(authorityDTO));
        userRightService.DeleteByUserId(authorityDTO.getUserId());
        for (String authority : authorityDTO.getAuthority()) {
            UseRright useRright = new UseRright();
            useRright.setUserId(authorityDTO.getUserId());
            useRright.setActionAlias(authority);
            userRightService.insertOrUpdate(useRright);
        }
        return res;

    }
}