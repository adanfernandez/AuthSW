package business.usermanager;

import java.security.NoSuchAlgorithmException;

import model.User;
import persistence.SimpleDataServiceFactory;
import persistence.user.UserDataService;
import utils.EncryptionUtils;

public class UserManager implements UserManagerService {

	@Override
	public boolean saveUser(User user) {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		if(userDataService.userExistsByEmail(user.getEmail())) {
			return userDataService.saveUser(user);
		}
		return false;
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		if(email != null && password != null) {
			return userDataService.getUserByEmailAndPassword(email, EncryptionUtils.encrypt(password));
		}
		return null;
	}

	@Override
	public boolean deleteUser(long id) {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		userDataService.deleteUser(id);
		return false;
	}

}
