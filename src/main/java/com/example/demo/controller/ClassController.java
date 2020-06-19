package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.db.model.Class1;
import com.example.demo.db.model.Course;
import com.example.demo.db.service.ClassService;
import com.example.demo.db.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private final ClassService classService;

    @Autowired
    private final CourseService courseService;

    public ClassController(ClassService classService, CourseService courseService) {
        this.classService = classService;
        this.courseService = courseService;
    }

    @ResponseBody
    @RequestMapping("/create")
    public JSONObject createClass(HttpServletRequest request) {
        JSONObject object=new JSONObject();

        int a = (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数

        System.out.println("1:"+request.getParameter("class_id"));
        System.out.println("2:"+request.getParameter("class_name"));
        System.out.println("3:"+request.getParameter("teacher_id"));
        System.out.println("4:"+a);

        Class1 class1= Class1.builder()
                .class_id(request.getParameter("class_id"))
                .class_name(request.getParameter("class_name"))
                .teacher_id(request.getParameter("teacher_id"))
                .check_num(a)
                .build();

        try{
            classService.getMapper().save(class1);
            System.out.println("1111:"+a);
            object.put("code",1);
            object.put("msg",a);
            return object;
        }
        catch (Exception e){
            object.put("code",0);
            object.put("msg","false");

            return object;
        }

    }

    @ResponseBody
    @RequestMapping("/look")
    public JSONObject lookClass(HttpServletRequest request){
        JSONObject object=new JSONObject();
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
       // System.out.println("112:"+request.getParameter("teacher_id"));
        List<Class1> list=classService.findAllByTeacherid(request.getParameter("teacher_id"));
        System.out.println("111:"+list.size());
        if(list.size()==0){
            object.put("code",0);
            object.put("msg","no class");
            return object;
        }
        else {
            int num=0;
            for(int i=0;i<list.size();i++){
                jsonlist.add(new JSONObject());
                //jsonlist.get(i).put("id",list.get(i).getId());
                jsonlist.get(i).put("class_id",list.get(i).getClass_id());
                jsonlist.get(i).put("class_name",list.get(i).getClass_name());
                jsonlist.get(i).put("teacher_id",list.get(i).getTeacher_id());
                jsonlist.get(i).put("check_num",list.get(i).getCheck_num());
                num++;
            }

            String data=String.valueOf(jsonlist);
            System.out.println(data);
            object.put("code",1);
            object.put("msg",num);
            object.put("neirong",jsonlist);
            return object;
        }
    }

    @ResponseBody
    @RequestMapping("/join")
    public JSONObject joinClass(HttpServletRequest request) {
        JSONObject object=new JSONObject();

        String class_id=request.getParameter("class_id");
        String teacher_id=request.getParameter("teacher_id");
        String check_num=request.getParameter("check_num");
        String student_id=request.getParameter("student_id");

        List<Class1> list=classService.findAllByClassid(class_id);

        if(list.size()==0){
            object.put("code",0);
            object.put("msg","无此课程");
            return object;
        }
        else if((list.get(0).getTeacher_id()).equals(teacher_id)){
            System.out.println("2:"+list.get(0).getCheck_num());
            System.out.println("21:"+check_num);
            if((list.get(0).getCheck_num().toString()).equals(check_num)){
                System.out.println("213:"+check_num);
                Course course=Course.builder()
                        .class_id(class_id)
                        .student_id(student_id)
                        .build();
                try{
                    courseService.getMapper().save(course);
                    object.put("code",1);
                    object.put("msg","OK");
                    return object;
                }
                catch (Exception e){
                    object.put("code",0);
                    object.put("msg","false");
                    return object;
                }

            }
            else {
                System.out.println("2111:"+check_num);
                object.put("code",0);
                object.put("msg","验证码错误");
                return object;
            }
        }
        else {
            object.put("code",0);
            object.put("msg","id不匹配");
            return object;
        }


    }

    @ResponseBody
    @RequestMapping("/look1")
    public JSONObject look1Class(HttpServletRequest request){
        JSONObject object=new JSONObject();
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        // System.out.println("112:"+request.getParameter("teacher_id"));
        //List<Class1> list=classService.findAllByTeacherid(request.getParameter("teacher_id"));
        List<Course> list=courseService.findAllByStudentid(request.getParameter("teacher_id"));
        System.out.println("111:"+list.size());
        if(list.size()==0){
            object.put("code",0);
            object.put("msg","no class");
            return object;
        }
        else {
            int num=0;
            for(int i=0;i<list.size();i++){
                String class_id=list.get(i).getClass_id();
                String class_name=classService.findAllByClassid(class_id).get(0).getClass_name();
                String teacher_id=classService.findAllByClassid(class_id).get(0).getTeacher_id();
                jsonlist.add(new JSONObject());
                //jsonlist.get(i).put("id",list.get(i).getId());
                jsonlist.get(i).put("class_id",list.get(i).getClass_id());
                jsonlist.get(i).put("class_name",class_name);
                jsonlist.get(i).put("teacher_id",teacher_id);
                num++;
            }

            String data=String.valueOf(jsonlist);
            System.out.println(data);
            object.put("code",1);
            object.put("msg",num);
            object.put("neirong",jsonlist);
            return object;
        }
    }

}
