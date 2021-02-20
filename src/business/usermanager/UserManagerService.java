package business.usermanager;

import java.security.NoSuchAlgorithmException;

import model.User;

public interface UserManagerService {

	public boolean saveUser(User user);
	
	public User getUserByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException;
	
	public boolean deleteUser(long id);
}
