package code.example.mybatis.component;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

public class MyBatisUtils {

	public static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
	public static final DefaultObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	public static final DefaultObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	public MyBatisUtils() {
		
	}

	public static MetaObject createMetaObject(Object object) {
		MetaObject javaBeanMeta = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
				DEFAULT_REFLECTOR_FACTORY);
		return javaBeanMeta;
	}
}
