package utils;

import java.util.HashMap;
import java.util.Map;

import bean.UserAccount;
import config.SecurityConfig;

//Truy van du lieu trong Database
public class DataDAO {
	private static final Map<String, UserAccount> mapUsers = new HashMap<String, UserAccount>();
	
	static {
		initUsers();
	}
	
	private static void initUsers() {
		// User này có 1 vai trò là DEVELOPER
		UserAccount emp = new UserAccount("developer", "123", UserAccount.GENDER_MALE, SecurityConfig.ROLE_DEVELOPER);
		// User này có 2 vai trò DEVELOPER và ADMIN
		UserAccount mng = new UserAccount("admin", "123", UserAccount.GENDER_MALE, SecurityConfig.ROLE_DEVELOPER, SecurityConfig.ROLE_ADMIN);
		mapUsers.put(emp.getUserName(), emp);
		mapUsers.put(mng.getUserName(), mng);
	}
	
	public static UserAccount findUser(String userName, String password)  {
		UserAccount u = mapUsers.get(userName);
		if (u != null && u.getPassword().equals(password)) {
			return u;
		}
		return null;
	}
}
