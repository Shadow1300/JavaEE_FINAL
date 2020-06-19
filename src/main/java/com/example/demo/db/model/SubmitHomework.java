package com.example.demo.db.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "submithomework")
public class SubmitHomework {
    @Id
    @Column(name = "student_id")
    private  String student_id;

    @Column(name = "homework_id")
    private String homework_id;

    @Column(name = "homework_content")
    private String homework_content;

    @Column(name = "class_id")
    private String class_id;

    @Column(name = "homework_title")
    private String homework_title;

    @Column(name = "submit_time")
    private Date submit_time;

    @Column(name = "teacher_comment")
    private String teacher_comment;

    @Column(name = "grade")
    private String grade;

}
