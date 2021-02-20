package persistence.user;

import java.sql.Connection;

import model.User;

public interface UserDataService {
	
	public boolean saveUser(User user);
	
	public User getUserByEmailAndPassword(String email, String password);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(long id);
	
	public boolean userExistsByEmail(String email);
	
	public User getUserByToken(String token);
	
	public Connection getConnection();

}
