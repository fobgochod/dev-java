package com.fobgochod.spring;

import org.springframework.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * CGlibUtils.java
 *
 * @author fobgochod
 * @date 2021/10/4 17:36
 */
public class CGlibUtils {

    /**
     * 保存代理字节码文件
     * <p>
     * 升级JDK17 异常: <code>Unable to make field private static java.util.Properties java.lang.System.props accessible: module java.base does not "opens java.lang" to unnamed module @3fa77460</code>
     * JVM options: --add-opens java.base/java.lang=ALL-UNNAMED
     */
    public static void generatedCGlibProxyFiles(String dir) throws Exception {
        Field field = System.class.getDeclaredField("props");
        field.setAccessible(true);
        Properties props = (Properties) field.get(null);
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, System.getProperty("user.dir") + "/target/proxy/" + dir);
        props.put("net.sf.cglib.core.DebuggingClassWriter.traceEnabled", "true");
    }
}
