package business.usermanager;

import java.security.NoSuchAlgorithmException;

import model.JWT;
import model.User;

public interface UserManagerService {

	public boolean saveUser(User user) throws NoSuchAlgorithmException;
	
	public User getUserByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException;
	
	public boolean deleteUser(long id);
	
	public JWT getTokenFromEmail(String email);
}
