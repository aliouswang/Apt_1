package com.example.annotation;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Messager mMessager;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(BindView.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> bindViewElements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : bindViewElements) {
            //1.获取包名
            PackageElement packageElement = mElementUtils.getPackageOf(element);
            String pkName = packageElement.getQualifiedName().toString();
            note(String.format("package = %s", pkName));

            //2.获取包装类类型
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            String enclosingName = enclosingElement.getQualifiedName().toString();
            note(String.format("enclosindClass = %s", enclosingElement));


            //因为BindView只作用于filed，所以这里可直接进行强转
            VariableElement bindViewElement = (VariableElement) element;
            //3.获取注解的成员变量名
            String bindViewFiledName = bindViewElement.getSimpleName().toString();
            //3.获取注解的成员变量类型
            String bindViewFiledClassType = bindViewElement.asType().toString();

            //4.获取注解元数据
            BindView bindView = element.getAnnotation(BindView.class);
            int id = bindView.value();
            note(String.format("%s %s = %d", bindViewFiledClassType, bindViewFiledName, id));

            //4.生成文件
            createFile(enclosingElement, bindViewFiledClassType, bindViewFiledName, id);
            return true;
        }

        JavaPoetTest.tryCreateJavaFile(mFiler);
        return false;
    }

    private void createFile(TypeElement enclosingElement, String bindViewFiledClassType, String bindViewFiledName, int id) {
        String pkName = mElementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
        try {
            JavaFileObject jfo = mFiler.createSourceFile(pkName + ".ViewBinding", new Element[]{});
            Writer writer = jfo.openWriter();
            writer.write(brewCode(pkName, bindViewFiledClassType, bindViewFiledName, id));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String brewCode(String pkName, String bindViewFiledClassType, String bindViewFiledName, int id) {
        StringBuilder builder = new StringBuilder();
        builder.append("package " + pkName + ";\n\n");
        builder.append("//Auto generated by apt,do not modify!!\n\n");
        builder.append("public class ViewBinding { \n\n");
        builder.append("public static void main(String[] args){ \n");
        String info = String.format("%s %s = %d", bindViewFiledClassType, bindViewFiledName, id);
        builder.append("System.out.println(\"" + info + "\");\n");
        builder.append("}\n");
        builder.append("}");
        return builder.toString();
    }


    private void note(String msg) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void note(String format, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(format, args));
    }

}
