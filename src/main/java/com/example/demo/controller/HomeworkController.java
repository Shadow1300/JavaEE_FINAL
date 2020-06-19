package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.db.model.Class1;
import com.example.demo.db.model.Course;
import com.example.demo.db.model.Homework;
import com.example.demo.db.model.SubmitHomework;
import com.example.demo.db.service.ClassService;
import com.example.demo.db.service.CourseService;
import com.example.demo.db.service.HomeworkService;
import com.example.demo.db.service.SubmitHomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController()
@RequestMapping("/homework")
public class HomeworkController {
    @Autowired
    private final ClassService classService;

    @Autowired
    private final HomeworkService homeworkService;

    @Autowired
    private final CourseService courseService;

    @Autowired
    private final SubmitHomeworkService submitHomeworkService;

    public HomeworkController(ClassService classService, HomeworkService homeworkService, CourseService courseService, SubmitHomeworkService submitHomeworkService) {
        this.classService = classService;

        this.homeworkService = homeworkService;
        this.courseService = courseService;
        this.submitHomeworkService = submitHomeworkService;
    }


    @ResponseBody
    @RequestMapping("/publish")
    public JSONObject publishHomework(HttpServletRequest request) {
        JSONObject object = new JSONObject();
        String homework_id=request.getParameter("homework_id");
        String class_id=request.getParameter("class_id");
        String homework_requirement=request.getParameter("homework_requirement");
        String load_id=request.getParameter("load_id");

       // System.out.println(request.getParameter("homework_endtime"));

        //将String转为date形式
        String nowtime=request.getParameter("homework_endtime")+":00";
        nowtime=nowtime.substring(0,10)+" "+nowtime.substring(11);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d2 = null;
        try {
            d2 = sdf2.parse(nowtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println("2222");

        List<Class1> list=classService.findAllByClassid(class_id);
        //System.out.println("2222:"+list.size());
        if(list.size()==0||!(list.get(0).getTeacher_id().equals(load_id))){
            System.out.println("2");
            object.put("code",0);
            object.put("msg","你没有这个课程");
            return object;
        }
        else {
            System.out.println("5");
            Homework homework=Homework.builder()
                    .homework_id(homework_id)
                    .class_id(class_id)
                    .homework_rm(homework_requirement)
                    .homework_et(d2)
                    .build();

            try{
                homeworkService.getMapper().save(homework);
                object.put("code",1);
                object.put("msg","yes");
                System.out.println("8");
                return object;
            }
            catch (Exception e){
                object.put("code",0);
                object.put("msg","false");
                //System.out.println("9");
                return object;
            }

        }
    }

    @ResponseBody
    @RequestMapping("/look")
    public JSONObject lookHomework(HttpServletRequest request) {
        JSONObject object = new JSONObject();
        String load_id=request.getParameter("load_id");
        List<Course> list=courseService.findAllByStudentid(load_id);
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();

        if(list.size()==0){
            object.put("code",0);
            object.put("msg","无课程");
            return object;
        }
        else {
            for(int i=0;i<list.size();i++){
                String class_id=list.get(i).getClass_id();
                String class_name=classService.findAllByClassid(class_id).get(0).getClass_name();
                List<Homework> list1=homeworkService.findAllByClassid(class_id);
                int num=0;
                if(list1.size()==0){

                }
                else {
                    for(int j=0;j<list1.size();j++){
                        String homework_id=list1.get(j).getHomework_id();
                        String homework_rm=list1.get(j).getHomework_rm();
                        Date homework_et=list1.get(j).getHomework_et();
                        jsonlist.add(new JSONObject());
                        jsonlist.get(num).put("class_id",class_id);
                        jsonlist.get(num).put("class_name",class_name);
                        jsonlist.get(num).put("homework_id",homework_id);
                        jsonlist.get(num).put("homework_rm",homework_rm);
                        jsonlist.get(num).put("homework_et",homework_et);
                        num++;
                        System.out.println("1");
                    }
                }

                jsonlist.add(new JSONObject());
                jsonlist.get(i).put("class_id",list.get(i).getClass_id());
            }
            object.put("code",1);
            object.put("msg","ok");
            object.put("neirong",jsonlist);
            return object;
        }

    }

    @ResponseBody
    @RequestMapping("/submit")
    public JSONObject submitHomework(HttpServletRequest request) {
        JSONObject object = new JSONObject();
        String student_id=request.getParameter("student_id");
        String homework_id=request.getParameter("homework_id");
        String class_id=request.getParameter("class_id");
        String homework_title=request.getParameter("homework_title");
        String homework_content=request.getParameter("homework_content");

        System.out.println("1:"+student_id);
        List<Course> list=courseService.findAllByStudentid(student_id);
        System.out.println("1:"+list.size());
        boolean check=false;
        if(list.size()==0){
            object.put("code",0);
            object.put("msg","no");
            return object;
        }

        else {
            for(int i=0;i<list.size();i++){
                if(list.get(i).getClass_id().equals(class_id)){
                    check=true;
                }
            }
            if(check){
                //获取当前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String nowtime=df.format(new Date());
                //给定模式(这里给定的模式须与给定日期字符串格式匹配)，将String转为Date
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d2 = null;
                try {
                    d2 = sdf2.parse(nowtime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SubmitHomework submitHomework=SubmitHomework.builder()
                        .homework_id(homework_id)
                        .homework_content(homework_content)
                        .student_id(student_id)
                        .homework_title(homework_title)
                        .class_id(class_id)
                        .submit_time(d2)
                        .build();
                try{
                    submitHomeworkService.getMapper().save(submitHomework);
                    object.put("code",1);
                    object.put("msg","ok");
                    return object;
                }catch (Exception e){
                    object.put("code",0);
                    object.put("msg","失败");
                    return object;
                }

            }
            else {
                object.put("code",0);
                object.put("msg","no");
                return object;
            }
        }

    }

    @ResponseBody
    @RequestMapping("/look1")
    public JSONObject look1Homework(HttpServletRequest request) {
        JSONObject object = new JSONObject();
        String load_id=request.getParameter("load_id");
        System.out.println("1:"+load_id);
        List<Class1> list=classService.findAllByTeacherid(load_id);
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        int num=0;
        System.out.println("2:"+list.size());
        if(list.size()==0){
            object.put("code",0);
            object.put("msg","无课程");
            return object;
        }
        else {
            System.out.println("3:");
            for(int i=0;i<list.size();i++) {
                String class_id = list.get(i).getClass_id();
                List<SubmitHomework> list1 = submitHomeworkService.findAllByclassid(class_id);
                System.out.println("41:"+list1.size());
                    for (int j = 0; j < list1.size(); j++) {
                        jsonlist.add(new JSONObject());
                        String homework_id = list1.get(j).getHomework_id();
                        String student_id = list1.get(j).getStudent_id();
                        String class_id1 = list1.get(j).getClass_id();
                        String homework_title = list1.get(j).getHomework_title();
                        String homework_content = list1.get(j).getHomework_content();
                        String teacher_comment = list1.get(j).getTeacher_comment();
                        Date submit_time = list1.get(j).getSubmit_time();
                        String grade = list1.get(j).getGrade();

                        jsonlist.get(num).put("homework_id", homework_id);
                        jsonlist.get(num).put("student_id", student_id);
                        jsonlist.get(num).put("class_id",class_id1);
                        jsonlist.get(num).put("homework_title", homework_title);
                        jsonlist.get(num).put("homework_content", homework_content);
                        jsonlist.get(num).put("teacher_comment", teacher_comment);
                        jsonlist.get(num).put("submit_time", submit_time);
                        jsonlist.get(num).put("grade", grade);
                        num++;
                        System.out.println("aa"+num);

                }
            }
            if(num==0){

                    object.put("code", 0);
                    object.put("msg", "无作业提交情况");
                    return object;

            }
            else {
                object.put("code", 1);
                object.put("msg", "ok");
                object.put("neirong", jsonlist);
                return object;
            }



        }

    }

    @ResponseBody
    @RequestMapping("/check")
    public JSONObject checkHomework(HttpServletRequest request) {
        JSONObject object = new JSONObject();

        String student_id=request.getParameter("student_id");
        String homework_id=request.getParameter("homework_id");
        String class_id=request.getParameter("class_id");
        String teacher_comment=request.getParameter("teacher_comment");
        String grade=request.getParameter("grade");
        String teacher_id=request.getParameter("teacher_id");

        System.out.println("1::"+homework_id);
        System.out.println("2::"+student_id);

        SubmitHomework submitHomework1=new SubmitHomework();

        boolean check=false;
        List<Class1> list=classService.findAllByClassid(class_id);
        if(list.size()==0||!list.get(0).getTeacher_id().equals(teacher_id)){
            System.out.println("1");
            object.put("code",0);
            object.put("msg","无此课");
            return object;
        }
        else {
            System.out.println("2");
            List<SubmitHomework> list1=submitHomeworkService.findAllByclassid(class_id);
            if(list1.size()==0){
                System.out.println("3");
                object.put("code",0);
                object.put("msg","无此作业");
                return object;
            }
            else {
                System.out.println("4");
                for(int i=0;i<list1.size();i++){
                    System.out.println(list1.get(i).getHomework_id());
                    System.out.println(list1.get(i).getStudent_id());
                    if(list1.get(i).getHomework_id().equals(homework_id)){
                        if(list1.get(i).getStudent_id().equals(student_id)){
                            check=true;
                            submitHomework1=list1.get(i);
                        }
                    }
                }

                if(!check){
                    System.out.println("5");
                    object.put("code",0);
                    object.put("msg","未交此作业");
                    return object;
                }
                else {
                    System.out.println("6");
                    submitHomework1.setGrade(grade);
                    submitHomework1.setTeacher_comment(teacher_comment);
                    submitHomeworkService.getMapper().save(submitHomework1);
                    object.put("code",1);
                    object.put("msg","ok");
                    return object;
                }
            }
        }

    }

    @ResponseBody
    @RequestMapping("/mypublish")
    public JSONObject mypublishHomework(HttpServletRequest request) {
        JSONObject object = new JSONObject();
        int num=0;
        String load_id=request.getParameter("load_id");
        List<Class1> list=classService.findAllByTeacherid(load_id);
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        if(list.size()==0){
            object.put("code",0);
            object.put("msg","无课程");
            return object;
        }
        else {
            for(int i=0;i<list.size();i++){
                String class_id=list.get(i).getClass_id();
                String class_name=list.get(i).getClass_name();
                List<Homework> list1=homeworkService.findAllByClassid(class_id);
                if(list1.size()==0){

                }
                else {
                    for(int j=0;j<list1.size();j++){
                        String homework_id=list1.get(j).getHomework_id();
                        String homework_rm=list1.get(j).getHomework_rm();
                        Date homework_et=list1.get(j).getHomework_et();

                        jsonlist.add(new JSONObject());
                        jsonlist.get(num).put("class_id",class_id);
                        jsonlist.get(num).put("class_name",class_name);
                        jsonlist.get(num).put("homework_id",homework_id);
                        jsonlist.get(num).put("homework_rm",homework_rm);
                        jsonlist.get(num).put("homework_et",homework_et);
                        num++;
                    }
                }
            }
            object.put("code",1);
            object.put("msg","ok");
            object.put("neirong",jsonlist);
            return object;
        }

    }
}

