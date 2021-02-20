package business;

import business.usermanager.UserManager;
import business.usermanager.UserManagerService;

public class SimpleBusinessFactory implements BusinessFactory {

	@Override
	public UserManagerService getUserManagerService() {
		return new UserManager();
	}

}
