package business.usermanager;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import conf.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.User;
import persistence.SimpleDataServiceFactory;
import persistence.user.UserDataService;
import utils.EncryptionUtils;

public class UserManager implements UserManagerService {

	@Override
	public boolean saveUser(User user) {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		if (userDataService.userExistsByEmail(user.getEmail())) {
			return userDataService.saveUser(user);
		}
		return false;
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		if (email != null && password != null) {
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

	public String generateToken(User user) {
		Date expiration = calculateTokenExpiration();
		return Jwts.builder().setIssuedAt(new Date()).setIssuer("EMAIL")
				.setSubject(user.getEmail()).setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, Config.getInstance().get("privateKey")).compact();
	}

	/**
	 * Calculate expiration of a JWT
	 * 
	 * @return
	 */
	private static Date calculateTokenExpiration() {
		Long expiration = Long.parseLong(Config.getInstance().get("expiration"));
		Date date = new Date(new Date().getTime() + expiration);
		return date;
	}

}
