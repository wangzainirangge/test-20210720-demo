package com.example.demo.controller;

import com.example.demo.util.AsyncDeleteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/test")
public class DeleteDataController {

    @Autowired
    private AsyncDeleteData asyncDeleteData;

    @RequestMapping(value = {"/delete"},method = RequestMethod.GET)
    public void findById(){
        asyncDeleteData.deleteData();
    }

}
