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
	public boolean saveUser(User user) {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		try {
			if (userDataService.getUserByEmail(user.getEmail()) == null) {
				user.setPassword(EncryptionUtils.encrypt(user.getPassword()));
				return userDataService.saveUser(user);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Revisar que hacer con el error
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Revisar que hacer con el error
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		try {
			if (email != null && password != null) {
				return userDataService.getUserByEmailAndPassword(email, EncryptionUtils.encrypt(password));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Revisar que hacer con el error
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Revisar que hacer con el error
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteUser(long id) {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		try {
			userDataService.deleteUser(id);
		} catch (SQLException e) {
			// TODO Revisar que hacer con el error
			e.printStackTrace();
		}
		return false;
	}

	public JWT generateTokenFromEmail(String email) {
		Date expiration = calculateTokenExpiration();
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		User user;
		try {
			user = userDataService.getUserByEmail(email);
			if (user != null) {
				String tokenValue = Jwts.builder().setIssuedAt(new Date()).setIssuer("email")
						.setSubject(String.valueOf(email)).setExpiration(expiration)
						.signWith(SignatureAlgorithm.HS512, Config.getInstance().get("privateKey")).compact();
				JWT jwt = new JWT(email, tokenValue, expiration, user.getId());
				userDataService.saveToken(jwt);
				return jwt;
			}
		} catch (SQLException e) {
			// TODO Revisar que hacer con el error
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUserByToken(String token) {
		UserDataService userDataService = new SimpleDataServiceFactory().getUserDataService();
		User user = null;
		try {
			user = userDataService.getUserByToken(token);
			if (user == null) {
				// TODO: Enviamos excepción de 401?
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
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
