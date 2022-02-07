package code.example.dynamicweb.servlet;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//http://localhost:8888/dynamicweb/TestJndiServlet
@WebServlet("/TestJndiServlet")
public class TestJndiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TestJndiServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object jnidValue = null;
		try {
		    Context context = new InitialContext();
		    jnidValue  = context.lookup("java:comp/env/jndiName");
		} catch (NamingException e) {
		    e.printStackTrace();
		}
		response.getWriter().append("Served at: ").append(jnidValue.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
