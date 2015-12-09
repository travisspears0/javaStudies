package types;

import com.sun.codemodel.JBlock;
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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class WrapperGenerator {
    
    public static void generateWrapper(Class wrappedClass)
            throws NoClassDefFoundError, IOException,
            IllegalAccessException, InvocationTargetException, ClassNotFoundException,
            InstantiationException, NoSuchMethodException, JClassAlreadyExistsException {
        
        String className = wrappedClass.getSimpleName()+"Wrapper";
        JCodeModel jcm = new JCodeModel();
        JDefinedClass wrapperClass = jcm._class(className);
        JFieldVar wrappedObjectReference = wrapperClass.field(
                JMod.PRIVATE|JMod.FINAL, wrappedClass, "wrappedReference");
        JExpr._new(jcm.ref(wrappedClass));
        Constructor[] constructors = wrappedClass.getConstructors();
        JClass jWrappedClass = jcm.directClass(wrappedClass.getName());
        
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
        }
        
        Method[] methods = wrappedClass.getDeclaredMethods();
        for(Method method : methods) {
            JMethod methodCreated = wrapperClass.method(
                    method.getModifiers(), 
                    method.getReturnType(),
                    method.getName());
            JInvocation callbackExpression = wrappedObjectReference.invoke(method.getName());
            for(int i=0,size=method.getParameters().length ; i<size ; ++i) {
                Parameter param = method.getParameters()[i];
                methodCreated.param(0, param.getType(), "arg" + (i));
                callbackExpression.arg(JExpr.ref("arg"+i));
            }
            if(     method.getReturnType().toString().equals("void") || 
                    method.getReturnType().toString().equals("Void")) {
                methodCreated.body().add(callbackExpression);
            } else {
                methodCreated.body()._return(callbackExpression);
            }
        }
        File file = new File("./src/types/");
        file.mkdirs();
        jcm.build(file);
        
        
    }
    
}
