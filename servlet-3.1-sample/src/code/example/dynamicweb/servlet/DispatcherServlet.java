package code.example.dynamicweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Context Root: dynamicweb => http://localhost:8888/dynamicweb/dispatch
 * @author someone
 */
@WebServlet(displayName = "DispatcherServlet", urlPatterns = { "/dispatch" }, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getPathInfo());
		super.doPost(req, resp);
	}
}
