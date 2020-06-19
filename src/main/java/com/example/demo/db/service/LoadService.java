package com.example.demo.db.service;

import com.example.demo.db.mapper.LoadMapper;
import com.example.demo.db.mapper.StudentMapper;
import com.example.demo.db.model.Student;
import com.example.demo.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadService extends BaseService<User, String, LoadMapper> {


    @Autowired
    private final LoadMapper loadMapper;

    public LoadService(LoadMapper loadMapper) {
        this.loadMapper = loadMapper;
    }

    public List<User> load(String id, String password) {
        User user=User.builder().loadid(id).build();
        Example<User> example = Example.of(user);
        List<User> list = loadMapper.findAll(example);
        return list;
    }

}
