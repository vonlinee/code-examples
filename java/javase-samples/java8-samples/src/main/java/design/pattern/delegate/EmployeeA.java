package design.pattern.delegate;

public class EmployeeA implements Employee {
	
	private Employee delegate;
	
	public EmployeeA(Employee delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void doing(String command) {
		delegate.doing(command);
		// System.out.println("我是员工A,我开始干活了，我擅长加密，执行");
	}
}