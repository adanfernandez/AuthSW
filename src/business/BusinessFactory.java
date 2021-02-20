package business;

import business.usermanager.UserManagerService;

public interface BusinessFactory {
	
	public UserManagerService getUserManagerService();
}
