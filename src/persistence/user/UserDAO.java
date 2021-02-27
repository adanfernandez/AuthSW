package persistence.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import model.JWT;
import model.User;
import persistence.MySQLCon;

public class UserDAO implements UserDataService {

	private MySQLCon connection = null;

	@Override
	public MySQLCon getConnection() {
		if (connection == null) {
			connection = new MySQLCon();
		}
		return connection;
	}

	@Override
	public boolean saveUser(User user) {
		String query = "INSERT INTO user (email,name,surname,phone,password) VALUES (?,?,?,?,?);";
		try {
			PreparedStatement ps = getConnection().getConnection().prepareStatement(query);

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
			getConnection().closeConnection();
		}
		return false;
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) throws SQLException {
		String query = "SELECT * FROM user WHERE email = ? and password = ?;";
		User user = null;
		try {
			PreparedStatement ps = getConnection().getConnection().prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = this.getUser(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().closeConnection();
		}
		return user;
	}

	@Override
	public boolean updateUser(User user) throws SQLException {
		String query = "UPDATE User SET email = ?, name = ?, surname = ?, phone = ?, deleted = ?;";
		try {
			PreparedStatement ps = getConnection().getConnection().prepareStatement(query);

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
			getConnection().closeConnection();
		}
		return false;
	}

	@Override
	public boolean deleteUser(long id) throws SQLException {
		String query = "UPDATE user SET deleted=true WHERE id=?;";
		try {
			PreparedStatement ps = getConnection().getConnection().prepareStatement(query);
			ps.setLong(1, id);
			ps.executeUpdate();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().closeConnection();
		}
		return false;
	}

	@Override
	public User getUserByEmail(String email) throws SQLException {
		String query = "SELECT * FROM user WHERE email = ? and deleted = false;";
		User user = null;
		try {
			PreparedStatement ps = getConnection().getConnection().prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = this.getUser(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().closeConnection();
		}
		return user;
	}

	@Override
	public User getUserByToken(String token) throws SQLException {
		String query = "SELECT * from user, jwt where jwt.user_id = user.id and jwt.value = ? and jwt.expiration > CURRENT_TIMESTAMP";
		User user = null;
		try {
			PreparedStatement ps = getConnection().getConnection().prepareStatement(query);
			ps.setString(1, token);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = this.getUser(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().closeConnection();
		}
		return user;
	}

	@Override
	public boolean saveToken(JWT jwt) {
		String query = "INSERT INTO jwt (value, expiration, user_id) VALUES (?,?,?);";
		try {
			PreparedStatement ps = getConnection().getConnection().prepareStatement(query);

			ps.setString(1, jwt.getToken());
			ps.setTimestamp(2, convert(jwt.getExpiration()));
			ps.setLong(3, jwt.getIdUsuario());

			ps.executeUpdate();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getConnection().closeConnection();
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

	private static Timestamp convert(Date date) {
		return new Timestamp(date.getTime());
	}

}
