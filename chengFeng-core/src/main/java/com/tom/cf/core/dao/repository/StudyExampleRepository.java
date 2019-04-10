package com.tom.cf.core.dao.repository;

import com.tom.cf.core.entity.FcExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyExampleRepository extends JpaRepository<FcExample,String>,JpaSpecificationExecutor<FcExample> {

    @Modifying
    @Query(value = "update FcExample m set m.updateAt=:#{#p.updateAt},m.name=:#{#p.name},m.content=:#{#p.content} where m.id = :#{#p.id}")
    void update(@Param("p") FcExample m);
}
