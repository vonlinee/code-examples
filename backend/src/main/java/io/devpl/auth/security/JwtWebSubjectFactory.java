package io.devpl.auth.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 继承默认的DefaultWebSubjectFactory，关闭自动创建Session功能
 * 工厂模式，负责创建Subject
 */
public class JwtWebSubjectFactory extends DefaultWebSubjectFactory {

    public JwtWebSubjectFactory() {
        super();
    }

    @Override
    public Subject createSubject(SubjectContext context) {
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
