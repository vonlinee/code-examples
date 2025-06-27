package demo;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.RuntimeSingleton;

import java.io.StringWriter;

public class VelocityTest {

    public static void main(String[] args) {

        Velocity.init();

        VelocityContext vc = new VelocityContext();
        vc.put("name", "zs");

        StringWriter sw = new StringWriter();

        String template = """
                                
                #where {
                    #eq(1, $name)
                }
                """;

        RuntimeInstance rs = (RuntimeInstance) RuntimeSingleton.getRuntimeServices();

        System.out.println(sw);
    }
}
