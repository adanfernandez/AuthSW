package business.usermanager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import model.JWT;
import model.User;

public interface UserManagerService {

	public boolean saveUser(User user) throws NoSuchAlgorithmException, SQLException;
	
	public User getUserByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException, SQLException;
	
	public boolean deleteUser(long id) throws SQLException;
	
	public JWT generateTokenFromEmail(String email) throws SQLException;
	
	public User getUserByToken(String token) throws SQLException;
	
}
