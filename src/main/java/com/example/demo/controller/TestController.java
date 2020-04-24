package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AsyncService;

@RestController
public class TestController {
    @Autowired
    private AsyncService asyncService;

    @GetMapping("/test")
    public void test() {
        asyncService.test("hello", "world");
    }

    @GetMapping("/test2")
    public void test2() {
        asyncService.test2("hello", "world");
    }
}
