package use;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

import java.io.Reader;

/**
 * 加载各种模板资源
 */
public class CompsiteTemplateLoader extends ResourceLoader {

    @Override
    public void init(ExtProperties configuration) {

    }

    @Override
    public Reader getResourceReader(String source, String encoding) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public boolean isSourceModified(Resource resource) {
        return false;
    }

    @Override
    public long getLastModified(Resource resource) {
        return 0;
    }
}
