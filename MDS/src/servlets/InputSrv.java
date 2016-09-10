package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DocInfoAccess;
import dao.SearchDocAccess;

/**
 * Servlet implementation class Inputserv
 */
public class InputSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title;
		String intro;
		String desc;
		String feat;
		String concl;
		String keys;
		String key[] = new String[100];
		int key_no = 0;
		int last_space = 0;
		int keys_inserted = 0;
		
		DocInfoAccess dao = new DocInfoAccess();
		SearchDocAccess daoSrch = new SearchDocAccess();
		int resp;
		
		response.setContentType("text/HTML");
		PrintWriter out = response.getWriter();
		
		title = request.getParameter("title");
		intro = request.getParameter("intro");
		desc = request.getParameter("desc");
		feat = request.getParameter("feat");
		concl = request.getParameter("concl");
		keys = request.getParameter("keys");
		
		if(title.isEmpty() || intro.isEmpty() || desc.isEmpty() || feat.isEmpty() || concl.isEmpty() || keys.isEmpty()) {
			out.println("<h3><font color='red' size='+2'><br> You cant leave any field blank.....</br> All fields are mandatory.... </font></h3>");
			out.print ("<br></br></br><a href='InputPage.jsp'>Go Back</a>");
		}
		else {
		keys = keys + " ";
		for (int i = 0; i < keys.length(); i++){
			if (keys.substring(i, i+1).equals(" ")) {
				key[key_no] = keys.substring(last_space, i);
				key_no++;
				last_space = i+1;
			}
		}
		
		try {
			dao.openDBConnection();
			resp = dao.insertDoc(title, intro, desc, feat, concl); //resp gets the doc_no where data has been inserted
			if (resp == 0)
				out.println ("<font color='red' size='+2'>Failure!!! </font></br></br><a href='InputPage.jsp'>Go Back</a>");
			else{
				out.println("<font color='green' size='+2'> Success... Document Inserted... </font><br><br>");
				daoSrch.openDBConnection();
				keys_inserted = daoSrch.insertKeys(key, resp); 
				out.print (keys_inserted + " keys inserted.</br></br><a href='InputPage.jsp'>Go Back</a>"); //Needs some processing on how the results will be displayed.
				daoSrch.closeDBConnection();
			}
			dao.closeDBConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}
}