package com.tom.cf.core.service;

import com.tom.cf.core.dao.config.WebUtil;
import com.tom.cf.core.dao.repository.StudyRepository;
import com.tom.cf.core.entity.Mistake;
import com.tom.cf.core.utils.UeditorDelImgUtil;
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
public class StudyService {

    @Autowired
    private StudyRepository studyRepository;
    @Value("${tom.files.path}")
    private String path;

    public Page<Mistake> findPageByUserid(Specification<Mistake> specification, Pageable pageable) {
        return studyRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Mistake m) {
        m.setCreateAt(new Date());
        m.setUpdateAt(new Date());
        m.setUserId(WebUtil.getCurrentUser().getUserId());
        m.setUserName(WebUtil.getCurrentUser().getUserName());
        m.setImportant(0);
        m.setCount(0);
        studyRepository.save(m);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateImportant(Mistake m) {
        studyRepository.updateStatus(m);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Mistake m) {
        m.setUpdateAt(new Date());
        m.setCount(m.getCount()+1);
        studyRepository.update(m);
    }

    public Optional<Mistake> getById(String id) {
        return studyRepository.findById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void del(Mistake m) {
        Optional<Mistake> mm = studyRepository.findById(m.getId());
        UeditorDelImgUtil.deleteImages(mm.get().getContent(),path);
        studyRepository.delete(m);
    }
}
