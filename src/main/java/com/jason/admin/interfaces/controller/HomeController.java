package com.jason.admin.interfaces.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jason.framework.web.exception.InvalidCaptchaException;




/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	public static final int UNKNOWN_ACCOUT_ERROR_CODE = 1;
	public static final int LOCKED_ACCOUT_ERROR_CODE = 2;
	public static final int AUTHENTICATION_ERROR_CODE = 4;
	public static final int INVALID_CAPTCHA_ERROR_CODE = 8;
	public static final int OTHER_ERROR_CODE = 16;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/admin/dashboard/", method=RequestMethod.GET)
	public String admin() {
		return "admin/index";
	}
	
	@RequestMapping(value = "/admin/deny/")
	public String deny() {
		return "admin/deny";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = POST)
	public String login(HttpServletRequest request, HttpSession session) {

		logger.debug(String.format(
									"Handle login request with session[id=%s,createOn=%s,lastAccessedOn=%s]", 
									session.getId(),
									session.getCreationTime(),
									session.getLastAccessedTime()
								)
		);
		
		Subject subject = SecurityUtils.getSubject();

		// if it has authenticated,logout first
		if (subject.isAuthenticated()) {
			subject.logout();
		}

		try {
			AuthenticationToken token = createToken(request);
			subject.login(token);
		} catch (Exception e) {
			logger.error("login occur exception.", e);
			return "redirect:/login?code=" + translateException(e);
		}
		return "redirect:/admin/dashboard/";
	}
	@RequestMapping(value = "/admin/logout/", method = { GET, POST })
	public String logout() {

		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login";
	}
	
	private int translateException(Exception e) {
		if (e instanceof InvalidCaptchaException) {
			return INVALID_CAPTCHA_ERROR_CODE;
		}
		if (e instanceof UnknownAccountException) {
			return UNKNOWN_ACCOUT_ERROR_CODE;
		}
		if (e instanceof LockedAccountException) {
			return LOCKED_ACCOUT_ERROR_CODE;
		}
		if (e instanceof AuthenticationException) {
			return AUTHENTICATION_ERROR_CODE;
		}
		return OTHER_ERROR_CODE;
	}


	private AuthenticationToken createToken(HttpServletRequest request) {
		String username = WebUtils.getCleanParam(request, "username");
		String password = WebUtils.getCleanParam(request, "password");

		//String passwordAsMd5 = MD5HashUtils.asMD5(password, username);
		String rememberMeAsString = WebUtils.getCleanParam(request, "rememberMe");
		boolean rememberMe = false;
		if (null != rememberMeAsString) {
			rememberMe = Boolean.valueOf(rememberMeAsString);
		}
		String host = request.getRemoteHost();
		return new UsernamePasswordToken(username, password, rememberMe, host);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		return "login";
	}

}

