package com.example.demo.service.impl;


import com.example.demo.bean.Course;
import com.example.demo.mapper.CourseDao;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;


    @Override
    public void addCourse(Course course) {
        courseDao.addCourse(course);
    }

    @Override
    public Course selectById(Long cid) {
        System.out.println("aaaaaaaaaaaaaaaa");
        //return courseDao.selectById(cid);
        return null;
    }

    @Override
    public List<Course> selectList() {
        return null;
        //return courseDao.selectList();
    }

    @Override
    public List<Course> selectListByBetween() {
        return null;
    }

    @Override
    public List<Course> selectListByIn(HashMap<String, Long> hashMap) {
        return null;
    }

}
