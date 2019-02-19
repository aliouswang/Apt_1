package com.example.annotation;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;

public class ProcessorTool {

    private ProcessingEnvironment processingEnv;
    private List<String> args = new ArrayList<>();

    public ProcessorTool(ProcessingEnvironment env) {
        this.processingEnv = env;
    }

    public ProcessorTool addArgs(String arg) {
        args.add(arg);
        return this;
    }

    public void printlog() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Logger");

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("test");

        int len = args.size();
        for (int i = 0; i < len; i++) {
            String arg = args.get(i);
            methodBuilder.addStatement("$T arg" + i + "=$S", String.class, args);
        }

        builder.addMethod(methodBuilder.build());

        JavaFile javaFile = JavaFile.builder("test", builder.build()).build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
