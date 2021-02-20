package persistence.user;

import model.User;

public class UserDAO implements UserDataService {

	@Override
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
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
	
}
