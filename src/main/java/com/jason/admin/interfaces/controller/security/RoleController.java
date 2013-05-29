package com.jason.admin.interfaces.controller.security;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jason.admin.application.security.AuthorityService;
import com.jason.admin.application.security.RoleService;
import com.jason.admin.domain.security.authority.Authority;
import com.jason.admin.domain.security.role.Role;
import com.jason.framework.domain.EntityUtils;
import com.jason.framework.orm.Page;
import com.jason.framework.orm.hibernate.HibernateHelper;
import com.jason.framework.orm.hibernate.query.HQLQuery;
import com.jason.framework.web.support.ControllerSupport;


@Controller
@RequestMapping(value = "/security/role")
public class RoleController extends ControllerSupport {
	private static final String REDIRECT_LIST = "redirect:/security/role/list";
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;

	/**
	 * role list
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page<Role> page, HttpServletRequest request, Model model) {
		
		HQLQuery query = new HQLQuery().table("Role")
										.like("name", request.getParameter("name"));
		
		page = roleService.queryPage(page, query.hql(), query.values());
		model.addAttribute(page);
		return "security/role/list";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute(new Role()).addAttribute("authorityList", authorityService.query("from Authority"));
		return "security/role/form";
	}

	/**
	 * 
	 * @param entity
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid Role entity, BindingResult result, HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("authorityList", authorityService.query("from Authority"));
			error(model, "创建角色失败，请核对数据!");
			return "security/role/form";
		}

		HibernateHelper.mergeByIds(
									entity.getAuthorities(),
									entity.getAuthorityMap().values(),
									Authority.class
								);
		roleService.store(entity);
		success(redirectAttributes,"创建角色成功！");
		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") String id, Model model) {

		model.addAttribute("_method", "put")
				.addAttribute(roleService.get(id).fillupAuthorityMap())
				.addAttribute("authorityList", authorityService.query("from Authority"));
		return "security/role/form";
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			Role entity = roleService.get(id);
			bind(request, entity);

			HibernateHelper.mergeByIds(
										entity.getAuthorities(),
										entity.getAuthorityMap().values(),
										Authority.class
									);
			roleService.store(entity);
			success(redirectAttributes,"角色修改成功！");
		} catch (Exception e) {
			error(redirectAttributes,"修改角色失败，请核实数据后重新提交！");
		}

		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String delete(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String[] items = EntityUtils.nullSafe(request.getParameterValues("items"), new String[] {});
		for (String item : items) {
			roleService.delete(item);
		}
		success(redirectAttributes,"删除角色成功！");
		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") String id,RedirectAttributes redirectAttributes) {
		roleService.delete(id);
		success(redirectAttributes,"删除角色成功！");
		return REDIRECT_LIST;
	}

}
