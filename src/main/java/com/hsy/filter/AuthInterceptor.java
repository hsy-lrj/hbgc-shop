package com.hsy.filter;

import com.hsy.domain.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 判断登陆,未登录不能访问其他页面
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 解决跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

		// 判断当前请求是否是登陆页面,是就直接放行
		// 获取当前请求的路由
		String servletPath = request.getServletPath();
		// 如果不是登陆页面,就判断 登陆,是登陆页面或者注册就直接放行 退出也放行
		// 还需要放行 door 请求相关,因为门户会校验登陆
		if (!servletPath.equals("/") && !servletPath.equals("/login")
				&& !servletPath.equals("/register")
				&& !servletPath.equals("/logout")
				&& !servletPath.startsWith("/door/")) {
			// 类型转换
			HandlerMethod handlerMethod = null;

			// 判断 handler是否由 HandlerMethod实例化而来,避免强制类型转换错误
			if (!(handler instanceof HandlerMethod)) {
				// 如果请求的都不是我们的方法,就直接放行,比如html,资源文件,或者是底层方法等
				return true;
			}
			handlerMethod = (HandlerMethod) handler;
			// 获得session
			HttpSession session = request.getSession(true);

			// 获得登陆的用户对象
			User user = (User) session.getAttribute("loginUser");
			// 判断是否登录
			if (user == null) {
				// request.getContextPath() 获取当前项目上下文环境 /根路径 这里是
				response.sendRedirect(request.getContextPath() + "/");
				// 不放行
				return false;
			}
		}
		// 放行
		return super.preHandle(request, response, handler);
	}
}
