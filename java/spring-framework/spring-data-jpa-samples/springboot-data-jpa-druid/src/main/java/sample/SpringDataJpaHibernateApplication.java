package sample;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.EnumSet;

@SpringBootApplication
public class SpringDataJpaHibernateApplication implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) {
        // SpringApplication.run(SpringDataJpaHibernateApplication.class, args);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(serviceRegistry).buildMetadata();
        // 工具类
        SchemaExport export = new SchemaExport();
        // 输出建表语句
        // 会根据hbm文件将实体类对应的数据表全部删除再创建表
        export.create(EnumSet.of(TargetType.STDOUT), metadata);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}