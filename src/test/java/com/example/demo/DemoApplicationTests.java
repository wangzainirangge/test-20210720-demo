package com.example.demo;


import com.example.demo.util.AsyncDeleteData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DemoApplicationTests {

    @Autowired
    AsyncDeleteData asyncDeleteData;

    @Test
    void contextLoads() {

        //deleteDataUtil.deleteData();
        asyncDeleteData.deleteData();


    }


}
