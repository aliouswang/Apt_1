package com.example.annotation;

@AAAAA("this is a class")
public class Test<T> {

    @AAAAA("this is a field")
    String hello;

    @AAAAA("this is a method")
    public String say(@AAAAA("this is a paramter") String args) {
        return "Say Hello!";
    }
}
