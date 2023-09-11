package jsamples;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Parameter;

public class RandomParametersExtension implements ParameterResolver {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface Random{}

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(Random.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getRandomValue(parameterContext.getParameter(), extensionContext);
    }

    private Object getRandomValue(Parameter parameter, ExtensionContext extensionContext){
        Class<?> type = parameter.getType();
//        自定义的Random注解与java.util中自带的Random类冲突，这需要使用类的全名加以区分，通过反射获取java的Random类
        java.util.Random random = extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).getOrComputeIfAbsent(java.util.Random.class);
        if(int.class.equals(type)){
            return random.nextInt();
        }
        if (double.class.equals(type)){
            return random.nextDouble();
        }
        throw new ParameterResolutionException("No random generator implement for " + type);
    }
}
