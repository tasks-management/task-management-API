package com.api.backendapi.repository;

import com.api.backendapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM tbl_tasks t WHERE t.handler_id = :id AND t.status = 'IN PROGRESS'", nativeQuery = true)
    List<Task> getInProgressTaskByUserId(@Param("id") Long id);

    @Query(value = "SELECT t.* FROM tbl_tasks t WHERE t.handler_id = :id AND (t.status = 'SUCCEED' OR t.status = 'FAIL')", nativeQuery = true)
    List<Task> getHistoryTask(@Param("id") Long id);

    @Query(value = "SELECT t.* FROM tbl_tasks t WHERE t.handler_id = :id" +
            " AND t.start_date >= :start AND t.end_date <= :end" +
            " AND (t.status = 'SUBMITTED' OR t.status = 'FAIL' OR t.status = 'SUCCEED')", nativeQuery = true)
    List<Task> getHistoryTaskByDate(@Param("id") Long id,
                                    @Param("start") Date startDate,
                                    @Param("end") Date endDate);

    @Query(value = "SELECT t.* FROM tbl_tasks t WHERE t.handler_id = :id" +
            " AND t.status = :status" , nativeQuery = true)
    List<Task> getHistoryByStatus(@Param("id") Long id,
                                  @Param("status") String status);

    @Query(value = "SELECT t* FROM tbl_tasks t WHERE t.handler_id = :id" +
            " AND t.status = :status" +
            " AND t.start_date >= :start AND t.end_date <= :end ", nativeQuery = true)
    List<Task> getHistoryByUserId(@Param("id") Long id,
                                  @Param("status") String status,
                                  @Param("start") Date startDate,
                                  @Param("end") Date endDate);

    @Query(value = "SELECT t.* FROM tbl_tasks t WHERE t.status = 'SUBMITTED' AND t.creator_id = :userId", nativeQuery = true)
    List<Task> getAllSubmitedTaskForManager(@Param("userId") Long userId);

    @Query(value = "SELECT *" +
            "FROM tbl_tasks t " +
            "WHERE t.status = 'PENDING' AND t.handler_id IN (SELECT u.id " +
            "FROM tbl_users u " +
            "WHERE u.role = 'user' AND u.team_id = (SELECT u.team_id " +
            "FROM tbl_users u " +
            "WHERE u.id = :id))", nativeQuery = true)
    List<Task> getAllPendingTaskForManager(@Param("id") Long userId);
}
