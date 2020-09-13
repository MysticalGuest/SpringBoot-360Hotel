package com.hotel.controllers;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.entity.Administrator;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.hotel.controllers.AdministratorController;
import com.hotel.serviceimpl.AdministratorServiceImpl;

@Controller
@RequestMapping("")
public class LoginController {
	private static final Logger LOG = Logger.getLogger(AdministratorController.class);
	@Autowired
	private AdministratorServiceImpl administratorService;
	
	@GetMapping("/login")
	public String login() {
		LOG.info("login...");
		return "login";
	}
	
	@PostMapping("/login_verification")
	@ResponseBody
	public boolean index_new(
			@RequestParam(value = "AdmId") String AdmId, 
			@RequestParam(value = "aPassword") String aPassword,
			@RequestParam(value = "limit", required = false) String check) {
		
		System.out.println(AdmId);
		System.out.println(aPassword);
		System.out.println(check);
		Administrator administrator;
		if (check.equals("front")) {
			LOG.info("前台登录...");
			administrator = new Administrator(AdmId, aPassword, "front");
			Administrator thisadministrator = administratorService.login(administrator);
			if (thisadministrator != null) {
				return true;
			} else {
				return false;
			}
		} else {
			administrator = new Administrator(AdmId, aPassword, "administrator");
			Administrator thisadministrator = administratorService.login(administrator);
			if (thisadministrator != null) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	@PostMapping("/passForm")
	public String passForm(
			@RequestParam(value = "AdmId") String AdmId, 
			@RequestParam(value = "aPassword") String aPassword,
			@RequestParam(value = "limit", required = false) String limit,
			HttpSession session) {
		LOG.info("Verified...");
		Administrator administrator = new Administrator(AdmId, aPassword, limit);
		Administrator thisadministrator = administratorService.login(administrator);
		session.setAttribute("thisadministrator", thisadministrator);
		return "redirect:/" + thisadministrator.getlimit() + (thisadministrator.getlimit().equals("front")? "/Home": "/HomeForAdm");
//		if (limit.equals("front")) {
//			return "redirect:/front/Home";
//		} else {
//			return "redirect:/administrator/HomeForAdm";
//		}
		
		
	}
	
	//登录界面
//	@RequestMapping(value="/login",method = RequestMethod.POST)
//	public String login(Administrator administrator,@Param("limits") String limits,HttpServletRequest req,HttpSession session) throws IOException {
//		Administrator thisadministrator = administratorService.login(administrator);
//		if (thisadministrator != null) {
//			System.out.println("登录界面");
//			if(thisadministrator.getlimit().equals(limits)&&limits.equals("front")){
//				session.setAttribute("thisadministrator", thisadministrator);
//				System.out.println(limits);
//				System.out.println("前台界面");
//				return "redirect:/front/Home";
//			}
//			else if(thisadministrator.getlimit().equals(limits)&&limits.equals("administrator")){
//				session.setAttribute("thisadministrator", thisadministrator);
//				System.out.println("管理员界面");
//				return "redirect:/administrator/HomeForAdm";
//			}
//			else{
//				System.out.println("用户名或密码错误!1");
//				return "redirect:login";
//			}
//			
//		}
//		else{
//			System.out.println("用户名或密码错误!2");
//			return "redirect:login";
//		}
//		
//	}
//	
//	//登录界面
//	@RequestMapping(value="/loginVerification",method = RequestMethod.POST)
//	public String loginVerification(Administrator administrator,@Param("limits") String limits,HttpServletRequest req,HttpServletResponse response, HttpSession session) throws IOException {
//		String Id = req.getParameter("Id");
//		System.out.println("Id:"+Id);
//		String password = req.getParameter("password");
//		System.out.println("password:"+password);
//		administrator.setAdmId(Id);
//		administrator.setaPassword(password);
//		Administrator thisadministrator = administratorService.login(administrator);
//		//如果在数据库找到有这个Id和password对应的人,但在对比权限是错误,就说明权限错误!
//		if (thisadministrator != null) {
//			System.out.println("登录界面");
//			if(thisadministrator.getlimit().equals(limits)&&limits.equals("front")){
//				return null;
//			}
//			else if(thisadministrator.getlimit().equals(limits)&&limits.equals("administrator")){
//				return null;
//			}
//			else{
//				System.out.println("权限错误!");
//				response.getWriter().print("errorOfLimit");
//				return null;
//			}
//			
//		}
//		else{
//			System.out.println("用户名或密码错误!");
//			response.getWriter().print("errorOfEmpty");
//			return null;
//		}
//		
//	}
	
	//登录
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String login() {
//		LOG.info("login...");
//		return "login";
//	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		LOG.info("logout...");
		session.invalidate();
		return "login";
	}
	
}
