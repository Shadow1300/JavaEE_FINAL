package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.core.request.LoadRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.db.model.Student;
import com.example.demo.db.model.User;
import com.example.demo.db.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController()
@RequestMapping("/load")
public class LoadController {

    @Autowired
    private final LoadService loadService;

    public LoadController(LoadService loadService) {
        this.loadService = loadService;
    }

    @ResponseBody
    @RequestMapping("/a")
    public JSONObject publishBlink(HttpServletRequest request) {

        JSONObject object=new JSONObject();

        //List<Student> list=loadService.findAllByStudentNumber(request.getParameter("student_id"));
        //String back=loadService.load(request.getParameter("load_id"),request.getParameter("load_pw"));
        List<User> list=loadService.load(request.getParameter("load_id"),request.getParameter("load_pw"));

        if(list.size()==0){
            object.put("code",0);
            object.put("msg","no student");
            System.out.println("1");
            return object;
        }
        else {
            String pw=list.get(0).getLoadpassword();
            if(pw.equals(request.getParameter("load_pw")))
            {
                object.put("code",1);
                object.put("msg",list.get(0).getType());
                return object;
            }
            else {
                object.put("code",0);
                object.put("msg","wrong");
                return object;
            }
        }

    }
}
