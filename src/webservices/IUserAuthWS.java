package webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import model.JWT;
import model.User;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface IUserAuthWS {

	@WebMethod
	public boolean saveUser(User user);

	@WebMethod
	public User getUserByEmailAndPassword(String email, String password);

	@WebMethod
	public boolean deleteUser(long id);
	
	@WebMethod
	public JWT generateTokenFromEmail(String email);
	
	@WebMethod
	public User getUserByToken(String token);

}

