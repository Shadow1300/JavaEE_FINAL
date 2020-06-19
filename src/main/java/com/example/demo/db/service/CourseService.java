package com.example.demo.db.service;

import com.example.demo.db.mapper.CourseMapper;
import com.example.demo.db.model.Course;
import com.example.demo.db.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends BaseService<Course, String, CourseMapper> {
    @Autowired
    private final CourseMapper courseMapper;

    public CourseService(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }


    public List<Course> findAllByStudentid(String student_id) {
        Course course=Course.builder().student_id(student_id).build();
        Example<Course> example = Example.of(course);
        List<Course> list = courseMapper.findAll(example);
        return list;
    }

}
