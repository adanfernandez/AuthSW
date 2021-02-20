package persistence;

import persistence.user.UserDAO;
import persistence.user.UserDataService;

public class SimpleDataServiceFactory implements DataServiceFactory {

	@Override
	public UserDataService getUserDataService() {
		return new UserDAO();
	}

}
