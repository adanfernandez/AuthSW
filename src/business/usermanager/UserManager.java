package business.usermanager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import conf.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.JWT;
import model.User;
import persistence.SimpleDataServiceFactory;
import persistence.user.UserDataService;
import utils.EncryptionUtils;

public class UserManager implements UserManagerService {

	@Override
	public boolean saveUser(User user) throws NoSuchAlgorithmException, SQLException {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		if (userDataService.getUserByEmail(user.getEmail()) == null) {
			user.setPassword(EncryptionUtils.encrypt(user.getPassword()));
			return userDataService.saveUser(user);
		}
		return false;
	}
 
	@Override
	public User getUserByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException, SQLException {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		if (email != null && password != null) {
			return userDataService.getUserByEmailAndPassword(email, EncryptionUtils.encrypt(password));
		}
		return null;
	}

	@Override
	public boolean deleteUser(long id) throws SQLException {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		userDataService.deleteUser(id);
		return false;
	}

	public JWT generateTokenFromEmail(String email) throws SQLException {
		Date expiration = calculateTokenExpiration();
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		User user = userDataService.getUserByEmail(email);
		if(user != null) {
			String tokenValue = Jwts.builder().setIssuedAt(new Date()).setIssuer("email")
					.setSubject(String.valueOf(email)).setExpiration(expiration)
					.signWith(SignatureAlgorithm.HS512, Config.getInstance().get("privateKey"))
					.compact();
			JWT jwt = new JWT(email, tokenValue,  expiration, user.getId());
			userDataService.saveToken(jwt);
			return jwt;
		}
		return null;		
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

	@Override
	public User getUserByToken(String token) throws SQLException {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		User user = userDataService.getUserByToken(token);
		if(user == null) {
			//Enviamos excepción de 401?
			return null;
		} 
		return user;
	}
}
