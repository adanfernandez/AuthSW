package webservices;

import javax.jws.WebService;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import business.SimpleBusinessFactory;
import business.usermanager.UserManagerService;
import model.JWT;
import model.User;

@WebService(endpointInterface = "webservices.IUserAuthWS")
public class UserAuthWSImplementation implements IUserAuthWS {

	private UserManagerService manager = null;

	@Override
	public boolean saveUser(User user) {
		return getManager().saveUser(user);
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		return getManager().getUserByEmailAndPassword(email, password);
	}

	@Override
	public boolean deleteUser(long id) {
		return getManager().deleteUser(id);
	}

	@Override
	public JWT generateTokenFromEmail(String email) {
		return getManager().generateTokenFromEmail(email);
	}

	@Override
	public User getUserByToken(String token) {
		return getManager().getUserByToken(token);
	}

	private UserManagerService getManager() {
		if (manager == null) {
			manager = new SimpleBusinessFactory().getUserManagerService();
		}
		return manager;
	}

}
