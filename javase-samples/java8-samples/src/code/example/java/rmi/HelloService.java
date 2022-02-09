package code.example.java.rmi;

//客户端和服务端共用的接口
public interface HelloService {
	String sayHello(String name);
}
