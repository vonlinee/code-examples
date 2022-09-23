package io.devpl.spring.data.jpa.hibernate;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HibernateCustomizer implements HibernatePropertiesCustomizer {
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.use_sql_comments", true);
        hibernateProperties.put("hibernate.integrator_provider", new HibernateIntegratorProvider());
    }
}
