package com.example.demo;

import com.example.demo.bean.Course;
import com.example.demo.bean.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.StudentService;
import com.example.demo.util.ThreadLocalTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDemo {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Test
    public void test01(){
        Long startTs = System.currentTimeMillis();
        Course course = new Course();
        course.setCid(startTs);
        course.setCname("java");
        course.setUserId(26L);
        course.setCstatus("Normal");
        courseService.addCourse(course);
    }


    @Test
    public void test02(){
        ThreadLocalTools.getStringThreadLocal().set("testaaa");
        Long startTs = System.currentTimeMillis();
        Student student = new Student();
        student.setCid(startTs);
        student.setCname("java");
        student.setUserId(21L);
        student.setCstatus("Normal");
        studentService.addStudent(student);
    }

}
