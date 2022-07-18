package filter;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserAccount;
import request.UserRoleRequestWrapper;
import utils.AppUtils;
import utils.SecurityUtils;

//Kiem tra cac request truoc khi cho phep truy cap trang
//Doc cau hinh bao mat o lop SecurityConfig
@WebFilter("/*")
public class SecurityFilter implements Filter {

	/*public SecurityFilter() {

	} 

	@Override
	public void destroy() {

	} */

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String servletPath = request.getServletPath();
		UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession()); // Thông tin người dùng đã được lưu trong Session
		if (servletPath.equals("/login")) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest wrapRequest = request;
		if (loginedUser != null) {
			String userName = loginedUser.getUserName(); // User Name
			List<String> roles = loginedUser.getRoles(); // Các vai trò (Role).
			wrapRequest = new UserRoleRequestWrapper(userName, roles, request); // Gói request cũ bởi một Request mới với các thông tin userName và Roles.
		}
		if (SecurityUtils.isSecurityPage(request)) {   
			// Nếu người dùng chưa đăng nhập,
			// Redirect (chuyển hướng) tới trang đăng nhập.
			if (loginedUser == null) {
				String requestUri = request.getRequestURI();
				int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri); // Lưu trữ trang hiện tại để redirect đến sau khi đăng nhập thành công.
				response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
				return;
			}
			boolean hasPermission = SecurityUtils.hasPermission(wrapRequest); // Kiểm tra người dùng có vai trò hợp lệ hay không?
			if (!hasPermission) {
				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		chain.doFilter(wrapRequest, response);
	}

}
