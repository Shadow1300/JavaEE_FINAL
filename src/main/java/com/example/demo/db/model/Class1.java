package com.example.demo.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "class1")
public class Class1 {
   /* @Column(name = "id")
    private String id;*/
    @Id
    @Column(name = "class_id")
    private  String class_id;

    @Column(name = "class_name")
    private String class_name;

    @Column(name = "teacher_id")
    private String teacher_id;

    @Column(name = "check_num")
    private Integer check_num;



}
