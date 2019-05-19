package com.tom.cf.core.service;

import com.tom.cf.core.dao.config.WebUtil;
import com.tom.cf.core.dao.mapper.StudyMapper;
import com.tom.cf.core.dao.repository.StudyRepository;
import com.tom.cf.core.entity.FcCol;
import com.tom.cf.core.entity.FcMistake;
import com.tom.cf.core.entity.ResultTwo;
import com.tom.cf.core.utils.UeditorDelImgUtil;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private StudyMapper sm;
    @Value("${tom.files.path}")
    private String path;

    public Page<FcMistake> findPageByUserid(Specification<FcMistake> specification, Pageable pageable) {
        return studyRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(FcMistake m) {
        m.setCreateAt(new Date());
        m.setUpdateAt(new Date());
        m.setUserId(WebUtil.getCurrentUser().getUserId());
        m.setUserName(WebUtil.getCurrentUser().getUserName());
        m.setImportant(0);
        m.setCount(0);
        studyRepository.save(m);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateImportant(FcMistake m) {
        studyRepository.updateStatus(m);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(FcMistake m) {
        m.setUpdateAt(new Date());
        m.setCount(m.getCount()+1);
        studyRepository.update(m);
    }

    public Optional<FcMistake> getById(String id) {
        return studyRepository.findById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void del(FcMistake m) {
        Optional<FcMistake> mm = studyRepository.findById(m.getId());
        if(mm.get().getContent()!=null){
            UeditorDelImgUtil.deleteImages(mm.get().getContent(),path);
        }
        studyRepository.delete(m);
    }

    public ResultTwo getCount(String s) {
        int indexCount = sm.getIndexCount(s);
        FcCol fc = sm.getIdByName(s);
        String id = "";
        if(fc!=null){
            id = fc.getId();
        }
        return new ResultTwo(id,indexCount);
    }
}
