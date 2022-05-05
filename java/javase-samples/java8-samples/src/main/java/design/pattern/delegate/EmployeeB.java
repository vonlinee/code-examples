package design.pattern.delegate;

public class EmployeeB implements Employee {
	@Override
	public void doing(String command) {
		System.out.println("我是员工B,我擅长架构，我开始干活了");
	}
}