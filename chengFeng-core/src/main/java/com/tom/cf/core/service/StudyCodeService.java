package com.tom.cf.core.service;

import com.tom.cf.core.dao.config.WebUtil;
import com.tom.cf.core.dao.repository.StudyCodeRepository;
import com.tom.cf.core.entity.FcCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class StudyCodeService {

    @Autowired
    private StudyCodeRepository studyRepository;
    @Value("${tom.files.path}")
    private String path;

    public Page<FcCode> findPageByUserid(Specification<FcCode> specification, Pageable pageable) {
        return studyRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(FcCode m) {
        m.setCreateAt(new Date());
        m.setUpdateAt(new Date());
        m.setUserId(WebUtil.getCurrentUser().getUserId());
        m.setUserName(WebUtil.getCurrentUser().getUserName());
        studyRepository.save(m);
    }


    public Optional<FcCode> getById(String id) {
        return studyRepository.findById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void del(FcCode m) {
        studyRepository.delete(m);
    }

    public long getCount(){
        return  studyRepository.count();
    }
}
