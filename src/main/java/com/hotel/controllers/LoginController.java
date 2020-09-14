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
	
	@ResponseBody
	@PostMapping("/login_verification")
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
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		LOG.info("logout...");
		session.invalidate();
		return "login";
	}
	
}
