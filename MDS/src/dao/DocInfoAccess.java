/*All Operations for Doc_Info table*/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DocInfoAccess {
	Connection con = null;
	PreparedStatement pSt;
	ResultSet rs;
	String url;
	String un;
	String pw;
	String driver;
	
	public void openDBConnection () throws SQLException, ClassNotFoundException {
		url = "jdbc:mysql://localhost:3306/multdocsumm";
		un = "root";
		pw = "admin";
		driver = "com.mysql.jdbc.Driver";
		
		Class.forName(driver);
		con = DriverManager.getConnection(url, un, pw);
	}
	
	public void closeDBConnection () throws SQLException {
		con.close();
	}
	
	public int insertDoc (String title, String intro, String desc, String feat, String concl) throws SQLException {
		int res = 0;
		
		pSt = con.prepareStatement("INSERT INTO DOC_INFO VALUES (NULL, ?, ?, ?, ?, ?)");
		pSt.setString(1, title);
		pSt.setString(2, intro);
		pSt.setString(3, desc);
		pSt.setString(4, feat);
		pSt.setString(5, concl);
		
		res = pSt.executeUpdate();
		
		if (res <= 0) {
			return 0;
		}
		else {
			pSt = con.prepareStatement("SELECT MAX(DOC_NO) FROM DOC_INFO"); 
			rs = pSt.executeQuery();
			while (rs.next())
				res = rs.getInt(1);
			return res;
		}
	}
	
	public int countDocs () throws SQLException {
		int noOfDocs = 0;
		
		pSt = con.prepareStatement("SELECT COUNT(*) FROM DOC_INFO");
		rs = pSt.executeQuery();
		
		while (rs.next())
			noOfDocs = rs.getInt(1); 
		
		return noOfDocs;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList getDocData (ArrayList <Integer> al) throws SQLException {
		ArrayList doc_data = new ArrayList();
		
		for (Object o : al) {
			pSt = con.prepareStatement("SELECT DOC_NO, INTRO, DESCRIPTION, FEATURES, CONCL FROM DOC_INFO WHERE DOC_NO = ?");
			pSt.setInt(1, Integer.parseInt(o.toString()));
			rs = pSt.executeQuery();
			
			while (rs.next()) {
				doc_data.add(rs.getInt(1));
				doc_data.add(rs.getString(2));
				doc_data.add(rs.getString(3));
				doc_data.add(rs.getString(4));
				doc_data.add(rs.getString(5));
			}
		}
		return doc_data;
	}
	
	@SuppressWarnings("unchecked")
	public int[] getDocNo() throws SQLException {
		int k = countDocs();
		int[] tl = new int[k];
		int i=0;
		
		pSt = con.prepareStatement("select doc_no from doc_info");
		rs = pSt.executeQuery();
		
		while(rs.next()){
			tl[i++] = rs.getInt(1);
		}
		
		return tl;
	}
	
	public String getTitle(int docno) throws SQLException {
		
		String tl = null;
		int i=0;
		
		pSt = con.prepareStatement("select title from doc_info where doc_no=?");
		pSt.setInt(1,docno);
		rs = pSt.executeQuery();
		
		while(rs.next())
			tl=rs.getString(1);
		
		return tl;
	}
	
	public String getDoc(int docno) throws SQLException {
		String doc = " ";
		
		pSt = con.prepareStatement("select intro,description,features,concl from doc_info where doc_no=?");
		pSt.setInt(1, docno);
		rs = pSt.executeQuery();
		
		while(rs.next()) {
			doc+=rs.getString(1);
			doc+=rs.getString(2);
			doc+=rs.getString(3);
			doc+=rs.getString(4);
		}
		
		return doc;
	}
}
