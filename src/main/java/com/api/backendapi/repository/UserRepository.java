package com.api.backendapi.repository;

import com.api.backendapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.* FROM tbl_users u WHERE u.username = :username AND u.password = :password AND u.is_active = 'TRUE'", nativeQuery = true)
    User checkLogin(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT u FROM User u WHERE u.role = 'manager'")
    List<User> getAllManagerUser();

    @Query(value = "SELECT u.* FROM tbl_users u WHERE u.role = 'user' AND u.team_id = " +
            "(SELECT u2.team_id FROM tbl_users u2 WHERE u2.id = :userId AND u2.role = 'manager')", nativeQuery = true)
    List<User> getAllUserInTeam(@Param("userId") Long userId);

    @Query(value = "SELECT u.* FROM tbl_users u WHERE u.is_active = 'TRUE' AND u.role = 'admin'", nativeQuery = true)
    List<User> getAllAdminUsers();
}
