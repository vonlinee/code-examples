package samples.config;

import com.ctrip.framework.apollo.internals.LocalFileConfigRepository;
import lombok.Data;

@Data
public class ReadOnlyConfiguration {

    private boolean enableSwagger = true;
    private boolean enableLombok = true;
    private boolean enableMergeXml = false;
    private boolean enableSpringBootJpa = false;
    private boolean enableMyBatisPlusJpa = false;

    LocalFileConfigRepository localFileConfigRepository;
}
