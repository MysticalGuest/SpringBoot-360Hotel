package com.hotel.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
	Spring拦截器
	HandlerInterceptorAdapter需要继承，HandlerInterceptor需要实现
	可以作为日志记录和登录校验来使用
	建议使用HandlerInterceptorAdapter，因为可以按需进行方法的覆盖
*/
@Component
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 	preHandle：拦截于请求刚进入时，进行判断，需要boolean返回值，如果返回true将继续执行，如果返回false，将不进行执行。
	 	一般用于登录校验
	*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("进入了权限拦截器...");
		System.out.println("IP:" + request.getRemoteAddr());
		
		request.setCharacterEncoding("UTF-8");
	 	response.setCharacterEncoding("UTF-8");
	 	response.setContentType("text/html;charset=UTF-8");
	 	
	 	Object obj =request.getSession().getAttribute("thisadministrator");
	 	System.out.println("obj:" + obj);
	 	
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

//	// 拦截于方法成功返回后，视图渲染前，可以进行成功返回的日志记录
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		super.afterCompletion(request, response, handler, ex);
//	}
//
//	// 拦截于方法成功返回后，视图渲染前，可以对modelAndView进行操作
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		super.postHandle(request, response, handler, modelAndView);
//	}

}
