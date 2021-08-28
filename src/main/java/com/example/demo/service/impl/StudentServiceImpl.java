package com.example.demo.service.impl;


import com.example.demo.bean.Course;
import com.example.demo.bean.Student;
import com.example.demo.mapper.CourseDao;
import com.example.demo.mapper.StudentDao;
import com.example.demo.service.CourseService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;


    @Override
    public void addStudent(Student student) {
        studentDao.addStudent(student);
    }

    @Override
    public Student selectById(Long cid) {
        System.out.println("aaaaaaaaaaaaaaaa");
        //return courseDao.selectById(cid);
        return null;
    }

    @Override
    public List<Student> selectList() {
        return null;
        //return courseDao.selectList();
    }


}
