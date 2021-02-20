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
				Class.forName(config.get("driver"));
				this.con = DriverManager
						.getConnection(config.get("jdbc") + config.get("host") + "/" + config.get("database") + "?user="
								+ config.get("username") + "&allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}

	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
