package io.netty.tomcat.servlet;

import io.netty.tomcat.http.GPRequest;
import io.netty.tomcat.http.GPResponse;
import io.netty.tomcat.http.GPServlet;

public class FirstServlet extends GPServlet {

	public void doGet(GPRequest request, GPResponse response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(GPRequest request, GPResponse response) throws Exception {
		response.write("This is First Serlvet");
	}

}
