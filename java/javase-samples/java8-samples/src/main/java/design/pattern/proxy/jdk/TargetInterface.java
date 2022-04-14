package design.pattern.proxy.jdk;

public interface TargetInterface {
    String targetMethod(String param);
    
    default int defaultMethod(int i, int j) {
    	return i + j;
    }
}