package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.db.model.Student;
import com.example.demo.db.model.Teacher;
import com.example.demo.db.model.User;
import com.example.demo.db.service.RegisterService;
import com.example.demo.db.service.StudentService;
import com.example.demo.db.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/register")
public class registerController {
    @Autowired
    private final RegisterService registerService;
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final TeacherService teacherService;

    public registerController(RegisterService registerService, StudentService studentService, TeacherService teacherService) {
        this.registerService = registerService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }


    @ResponseBody
    @RequestMapping("/a")
    public JSONObject publishBlink(HttpServletRequest request) {
        User user=User.builder()
                .loadid(request.getParameter("register_id"))
                .loadpassword(request.getParameter("register_pw"))
                .type(request.getParameter("register_type"))
                .loadname(request.getParameter("register_name"))
                .build();

        JSONObject object=new JSONObject();

        try{
            registerService.getMapper().save(user);
            if(request.getParameter("register_type").equals("学生")){
                Student student=Student.builder()
                        .student_id(request.getParameter("register_id"))
                        .student_name(request.getParameter("register_name"))
                        .build();
                studentService.getMapper().save(student);
            }
            else {
                Teacher teacher=Teacher.builder()
                        .teacher_id(request.getParameter("register_id"))
                        .teacher_name(request.getParameter("register_name"))
                        .build();
                teacherService.getMapper().save(teacher);
            }
            object.put("code",1);
            object.put("msg","success");
            return object;
        }
        catch (Exception e){
            object.put("code",0);
            object.put("msg","false");

            return object;
        }

    }
}
