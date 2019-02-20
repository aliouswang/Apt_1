package com.example.aliouswang.apt_1;

import com.example.annotation.AAAAA;

@AAAAA("this is a class")
public class Test<T> {

    @AAAAA("this is a field")
    String hello;

    @AAAAA("this is a method")
    public String say(@AAAAA("this is a paramter") String args) {

        Class<?> clazz = Test.class;

        return "Say Hello!";
    }
}
