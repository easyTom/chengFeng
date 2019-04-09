package com.tom.cf.core.service;

import com.tom.cf.core.dao.config.WebUtil;
import com.tom.cf.core.dao.repository.StudyExampleRepository;
import com.tom.cf.core.entity.Example;
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
public class StudyExampleService {

    @Autowired
    private StudyExampleRepository studyRepository;
    @Value("${tom.files.path}")
    private String path;

    public Page<Example> findPageByUserid(Specification<Example> specification, Pageable pageable) {
        return studyRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Example m) {
        m.setCreateAt(new Date());
        m.setUpdateAt(new Date());
        m.setUserId(WebUtil.getCurrentUser().getUserId());
        m.setUserName(WebUtil.getCurrentUser().getUserName());
        m.setCount(0);
        studyRepository.save(m);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Example m) {
        m.setUpdateAt(new Date());
        studyRepository.update(m);
    }

    public Optional<Example> getById(String id) {
        return studyRepository.findById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void del(Example m) {
        Optional<Example> mm = studyRepository.findById(m.getId());
        //删除文件
        studyRepository.delete(m);
    }

    public Optional<Example> findByUserId(String id) {
        return studyRepository.findById(id);
    }
}
