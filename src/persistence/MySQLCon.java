package persistence;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLCon {

	private Connection con;

	public Connection getConnection() {

		if (con == null) {
			try {
				Class.forName(System.getenv("DRIVER"));
				this.con = DriverManager.getConnection(System.getenv("JDBC") + System.getenv("HOST") + "/"
						+ System.getenv("DATABASE") + "?user=" + System.getenv("USERNAME") + "&password="
						+ System.getenv("PASSWORD") + "&allowPublicKeyRetrieval=true&useSSL=false");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}

	public void closeConnection() {
		try {
			if (con != null) {
				this.con.close();
				this.con = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
