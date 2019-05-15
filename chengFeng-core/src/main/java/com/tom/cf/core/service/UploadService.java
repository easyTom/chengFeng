package com.tom.cf.core.service;

import com.tom.cf.core.dao.repository.UploadRepository;
import com.tom.cf.core.entity.FcUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UploadService {

    @Autowired
    private UploadRepository uploadRepository;
    @Value("${tom.files.path}")
    private String path;

    public Page<FcUpload> findPageByUserid(Specification<FcUpload> specification, Pageable pageable) {
        return uploadRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(FcUpload m) {
        uploadRepository.save(m);
    }


    public Optional<FcUpload> getById(String id) {
        return uploadRepository.findById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void del(FcUpload m) {
        Optional<FcUpload> mm = uploadRepository.findById(m.getId());
        uploadRepository.delete(m);
    }
}
