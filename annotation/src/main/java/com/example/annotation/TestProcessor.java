package com.example.annotation;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {

    ProcessorTool processorTool;
    ProcessingEnvironment mProcessingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        processorTool = new ProcessorTool(processingEnvironment);
        mProcessingEnvironment = processingEnvironment;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add(AAAAA.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(AAAAA.class);
        for (Element element : elements) {
            AAAAA annotation = element.getAnnotation(AAAAA.class);
            if (element instanceof TypeElement) {
                processorTool.addArgs("TypeElement:" + annotation.value());

                processorTool.addArgs("----print package message----");
                PackageElement packageElement = mProcessingEnvironment.getElementUtils().getPackageOf(element);
                processorTool.addArgs("packageElement:" + packageElement.getSimpleName().toString());
                processorTool.addArgs("packageElement:" + packageElement.getQualifiedName());

                processorTool.addArgs("----print Generic message-----");

                TypeElement typeElement = (TypeElement) element;
                List<? extends TypeParameterElement> typeParameterElements = typeElement.getTypeParameters();
                for (TypeParameterElement parameterElement : typeParameterElements) {
                    processorTool.addArgs(parameterElement.getSimpleName().toString());
                }

                for (Element element1 : typeElement.getEnclosedElements()) {
                    processorTool.addArgs(element1.getSimpleName().toString());
                }
            }else if (element instanceof ExecutableElement) {
                processorTool.addArgs("ExecutableElement:" + annotation.value());
            }else if (element instanceof VariableElement) {
                processorTool.addArgs("varitableElement:" + annotation.value());
            }


        }

        processorTool.printlog();

        return false;
    }
}
