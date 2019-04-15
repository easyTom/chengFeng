package com.tom.cf.core.service;

import com.tom.cf.core.dao.repository.StudyColRepository;
import com.tom.cf.core.entity.FcCol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudyColService {

    @Autowired
    private StudyColRepository studyRepository;


    @Transactional(rollbackFor = Exception.class)
    public void save(FcCol m) {
        studyRepository.save(m);
    }

    @Transactional(rollbackFor = Exception.class)
    public void del(FcCol m) {
        studyRepository.delete(m);
    }

    public Page<FcCol> getAll(PageRequest pageRequest) {
        return studyRepository.findAll(pageRequest);
    }

    public Optional<FcCol> getById(String id) {
        return studyRepository.findById(id);
    }

    public List<FcCol> selectInit() {
        return studyRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }
}