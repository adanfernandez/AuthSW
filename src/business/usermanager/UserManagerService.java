package business.usermanager;

import model.JWT;
import model.User;

public interface UserManagerService {

	public boolean saveUser(User user);
	
	public User getUserByEmailAndPassword(String email, String password);
	
	public boolean deleteUser(long id);
	
	public JWT generateTokenFromEmail(String email);
	
	public User getUserByToken(String token);
	
}
