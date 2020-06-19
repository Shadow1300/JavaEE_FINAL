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
@Table(name = "user")
public class User {


    @Column(name = "id")
    private  Integer id;
    @Id
    @Column(name = "loadid")
    private String loadid;

    @Column(name = "loadpassword")
    private String loadpassword;

    @Column(name = "loadtype")
    private String type;

    @Column(name = "loadname")
    private String loadname;


}
