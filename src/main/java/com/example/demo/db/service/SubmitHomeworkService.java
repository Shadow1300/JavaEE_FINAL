package com.example.demo.db.service;

import com.example.demo.db.mapper.SubmitHomeworkMapper;
import com.example.demo.db.model.Student;
import com.example.demo.db.model.SubmitHomework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmitHomeworkService extends BaseService<SubmitHomework, String, SubmitHomeworkMapper> {
    @Autowired
    private final SubmitHomeworkMapper studentMapper;

    public SubmitHomeworkService(SubmitHomeworkMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public List<SubmitHomework> findAllByclassid(String class_id) {
        SubmitHomework submitHomework = SubmitHomework.builder().class_id(class_id).build();
        Example<SubmitHomework> example = Example.of(submitHomework);
        List<SubmitHomework> list = studentMapper.findAll(example);
        return list;
    }

}
