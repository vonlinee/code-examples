package utils;

import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * org.apache.juli.logging.LogFactory
 * 通过在ClassPath路径下配置添加文件，META-INF/services/com.study.SPIService，文件名为接口的全限定类名。
 * 在配置文件中加入实现类的全限定类名 utils.JuliLogger
 * 
 * 
 * https://blog.csdn.net/weixin_33910460/article/details/91757113
 */
public class Log4jLogger implements Log {
	
	private String name;
	private Logger log;
	
	// ServiceLoader实例化服务类要求必须要有默认构造器
    public Log4jLogger() {}
	
	public Log4jLogger(String logImplName) {
		this.name = logImplName; // 全限定类名
		log = LoggerFactory.getLogger(logImplName);
	}
	
	@Override
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}

	@Override
	public boolean isFatalEnabled() {
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	@Override
	public void trace(Object message) {
		log.info(message.toString());
	}

	@Override
	public void trace(Object message, Throwable t) {
		log.info(message.toString(), t);
	}

	@Override
	public void debug(Object message) {
		log.debug(message.toString());
	}

	@Override
	public void debug(Object message, Throwable t) {
		log.debug(message.toString(), t);
	}

	@Override
	public void info(Object message) {
		log.info(message.toString());
	}

	@Override
	public void info(Object message, Throwable t) {
		log.info(message.toString(), t);
	}

	@Override
	public void warn(Object message) {
		log.warn(message.toString());
	}

	@Override
	public void warn(Object message, Throwable t) {
		log.warn(message.toString(), t);
	}

	@Override
	public void error(Object message) {
		log.error(message.toString());
	}

	@Override
	public void error(Object message, Throwable t) {
		log.error(message.toString(), t);
	}

	/**
	 * 指出每个严重的错误事件将会导致应用程序的退出。这个级别比较高了。重大错误，这种级别你可以直接停止程序了
	 */
	@Override
	public void fatal(Object message) {
		log.error("FATAL {} ", message.toString());
	}

	@Override
	public void fatal(Object message, Throwable t) {
		log.error("FATAL {} ", message.toString(), t);
	}
}
