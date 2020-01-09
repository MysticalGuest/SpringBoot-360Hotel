package com.hotel.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("进入了权限拦截器。。。");
		System.out.println("IP:" + request.getRemoteAddr());
		/*
		 * request.setCharacterEncoding("UTF-8");
		 * response.setCharacterEncoding("UTF-8");
		 * response.setContentType("text/html;charset=UTF-8");
		 * System.out.println("IP:" + request.getRemoteAddr()); Object obj =
		 * request.getSession().getAttribute("user"); if (obj == null) {
		 * PrintWriter out = response.getWriter(); out.print("<script>");
		 * out.print("alert('您还没有登录！！！');");
		 * out.print("location.href='../login.jsp';"); out.print("</script>");
		 * return false; } else { return true; }
		 */
		return true;
	}

}
