package samples.config;

import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class AbstractConfigRepository implements ConfigRepository {

    protected Map<String, Object> configurations;

    @Override
    public void initialize() {

    }

    @Override
    public String name() {
        return "";
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> Map<String, V> getConfig() {
        return (Map<String, V>) configurations;
    }
}
