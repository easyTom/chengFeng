package com.tom.cf.core.dao.repository;

import com.tom.cf.core.entity.UseRright;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRightRepository extends JpaRepository<UseRright,String>,JpaSpecificationExecutor<UseRright> {
    @Query("select actionAlias from UseRright WHERE userId=?1")
    Set<String> findActionAliasByUserId(String userId);
}
