package jsamples;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Disabled("Disabled until bug #99 has been fixed")
class DisabledTestDemo{
    @Test
    void testWillBeSkipped(){}
}

class DisabledTest{
    @Disabled("Disabled until bug #43 has been resolved")
    @Test
    void testWillBeSkipped(){}

    @Test
    void testWillBeExecuted(){}
}

class ConditionOnOperatingSystem{
    @Test
    @EnabledOnOs(OS.MAC)
    void onlyOnMacOs(){}

    @TestOnMac
    void testOnMac(){}

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void onLinuxOrMac(){}

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void notOnWindows(){}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @EnabledOnOs(OS.MAC)
    @interface TestOnMac{}  // Costume annotation
}

class ConditionOnArchitecture{
    @Test
    @EnabledOnOs(architectures = "aarch64")
    void onAarch64(){}

    @Test
    @DisabledOnOs(architectures = "x86_64")
    void notOnX86_64(){}

    @Test
    @EnabledOnOs(value = OS.MAC, architectures = "aarch64")
    void onNewMac(){}

    @Test
    @DisabledOnOs(value = OS.MAC, architectures = "aarch64")
    void notOnNewMac(){}
}

class ConditionOnJre{
    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void onlyOnJava8(){}

    @Test
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11})
    void onJava8Or11(){}

    @Test
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_11)
    void fromJava8To11(){}

    @Test
    @EnabledForJreRange(min = JRE.JAVA_9)
    void fromJava9ToCurrentJavaFeatureNumber(){}

    @Test
    @EnabledForJreRange(max = JRE.JAVA_15)
    void fromJava8To15(){}

    @Test
    @DisabledForJreRange(min = JRE.JAVA_9)
    void notFromJava9ToCurrentJavaFeatureNumber(){}

    @Test
    @DisabledForJreRange(max = JRE.JAVA_11)
    void notFromJava8To11(){}
}

class ConditionOnSystemPropertyAndEnvironmentVariable{
    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void only64BitArchitectures(){}

    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    void notOnCiServer(){}

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
    void onlyOnStagingServer(){}

    @Test
    @DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
    void notOnDeveloperWorkStation(){}
}

class CostumeConditionTest{
    @Test
    @EnabledIf("costumeCondition")
    void enabled(){}

    @Test
    @DisabledIf("costumeCondition")
    void disabled(){}

    boolean costumeCondition(){
        return true;
    }

    @Test
    @EnabledIf("jsamples.ExternalCondition#costumeCondition") // Full qualified path
    void DisabledDemo(){}
}

class ExternalCondition{
    static boolean costumeCondition(){
        return false;
    }
}





