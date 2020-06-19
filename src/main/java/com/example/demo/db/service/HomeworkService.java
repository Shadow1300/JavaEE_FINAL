package com.example.demo.db.service;

import com.example.demo.db.mapper.HomeworkMapper;
import com.example.demo.db.model.Course;
import com.example.demo.db.model.Homework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService extends BaseService<Homework, String, HomeworkMapper> {
    @Autowired
    private final HomeworkMapper homeworkMapper;


    public HomeworkService(HomeworkMapper homeworkMapper) {
        this.homeworkMapper = homeworkMapper;
    }

    public List<Homework> findAllByClassid(String class_id) {
        Homework homework=Homework.builder().class_id(class_id).build();
        Example<Homework> example = Example.of(homework);
        List<Homework> list = homeworkMapper.findAll(example);
        return list;
    }
}
