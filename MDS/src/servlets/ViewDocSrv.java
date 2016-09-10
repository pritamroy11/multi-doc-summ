package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DocInfoAccess;

/**
 * Servlet implementation class ViewDocSrv
 */
public class ViewDocSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/HTML");		
		PrintWriter out = response.getWriter();
		int docno;
		String doc;
		
		docno = Integer.parseInt(request.getParameter("docno"));
		
		DocInfoAccess dao = new DocInfoAccess();
		try {
			dao.openDBConnection();
			doc = dao.getDoc(docno);
			out.println("<font color='red' size='+2'><u><b>THE FULL DOCUMENT IS (DOC. NO. " + docno +" & TITLE : " + dao.getTitle(docno));
			out.println("):- </b></u></font></br>");
			out.println ("<p>" + doc + "</p>" + "</br><a href='ViewDoc.jsp'>Go Back</a>");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		try {
			dao.closeDBConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
