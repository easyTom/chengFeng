package com.tom.cf.core.dao.repository;

import com.tom.cf.core.entity.FcUserRright;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRightRepository extends JpaRepository<FcUserRright,String>,JpaSpecificationExecutor<FcUserRright> {
    @Query("select actionAlias from FcUserRright WHERE userId=?1")
    Set<String> findActionAliasByUserId(String userId);
}
