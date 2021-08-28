package com.example.demo.service;


import com.example.demo.bean.Course;

import java.util.HashMap;
import java.util.List;

public interface CourseService {

    public void addCourse(Course course);

    public Course selectById(Long cid);

    public List<Course> selectList();

    public List<Course> selectListByBetween();

    public List<Course> selectListByIn(HashMap<String,Long> hashMap);

}
