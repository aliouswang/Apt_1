package com.example.annotation;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.util.List;
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
                        TypeVariableName.get("T"), TypeVariableName.get("E")))

                .addStaticBlock(codeBlock)

                .addField(FieldSpec.builder(int.class, "mInt", Modifier.PRIVATE).build())
                .addField(FieldSpec.builder(int[].class, "mArr", Modifier.PRIVATE).build())
                .addField(FieldSpec.builder(ClassName.get(File.class), "mRef", Modifier.PRIVATE).build())
                .addField(FieldSpec.builder(ParameterizedTypeName.get(List.class, String.class), "mList", Modifier.PRIVATE).build())
                .addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), WildcardTypeName.subtypeOf(String.class)), "mList2", Modifier.PRIVATE).build())
                .addField(FieldSpec.builder(TypeVariableName.get("T"), "mT", Modifier.PRIVATE).build())

                .addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build())

                .addMethod(MethodSpec.methodBuilder("method").addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                        .addTypeVariable(TypeVariableName.get("T"))
                        .returns(int.class)
                        .addParameter(String.class, "string")
                        .addParameter(TypeVariableName.get("T"), "T")

                        .build())


                .build();


        JavaFile javaFile = JavaFile.builder("com.walfud.howtojavapoet", typeSpec).build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
