package com.example.demo.db.service;

import com.example.demo.db.mapper.StudentMapper;
import com.example.demo.db.mapper.TeacherMapper;
import com.example.demo.db.model.Student;
import com.example.demo.db.model.Teacher;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends BaseService<Teacher, String, TeacherMapper> {

}
