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
@Table(name = "homework")
public class Homework {

    @Id
    @Column(name = "homework_id")
    private  String homework_id;

    @Column(name = "class_id")
    private String class_id;

    @Column(name = "homework_rm")
    private String homework_rm;

    @Column(name = "homework_et")
    private Date homework_et;
}
