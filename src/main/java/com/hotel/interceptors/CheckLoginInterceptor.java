package com.hotel.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("进入了权限拦截器...");
		System.out.println("IP:" + request.getRemoteAddr());
		
		request.setCharacterEncoding("UTF-8");
	 	response.setCharacterEncoding("UTF-8");
	 	response.setContentType("text/html;charset=UTF-8");
	 	
	 	Object obj =request.getSession().getAttribute("thisadministrator");
	 	System.out.println("obj:" + obj);
	 	
//	 	Thread.sleep(6000);
	 	
	 	if (obj == null) {
//		 	request.setAttribute( "intercept", "true" );
	 		//未登陆，返回登陆页面
		 	request.getRequestDispatcher( "/login" ).forward(request,response);
		 	return false;
	 	}
	 	else {
	 		//已登陆，放行请求
	 		return true;
	 	}
	}

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

}
