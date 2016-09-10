package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDocAccess;

/**
 * Servlet implementation class UpdateKeywords
 */
public class UpdateKeywords extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/HTML");		
		PrintWriter out = response.getWriter();
		
		String keys;
		String key[] = new String[100];
		int docno, key_no = 0;
		int last_space = 0;
		int keys_inserted = 0;
		
		docno = Integer.parseInt(request.getParameter("docno"));
		keys = request.getParameter("keyw");
		if(keys.isEmpty()){
			out.println("<h3><font color='red' size='+2'><br> You have to give atleast one keyword..... </font></h3>");
			out.print ("<br></br></br><a href='ViewDoc.jsp'>Go Back</a>");
		}
		else{
			keys = keys + " ";
			for (int i = 0; i < keys.length(); i++){
				if (keys.substring(i, i+1).equals(" ")) {
					key[key_no] = keys.substring(last_space, i);
					key_no++;
					last_space = i+1;
				}
			}
			
		SearchDocAccess dao = new SearchDocAccess();
		try {
			dao.openDBConnection();
			dao.removeKeywords(docno);
			keys_inserted = dao.insertKeys(key, docno); 
			out.print ("<br><br><font color='green' size='+2'> <h1> "+keys_inserted + " </h1> keys inserted and updated successfully....</font><br></br></br><a href='ViewDoc.jsp'>Go Back</a>");
			dao.closeDBConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/HTML");		
		PrintWriter out = response.getWriter();
		int docno;
		String docKey;
		
		docno = Integer.parseInt(request.getParameter("docno"));
		
		SearchDocAccess dao = new SearchDocAccess();
		try {
			dao.openDBConnection();
		//	docKey = dao.getKeyw(docno);
			
			out.println("<font color='green' size='+3'><b><u> UPDATE KEYWORDS FOR DOC. NO. "+docno+"</u></b></font><br><br>");
			out.println("<form action='UpdateKeywords' method='get'>");
			out.println("<input name='docno' type ='hidden' value= "+ docno +" ></input>");
			out.println("<center><h2>Keywords (seperated by a space) :  <input name='keyw' value=" + dao.getKeyw(docno) + "> </input></br></br>");
			out.println("<input type='submit' value='UPDATE'> </input></h2></center>");
			out.println("</form>");
			
			dao.closeDBConnection();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
