package persistence.user;

import java.sql.SQLException;

import model.JWT;
import model.User;
import persistence.MySQLCon;

public interface UserDataService {
	
	public boolean saveUser(User user);
	
	public User getUserByEmailAndPassword(String email, String password) throws SQLException;
	
	public boolean updateUser(User user) throws SQLException;
	
	public boolean deleteUser(long id) throws SQLException;
	
	public User getUserByEmail(String email) throws SQLException;
	
	public User getUserByToken(String token) throws SQLException;
	
	public boolean saveToken(JWT jwt);
	
	public MySQLCon getConnection();

}
