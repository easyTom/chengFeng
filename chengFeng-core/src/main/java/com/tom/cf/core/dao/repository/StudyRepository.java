package com.tom.cf.core.dao.repository;

import com.tom.cf.core.entity.Mistake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Mistake,String>,JpaSpecificationExecutor<Mistake> {
    @Modifying
    @Query(value = "update Mistake set important=:#{#p.important} where id = :#{#p.id}")
    void updateStatus(@Param("p") Mistake m);


    @Modifying
    @Query(value = "update Mistake m set m.updateAt=:#{#p.updateAt},m.content=:#{#p.content},m.count=:#{#p.count},m.type=:#{#p.type},m.name=:#{#p.name} where m.id = :#{#p.id}")
    void update(@Param("p") Mistake m);
   /* @Query("select userId from User where userName=?1 and status = '1'")
    String findIdByUserName(String userName);

    @Transactional
    @Modifying
    @Query("update User u set u.status=:#{#user.status},u.memo=:#{#user.memo} where u.userId=:#{#user.userId}")
    void updateUserByUserId(@Param("user") User user);

    User findByUserNameAndStatus(String userName, Integer status);

    User findByToken(String token);*/
}
