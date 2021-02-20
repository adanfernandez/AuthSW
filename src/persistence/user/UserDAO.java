package persistence.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		String query = "INSERT INTO USER (`email`,`name`,`surname`,`phone`,`password`) VALUES (?,?,?,?,?); ";
		List<User> users = new ArrayList<User>();
		try {
			PreparedStatement ps = getConnection().prepareStatement(query);
			
			ps.setLong(1, user.getId());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getName());
			ps.setString(4, user.getSurname());
			ps.setString(5, user.getPhone());
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				users.add(this.getUser(rs));
			}
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(long user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userExistsByEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUserByToken(String token) {
		// TODO Auto-generated method stub
		return null;
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
	
}
