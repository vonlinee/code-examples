package samples.spring.ioc.inject.advanced;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<Model> {

    @Override
    public Model getObject() throws Exception {
        return new Model();
    }

    @Override
    public Class<?> getObjectType() {
        return Model.class;
    }
}
