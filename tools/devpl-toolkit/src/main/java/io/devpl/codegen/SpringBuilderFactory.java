package io.devpl.codegen;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringBuilderFactory implements BuilderFactory {

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        JavaFXBuilderFactory factory;
        return new SpringJavaFXControllerBuilder<>(type);
    }

    static class SpringJavaFXControllerBuilder<T> implements Builder<T>, BeanFactoryAware {

        private BeanFactory beanFactory;
        private final Class<T> type;

        public SpringJavaFXControllerBuilder(Class<T> type) {
            this.type = type;
        }

        @Override
        public T build() {
            return beanFactory.getBean(type);
        }

        @Override
        public void setBeanFactory(@NotNull BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }
    }
}
