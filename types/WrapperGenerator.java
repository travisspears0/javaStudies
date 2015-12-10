package types;

import com.sun.codemodel.JAssignmentTarget;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WrapperGenerator {
    
    public static void generateWrapper(Class wrappedClass)
            throws NoClassDefFoundError, IOException,
            IllegalAccessException, InvocationTargetException, ClassNotFoundException,
            InstantiationException, NoSuchMethodException, JClassAlreadyExistsException {
        
        
        // classinitialization
        String className = wrappedClass.getSimpleName()+"Wrapper";
        JCodeModel model = new JCodeModel();
        JDefinedClass wrapperClass = model._class(className);
        JClass jWrappedClass = model.directClass(wrappedClass.getName());
        
        //fields
        JFieldVar wrappedObjectReference = wrapperClass.field(
                JMod.PRIVATE|JMod.FINAL, wrappedClass, "wrappedReference");
        JFieldVar loggerReference = wrapperClass.field(
                JMod.PRIVATE|JMod.FINAL, Logger.class, "logger");
        JFieldVar testTimeReference = wrapperClass.field(
                JMod.PRIVATE, Long.TYPE, "testTime");
        
        
        //constructors
        Constructor[] constructors = wrappedClass.getConstructors();
        for(Constructor constructor : constructors) {
            JMethod constructorCreated = wrapperClass.constructor(constructor.getModifiers());
            JInvocation initializeExpression = JExpr._new(jWrappedClass);
            for(int i=0,size=constructor.getParameters().length ; i<size ; ++i) {
                Parameter param = constructor.getParameters()[i];
                constructorCreated.param(0, param.getType(), "arg" + (i));
                initializeExpression.arg(JExpr.ref("arg"+i));
            }
            constructorCreated.body().assign(
                    wrappedObjectReference,
                    initializeExpression);
            constructorCreated.body().assign(
                    loggerReference,
                    model.directClass(Logger.class.getName()).staticInvoke("getLogger")
                            .arg(className+"Logger"));
        }
        
        //useful references
        JInvocation nanoTimeReference = 
                model.directClass(System.class.getName()).staticInvoke("nanoTime");
        JClass longReference = model.directClass(Long.class.getName());
        //private methods with logging time
        JMethod timerStartMethod = wrapperClass.method(
                JMod.PRIVATE, 
                Void.TYPE,
                "timerStart");
        timerStartMethod.body().assign(testTimeReference,nanoTimeReference);
        
        JMethod timerStopMethod = wrapperClass.method(
                JMod.PRIVATE, 
                Void.TYPE,
                "timerStop");
        JExpression countingExpression = JExpr.ref(testTimeReference.name())
                .mul(JExpr.lit(-1))
                .plus(nanoTimeReference)
                .div(JExpr.lit(1000000))
                .plus(JExpr.lit(""));
        timerStopMethod.body().add(loggerReference.invoke("log")
                .arg(model.directClass(Level.class.getName()).staticRef("INFO"))
                .arg(countingExpression));
        
        //methods
        Method[] methods = wrappedClass.getDeclaredMethods();
        for(Method method : methods) {
            JMethod methodCreated = wrapperClass.method(
                    method.getModifiers(), 
                    method.getReturnType(),
                    method.getName());
            methodCreated.body().invoke(timerStartMethod);
            JInvocation callbackExpression = wrappedObjectReference.invoke(method.getName());
            for(int i=0,size=method.getParameters().length ; i<size ; ++i) {
                Parameter param = method.getParameters()[i];
                methodCreated.param(0, param.getType(), "arg" + (i));
                callbackExpression.arg(JExpr.ref("arg"+i));
            }
            if(     method.getReturnType().toString().equals("void") || 
                    method.getReturnType().toString().equals("Void")) {
                methodCreated.body().add(callbackExpression);
                methodCreated.body().invoke(timerStopMethod);
            } else {
                JAssignmentTarget jat = methodCreated.body().decl(JType.parse(model, method.getReturnType().getName()), "res");
                methodCreated.body().assign(jat, callbackExpression);
                methodCreated.body().invoke(timerStopMethod);
                methodCreated.body()._return(jat);
            }
        }
        
        /*timerStopMethod.body()._return(
                longReference.staticInvoke("sum")
                        .arg(nanoTimeReference)
                        .arg(longReference.staticInvoke("")));*/
        //saving file
        File file = new File("./src/types/");
        file.mkdirs();
        model.build(file);
        
        
    }
    
}
