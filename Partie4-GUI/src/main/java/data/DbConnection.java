package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	private DbConnection() {}

	
	public static Connection getConnection() throws SQLException {
		String user, passwd, driver, url;
		user = "c##java_bank";
		passwd = "passwd";
		driver = "jdbc:oracle:thin:";
		url = driver + "@localhost:1521:xe";
		return DriverManager.getConnection(url, user, passwd);
		
	}

}
