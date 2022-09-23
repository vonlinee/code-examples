package io.devpl.spring.data.jpa.hibernate;

import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.ArrayList;
import java.util.List;

public class HibernateIntegratorProvider implements IntegratorProvider {

    @Override
    public List<Integrator> getIntegrators() {
        List<Integrator> integrators = new ArrayList<>();
        integrators.add(new CommentIntegrator());
        return integrators;
    }
}
