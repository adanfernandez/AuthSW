package persistence;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import conf.Config;

public class MySQLCon {

	private Connection con;
	private Config config = Config.getInstance();

	public Connection getConnection() {

		if (con == null) {
			try {
				Class.forName(config.get("DRIVER"));
				this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba_sw?user=vmchaves&password=123456&allowPublicKeyRetrieval=true&useSSL=false");
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
