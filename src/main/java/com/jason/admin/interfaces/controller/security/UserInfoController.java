package com.jason.admin.interfaces.controller.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jason.admin.application.security.RoleService;
import com.jason.admin.application.security.UserInfoService;
import com.jason.framework.domain.EntityUtils;
import com.jason.framework.orm.Page;
import com.jason.framework.orm.hibernate.HibernateHelper;
import com.jason.framework.orm.hibernate.query.HQLQuery;
import com.jason.framework.util.MD5Utils;
import com.jason.framework.web.support.ControllerSupport;
import com.jason.security.model.Role;
import com.jason.security.model.UserInfo;


/**
 * 
 * 用户管理的Controller, 使用Restful风格的Urls:
 * 
 * List   page        : GET  	/user/list
 * Create page        : GET  	/user/create
 * Create action      : POST 	/user/create
 * Update page        : GET  	/user/edit/{id}
 * Update action      : PUT 	/user/edit/{id}
 * Delete action      : DELETE 	/user/delete/{id}
 * Delete many action : DELETE 	/user/delete
 * 
 * @author Jason
 *
 */
@Controller
@RequestMapping(value = "/security/user")
public class UserInfoController extends ControllerSupport {
	
	private static final String REDIRECT_LIST = "redirect:/security/user/list";
	private static final String FORM = "security/user/form";
	private static final String LIST = "security/user/list";
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RoleService roleService;

	/**
	 * user list
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page<UserInfo> page, HttpServletRequest request, Model model) {

		HQLQuery query = null;
		if (StringUtils.isNotBlank(request.getParameter("roleId"))) {
			query = new HQLQuery().table("select u from UserInfo u join u.roles role")
									.like("username", request.getParameter("name"))
									.eq("role.id", request.getParameter("roleId"))
									.orderBy("u.username");
		} else {
			query = new HQLQuery().table("UserInfo")
									.like("username", request.getParameter("name"))
									.orderBy("username");
		}
		page = userInfoService.queryPage(page, query.hql(), query.values());
		model.addAttribute(page).addAttribute("roleList", roleService.query("from Role"));
		return 	LIST;
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute(new UserInfo()).addAttribute("roleList", roleService.query("from Role"));
		return FORM;
	}

	/**
	 * @param entity
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid UserInfo entity, BindingResult result, HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("roleList", roleService.query("from Role"));
			error(model, "创建用户失败，请核对数据!");
			return FORM;
		}
		HibernateHelper.mergeByIds(
									entity.getRoles(),
									entity.getRoleMap().values(),
									Role.class
								);
		entity.encodePassword(MD5Utils.encode(entity.getPassword()));
		Date now = new Date();
		entity.setCreatedAt(now).setUpdatedAt(now);
		userInfoService.store(entity);
		
		success(redirectAttributes,"创建用户成功！"); 
		return REDIRECT_LIST;
	}

	/**
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model model) {

		model.addAttribute("_method", "put")
				.addAttribute(userInfoService.get(id).fillupRoleMap())
				.addAttribute("roleList", roleService.query("from Role"));
		return FORM;
	}

	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	public String edit(@PathVariable("id") Long id, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			UserInfo entity = userInfoService.get(id);

			String origUsername = entity.getUsername();
			String origPassword = entity.getPassword();
			bind(request, entity);
			/* prevent malicious request,if username has modify,throw exception */
			if (usernameHasModified(origUsername, entity.getUsername())) {
				throw new UnsupportedOperationException("Cannot modify the username!");
			}
			HibernateHelper.mergeByIds(
										entity.getRoles(),
										entity.getRoleMap().values(),
										Role.class
									);
			/* user has modified password*/
			if (passwordHasModified(origPassword, entity.getPassword())) {
				entity.encodePassword(MD5Utils.encode(entity.getPassword()));
			} else { 
				/* user didnot want to modify password*/
				entity.setPassword(origPassword);
			}
			entity.setUpdatedAt(new Date());
			userInfoService.store(entity);
			success(redirectAttributes,"用户修改成功！");
		} catch (Exception e) {
			this.getLogger().error("修改用户出错！", e);
			error(redirectAttributes,"修改用户失败，请核实数据后重新提交！");
		}
		return REDIRECT_LIST;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String delete(HttpServletRequest request,RedirectAttributes redirectAttributes) {

		String[] items = EntityUtils.nullSafe(request.getParameterValues("items"), new String[] {});
		for (String item : items) {
			userInfoService.delete(Long.valueOf(item));
		}
		success(redirectAttributes,"删除用户成功！");
		return REDIRECT_LIST;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,RedirectAttributes redirectAttributes) {
		userInfoService.delete(id);
		success(redirectAttributes,"删除用户成功！");
		return REDIRECT_LIST;
	}
	
	/**
	 * username  is modified
	 * @param origUsername
	 * @param newUsername
	 * @return
	 */
	private boolean usernameHasModified(String origUsername, String newUsername) {
		return !StringUtils.equals(origUsername, newUsername);
	}
	
	/**
	 * password is modified
	 * 
	 * @param origPassword
	 * @param newPassword
	 * @return
	 */
	private boolean passwordHasModified(String origPassword, String newPassword) {
		return StringUtils.isNotBlank(newPassword) && !StringUtils.equals(origPassword, newPassword);
	}

}
