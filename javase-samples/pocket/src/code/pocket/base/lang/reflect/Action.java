package code.pocket.base.lang.reflect;

import java.security.PrivilegedAction;

public interface Action<T> extends PrivilegedAction<T> {
	
	String name();
	void afterException();
	void afterCompletion();
	
	default SecurityManager getSecurityManager() {
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			return new SecurityManager();
		}
		return sm;
	}
}
