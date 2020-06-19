package com.example.demo.db.service;

import com.example.demo.db.mapper.LoadMapper;
import com.example.demo.db.mapper.StudentMapper;
import com.example.demo.db.model.Student;
import com.example.demo.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService extends BaseService<User, String, LoadMapper> {


}
