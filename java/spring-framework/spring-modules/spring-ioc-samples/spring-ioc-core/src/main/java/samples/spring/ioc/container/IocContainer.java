package samples.spring.ioc.container;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations= {"classpath:*.xml"})
public class IocContainer {

	
}
