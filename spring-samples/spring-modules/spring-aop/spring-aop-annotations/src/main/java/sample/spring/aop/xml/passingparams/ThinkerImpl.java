package sample.spring.aop.xml.passingparams;

public class ThinkerImpl implements Thinker {
	private String thoughts;
		public void thinkOfSomething(String thoughts) {
		this.thoughts = thoughts;
		System.out.println("thinkOfSomething executed");
	}
	public String getThoughts() {
		return thoughts;
	}
}
