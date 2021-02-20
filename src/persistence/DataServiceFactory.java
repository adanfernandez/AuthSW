package persistence;

import persistence.user.UserDataService;

public interface DataServiceFactory {

	public UserDataService getUserDataService();

}
