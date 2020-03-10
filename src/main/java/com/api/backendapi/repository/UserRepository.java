package com.api.backendapi.repository;

import com.api.backendapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.* FROM tbl_users u WHERE u.username = :username AND u.password = :password AND u.is_active = 'TRUE'", nativeQuery = true)
    User checkLogin(@Param("username") String username, @Param("password") String password);
}
