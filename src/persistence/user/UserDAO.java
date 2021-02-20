package persistence.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.JWT;
import model.User;
import persistence.MySQLCon;

public class UserDAO implements UserDataService {

	private Connection connection = null;

	@Override
	public Connection getConnection() {
		if (connection == null) {
			connection = new MySQLCon().getConnection();
		}
		return connection;
	}

	@Override
	public boolean saveUser(User user) {
		String query = "INSERT INTO `USER` (email,name,surname,phone,password) VALUES (?,?,?,?,?);";
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);

			ps.setString(1, user.getEmail());
			ps.setString(2, user.getName());
			ps.setString(3, user.getSurname());
			ps.setString(4, user.getPhone());
			ps.setString(5, user.getPassword());

			ps.executeUpdate();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				getConnection().close();
				this.connection = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) throws SQLException {
		String query = "SELECT * FROM user WHERE email = ? and password = ?;";
		User user = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = this.getUser(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().close();
		}
		return user;
	}

	@Override
	public boolean updateUser(User user) throws SQLException {
		String query = "UPDATE User SET email = ?, name = ?, surname = ?, phone = ?, deleted = ?;";
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);

			ps.setString(1, user.getEmail());
			ps.setString(2, user.getName());
			ps.setString(3, user.getSurname());
			ps.setString(4, user.getPhone());
			ps.setBoolean(5, user.isDeleted());
			ps.executeUpdate();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().close();
		}
		return false;
	}

	@Override
	public boolean deleteUser(long id) throws SQLException {
		String query = "UPDATE user SET deleted=true WHERE id=?;";
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);
			ps.setLong(1, id);
			ps.executeUpdate();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().close();
		}
		return false;
	}

	@Override
	public User getUserByEmail(String email) throws SQLException {
		String query = "SELECT * FROM user WHERE email = ? and deleted = false;";
		User user = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = this.getUser(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().close();
			this.connection = null;
		}
		return user;
	}

	@Override
	public User getUserByToken(String token) throws SQLException {
		String query = "SELECT * from User, jwt where jwt.user_id = user.id and jwt.value = ? and jwt.expiration > CURRENT_TIMESTAMP";
		User user = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);
			ps.setString(1, token);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = this.getUser(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().close();
		}
		return user;
	}

	@Override
	public boolean saveToken(JWT jwt) {
		String query = "INSERT INTO jwt (value, expiration, user_id) VALUES (?,?,?);";
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);

			ps.setString(1, jwt.getToken());
			ps.setDate(2, convert(jwt.getExpiration()));
			ps.setLong(3, jwt.getIdUsuario());

			ps.executeUpdate();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				getConnection().close();
				this.connection = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	private User getUser(ResultSet rs) throws SQLException {
		User user = new User();

		user.setId(rs.getLong(1));
		user.setEmail(rs.getString(2));
		user.setName(rs.getString(3));
		user.setSurname(rs.getString(4));
		user.setPhone(rs.getString(5));

		return user;
	}

	private static java.sql.Date convert(java.util.Date uDate) {
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

}
