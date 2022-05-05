package design.pattern.delegate;

public class Boss {

	/**
	 * 发任务
	 * 
	 * @param command
	 * @param leader
	 */
	public void command(String command, Leader leader) {
		leader.doing(command);
	}
}