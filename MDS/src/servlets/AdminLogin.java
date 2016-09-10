package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminLogin
 */
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String uid = request.getParameter("uid");
		String pass = request.getParameter("pass");
		
		if (uid.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("password"))
			response.sendRedirect("InputPage.jsp");
		else {
			out.println ("<font color='red'>Sorry, incorrect username or password...Try Again.</font>");
			out.println("<jsp:forward page = Admin.jsp></jsp:include>");
			//out.println("</br><a href='Admin.jsp'>Go Back</a>");
		}	
	}

}
