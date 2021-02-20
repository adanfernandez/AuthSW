package persistence.user;

import java.sql.Connection;
import java.sql.SQLException;

import model.User;

public interface UserDataService {
	
	public boolean saveUser(User user);
	
	public User getUserByEmailAndPassword(String email, String password) throws SQLException;
	
	public boolean updateUser(User user) throws SQLException;
	
	public boolean deleteUser(long id) throws SQLException;
	
	public boolean userExistsByEmail(String email) throws SQLException;
	
	public User getUserByToken(String token) throws SQLException;
	
	public Connection getConnection();

}
