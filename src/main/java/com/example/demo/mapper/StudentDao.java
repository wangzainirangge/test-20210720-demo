package com.example.demo.mapper;

import com.example.demo.bean.Course;
import com.example.demo.bean.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {

    public boolean addStudent(@Param("student") Student student);

    public Course selectById(@Param("cid") Long cid);

    public List<Course> selectList();

}
