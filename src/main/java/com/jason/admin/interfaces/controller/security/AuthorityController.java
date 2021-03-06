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
import com.jason.framework.domain.EntityUtils;
import com.jason.framework.orm.Page;
import com.jason.framework.orm.hibernate.query.HQLQuery;
import com.jason.framework.orm.hibernate.query.HQLQuery.MatchType;
import com.jason.framework.web.support.ControllerSupport;
import com.jason.security.model.Authority;


@Controller
@RequestMapping(value = "/security/authority")
public class AuthorityController extends ControllerSupport {
	
	private static final String REDIRECT_LIST = "redirect:/security/authority/list";
	private static final String FORM = "security/authority/form";
	private static final String LIST = "security/authority/list";
	
	@Autowired
	private AuthorityService authorityService;
	
	/**
	 * @param page
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page<Authority> page, HttpServletRequest request, Model model) {

		HQLQuery query = new HQLQuery().table("Authority")
										.or(
												new String[] { "name", "permission" },
												new MatchType[] { MatchType.LIKE, MatchType.LIKE },
												new Object[] { request.getParameter("name"), request.getParameter("name") }
										).orderBy(" id desc ");

		page = authorityService.queryPage(page, query.hql(), query.values());
		model.addAttribute(page);
		
		return LIST;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute(new Authority());
		return FORM;
	}

	/**
	 * @param entity
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid Authority entity, BindingResult result,Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			error(model, "创建权限失败，请核对数据!");
			return FORM;
		}

		authorityService.store(entity);
		success(redirectAttributes,"创建权限成功！");
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
				.addAttribute(authorityService.get(id));
		return FORM;
	}

	/**
	 * @param id
	 * @param entity
	 * @param result
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	public String edit(@PathVariable("id") String id,@Valid Authority entity, BindingResult result,
			HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		
		try {
			if (result.hasErrors()) {
				error(model, "修改权限失败，请核实数据后重新提交！");
				model.addAttribute("_method", "put");
				return FORM;
				
			}
			
			authorityService.store(entity);
			success(redirectAttributes,"权限修改成功！");
		} catch (Exception e) {
			this.getLogger().error("修改权限失败，请核实数据后重新提交！",e);
			error(redirectAttributes,"修改权限失败，请核实数据后重新提交！");
		}
		return REDIRECT_LIST;
	}

	/**
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String delete(HttpServletRequest request,RedirectAttributes redirectAttributes) {

		String[] items = EntityUtils.nullSafe(request.getParameterValues("items"), new String[] {});

		for (String item : items) {
			authorityService.delete(item);
		}
		success(redirectAttributes,"删除权限成功！");
		return REDIRECT_LIST;
	}

	/**
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") String id,RedirectAttributes redirectAttributes) {
		authorityService.delete(id);
		success(redirectAttributes,"删除权限成功！");
		return REDIRECT_LIST;
	}

}
