package com.tom.cf.core.dao.repository;

import com.tom.cf.core.entity.FcCol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyColRepository extends JpaRepository<FcCol,String>,JpaSpecificationExecutor<FcCol> {

}
