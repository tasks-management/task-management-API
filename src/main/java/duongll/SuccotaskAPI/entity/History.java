package duongll.SuccotaskAPI.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_history")
@Getter
@Setter
public class History implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "update_content")
    private String updateContent;

    @ManyToOne(targetEntity = Task.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "task_status")
    private Enum<TaskStatus> taskStatus;

    private enum TaskStatus {FREE, DONE, LATE, INPROCESS}
}
