package config;
import java.util.*;

//Cau hinh ROLE voi cac user duoc phep truy cap
//Cau hinh bao mat 
public class SecurityConfig {
	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_DEVELOPER = "DEVELOPER";

	// String: Role
	// List<String>: urlPatterns
	private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

	static {
		init();
	}

	private static void init() {
		List<String> urlPatterns1 = new ArrayList<String>();
		urlPatterns1.add("/userInfo");
		urlPatterns1.add("/developerTask");
		mapConfig.put(ROLE_DEVELOPER, urlPatterns1);

		List<String> urlPatterns2 = new ArrayList<String>();
		urlPatterns2.add("/userInfo");
		urlPatterns2.add("/adminTask");
		mapConfig.put(ROLE_ADMIN, urlPatterns2);
	}

	public static Set<String> getAllAppRoles() {
		return mapConfig.keySet();
	}

	public static List<String> getUrlPatternsForRole(String role) {
		return mapConfig.get(role);
	}
}
