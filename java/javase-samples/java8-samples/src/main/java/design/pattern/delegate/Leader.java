package design.pattern.delegate;

import java.util.HashMap;
import java.util.Map;

public class Leader {
	// 预先知道每个员工的特长、特征，分发任务
	private final Map<String, Employee> register = new HashMap<String, Employee>();
	
	public Leader() {
		register.put("加密", new EmployeeA(null));
		register.put("架构", new EmployeeB());
	}

	public void doing(String command) {
		// 交给指定的员工去做
		register.get(command).doing(command);
	}
}