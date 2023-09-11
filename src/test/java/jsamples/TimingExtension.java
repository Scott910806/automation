package jsamples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import java.lang.reflect.Method;

@Slf4j
public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final String START_TIME = "start_time";

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        this.getStore(context).put(START_TIME, System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        long startTime = getStore(context).remove(START_TIME, long.class);
        long duration = System.currentTimeMillis() - startTime;
        log.info(String.format("Method [%s] took %s ms.", testMethod.getName(), duration));
    }

    private Store getStore(ExtensionContext context){
//        这里使用类和方法对象标识Namespace，也可以用其他对象标识Namespace
        return context.getStore(Namespace.create(this.getClass(), context.getRequiredTestMethod()));
    }
}

@ExtendWith(TimingExtension.class)
class TimingExtensionTest{

    @Test
    void sleep20ms() throws Exception{
        Thread.sleep(20);
    }

    @Test
    void sleep50ms() throws Exception{
        Thread.sleep(50);
    }
}
