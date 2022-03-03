package io.netty.nio.bio.tomcat.servlet;

import io.netty.nio.bio.tomcat.http.GPRequest;
import io.netty.nio.bio.tomcat.http.GPResponse;
import io.netty.nio.bio.tomcat.http.GPServlet;

public class SecondServlet extends GPServlet {

	public void doGet(GPRequest request, GPResponse response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(GPRequest request, GPResponse response) throws Exception {
		response.write("This is Second Serlvet");
	}

}
