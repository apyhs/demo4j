package demo.clazz;

import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

public class QdoxDemo {
    private static Logger log = LoggerFactory.getLogger(QdoxDemo.class);

    @Test
    public void test1() {
        String path = "src/main/java";
        path = StringUtils.replace(path, "\\", "/");
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(path));
        Collection<JavaClass> classes = builder.getClasses();
        for (JavaClass javaClass : classes) {
            String fullyQualifiedName = javaClass.getFullyQualifiedName();
            log.info("{}", fullyQualifiedName);
        }
    }

}
