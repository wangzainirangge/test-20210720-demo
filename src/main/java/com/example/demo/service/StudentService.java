package com.example.demo.service;


import com.example.demo.bean.Course;
import com.example.demo.bean.Student;

import java.util.HashMap;
import java.util.List;

public interface StudentService {

    public void addStudent(Student student);

    public Student selectById(Long cid);

    public List<Student> selectList();



}
