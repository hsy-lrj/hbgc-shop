package com.hsy.controller;

import com.hsy.domain.Logistics;
import com.hsy.domain.User;
import com.hsy.service.ILogisticsService;
import com.hsy.util.AjaxObj;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * 物流管理
 */
@Controller
@RequestMapping("/logistics")
public class LogisticsController {
	@Autowired
	private ILogisticsService logisticsService;

	@RequestMapping("/list")
	public String list() {
		return "logistics/list";
	}

	@RequestMapping(value = "/pager")
	@ResponseBody
	public Pager<Logistics> pager(HttpSession session, int page, int limit,
			String search) throws UnsupportedEncodingException {
		if (search == null || search.trim().equals("")) {
			search = "";
		} else {
			search = search.trim();
		}
		// 获取登陆的用户
		User loginUser = (User) session.getAttribute("loginUser");
		int userId = loginUser.getUserId();
		return logisticsService.find(userId, search, page, limit);
	}



	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj delete(int logisticsId) {
		try {
			logisticsService.delete(logisticsId);
			return new AjaxObj(200, "删除成功");
		} catch (Exception e) {
			return new AjaxObj(0, e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	// 如果传递的是数组,会在name后面加[] 需要映射一下
	public AjaxObj delete(@RequestParam("ids[]") int[] ids) {

		try {
			String idsStr = "";
			for (int id : ids) {
				logisticsService.delete(id);
			}
			return new AjaxObj(200, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}
	
	@RequestMapping("/update")
	public String update(int logisticsId, Model model) {
		Logistics logistics = logisticsService.load(logisticsId);
		model.addAttribute("logistics", logistics);
		return "logistics/update";
	}

	// 更新这里权限不能更改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj update(Logistics logistics) {
		try {
			logisticsService.update(logistics);
			return new AjaxObj(200, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}

	/**
	 *  参数：物流的id
	 */
	@RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj sendSMS(int logisticsId) {
		try {
			logisticsService.sendSMS(logisticsId);
			return new AjaxObj(200, "发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}
}
