package com.api.backendapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tbl_tasks")
@Getter
@Setter
public class Task implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(name = "status")
    private String taskStatus;

    @Column(name = "description", columnDefinition = "NVARCHAR", length = 5000)
    private String description;

    @Column(name = "process_content", columnDefinition = "NVARCHAR", length = 5000)
    private String contentProcess;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "time_comment")
    private Date timeComment;

    @Column(name = "comment_content", columnDefinition = "NVARCHAR", length = 2000)
    private String commentContent;

    @Column(name = "rate")
    private float rate;

    @Column(name = "created")
    private Date created;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "creator_id", columnDefinition = "bigint")
    private User creatorId;

    @Column(name = "image", columnDefinition = "NVARCHAR(100)")
    private String image;

    @Column(name = "source_handler", columnDefinition = "NVARCHAR(100)")
    private String sourceHandler;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "handler_id", columnDefinition = "bigint")
    private User handlerId;

    @Column(name = "last_modified")
    private Date lastModified;

}
