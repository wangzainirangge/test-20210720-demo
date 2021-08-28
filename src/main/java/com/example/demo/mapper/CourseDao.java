package com.example.demo.mapper;

import com.example.demo.bean.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao {

    public boolean addCourse(@Param("course") Course course);

    public Course selectById(@Param("cid") Long cid);

    public List<Course> selectList();

    //public List<Course> selectListByBetween();

    //public List<Course> selectListByIn(HashMap<String,Long> hashMap);
}
