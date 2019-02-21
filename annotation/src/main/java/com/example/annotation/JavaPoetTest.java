package com.example.annotation;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

public class JavaPoetTest {

    public static void tryCreateJavaFile(Filer filer) {

        CodeBlock codeBlock = CodeBlock.builder()
                .addStatement("System.out.println(\"Static init\")")
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("Clazz")
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .addTypeVariable(TypeVariableName.get("T"))
                .addTypeVariable(TypeVariableName.get("E"))
                .superclass(MyFactory.class)
                .addSuperinterface(Serializable.class)
                .addSuperinterface(ParameterizedTypeName.get(Comparable.class, String.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Map.class),
                        TypeVariableName.get("T"), WildcardTypeName.subtypeOf(String.class)))

                .addStaticBlock(codeBlock)


                .build();


        JavaFile javaFile = JavaFile.builder("com.walfud.howtojavapoet", typeSpec).build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
