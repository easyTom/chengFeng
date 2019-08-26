package com.tom.cf.core.dao.repository;

import com.tom.cf.core.mobile.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity,String>,JpaSpecificationExecutor<AuthEntity> {
}
