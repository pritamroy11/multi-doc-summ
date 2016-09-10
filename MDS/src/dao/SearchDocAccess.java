/*All Operations for Search_Doc table*/
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SearchDocAccess {
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
	
	@SuppressWarnings("unchecked")
	public ArrayList getDocs (String keys []) throws SQLException {
		ArrayList al = new ArrayList();
		for (int i = 0; i < keys.length; i++){
			pSt = con.prepareStatement("SELECT DOC_NO FROM SEARCH_DOC WHERE KEYWORD = ?");
			pSt.setString(1, keys[i]);
			rs = pSt.executeQuery();
			while (rs.next()){
				al.add(keys[i]);
				al.add(rs.getInt(1));
			}
		}
		return al; //Returns keyword (key), doc number (value) pair
	}
	
	public int insertKeys (String keys[], int doc_no) throws SQLException {
		int inserts = 0, i = 0;
		
		while (keys [i] != null){
			pSt = con.prepareStatement("INSERT INTO SEARCH_DOC VALUES (?, ?)");
			pSt.setInt(1, doc_no);
			pSt.setString(2, keys[i]);
			inserts += pSt.executeUpdate();
			i++;
		}/*
		for (j = 0; j < keys.length; j++ ) {
			pSt = con.prepareStatement("INSERT INTO SEARCH_DOC VALUES (?, ?)");
			pSt.setInt(1, doc_no);
			pSt.setString(2, keys[j]);
			inserts += pSt.executeUpdate();
		}
		*/
		return (inserts); //returns number of keys inserted.
	}
	
	public String getKeywords(int docno) throws SQLException{
		String key = " ";
		
		pSt = con.prepareStatement("select keyword from search_doc where doc_no=?");
		pSt.setInt(1, docno);
		rs = pSt.executeQuery();
		
		while(rs.next())
			key= key + rs.getString(1) + ", ";
		
		return key;
	}
	
	public String getKeyw(int docno) throws SQLException{
		String key = " ";
		
		pSt = con.prepareStatement("select keyword from search_doc where doc_no=?");
		pSt.setInt(1, docno);
		rs = pSt.executeQuery();
		
		while(rs.next())
			key= key + rs.getString(1) + " ";
		
		return key;
	}
	
	public void removeKeywords(int docno) throws SQLException{
		pSt = con.prepareStatement("delete from search_doc where doc_no=?");
		pSt.setInt(1, docno);
		pSt.executeUpdate();
	}
}
