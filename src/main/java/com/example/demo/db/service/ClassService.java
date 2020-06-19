package com.example.demo.db.service;

import com.example.demo.db.mapper.Class1Mapper;
import com.example.demo.db.model.Class1;
import com.example.demo.db.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService extends BaseService<Class1, String, Class1Mapper> {

    @Autowired
    private final Class1Mapper class1Mapper;

    public ClassService(Class1Mapper class1Mapper) {
        this.class1Mapper = class1Mapper;
    }


    public List<Class1> findAllByTeacherid(String teacher_id) {
        Class1 class1=Class1.builder().teacher_id(teacher_id).build();
        Example<Class1> example = Example.of(class1);
        List<Class1> list = class1Mapper.findAll(example);
        return list;
    }

    public List<Class1> findAllByClassid(String class_id) {
        Class1 class1=Class1.builder().class_id(class_id).build();
        Example<Class1> example = Example.of(class1);
        List<Class1> list = class1Mapper.findAll(example);
        return list;
    }
}
