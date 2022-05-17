package sample.springboot.config.resources;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 将资源或文件（例如文本文件、XML 文件、属性文件或图像文件）加载到 Spring 应用程序上下文中的不同方法
 * 
 * Resource是 Spring 中用于表示外部资源的通用接口
 */
public class MultiTypeResourceLoader implements ResourceLoader {

	private ResourceLoader delegate;

	public MultiTypeResourceLoader(ResourceLoader delegate) {
		super();
		this.delegate = delegate;
	}

	/**
	 * 该getResource()方法将Resource根据资源路径决定要实例化哪个实现。
	 */
	@Override
	public Resource getResource(String location) {
		return delegate.getResource(location);
	}

	@Override
	public ClassLoader getClassLoader() {
		return delegate.getClassLoader();
	}
}
