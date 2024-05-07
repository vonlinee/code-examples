package org.example.java8.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl implements HelloService, Remote {

	public HelloServiceImpl() throws RemoteException {
		UnicastRemoteObject.exportObject(this, 8888);
	}
	
	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}
}
