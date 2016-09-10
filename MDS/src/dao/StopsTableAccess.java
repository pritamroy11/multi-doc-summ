/*All operations for stops table*/
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StopsTableAccess {
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
	public ArrayList<String> getWords () throws SQLException {
		ArrayList<String> stops = new ArrayList<String>();
		pSt = con.prepareStatement("SELECT * FROM STOPS");
		rs = pSt.executeQuery();
		
		while (rs.next())
			stops.add(rs.getString(1));
	
		return stops;
	}
}
