package com.tom.cf.core.dao.repository;

import com.tom.cf.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

    @Query("select userId from User where userName=?1 and status = '1'")
    String findIdByUserName(String userName);

    @Transactional
    @Modifying
    @Query("update User u set u.status=:#{#user.status},u.memo=:#{#user.memo} where u.userId=:#{#user.userId}")
    void updateUserByUserId(@Param("user") User user);

    User findByUserNameAndStatus(String userName, Integer status);

    User findByToken(String token);

}
