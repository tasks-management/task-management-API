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

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String taskStatus;

    @Column(name = "description")
    private String description;

    @Column(name = "process_content")
    private String contentProcess;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "time_comment")
    private Date timeComment;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "rate")
    private float rate;

    @Temporal(TemporalType.DATE)
    @Column(name = "created")
    private Date created;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "creator_id", columnDefinition = "bigint")
    private User creatorId;

    @Column(name = "image")
    private String image;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "handler_id", columnDefinition = "bigint")
    private User handlerId;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_modified")
    private Date lastModified;

}
