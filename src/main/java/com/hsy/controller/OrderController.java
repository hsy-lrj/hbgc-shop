package com.hsy.controller;

import com.hsy.domain.Order;
import com.hsy.domain.User;
import com.hsy.service.ILogisticsService;
import com.hsy.service.IOrderService;
import com.hsy.service.IUserService;
import com.hsy.util.AjaxObj;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 订单管理
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ILogisticsService logisticsService;

	@RequestMapping("/list")
	public String list() {
		return "order/list";
	}

	@RequestMapping(value = "/pager")
	@ResponseBody
	public Pager<Map<String, Object>> pager(HttpSession session, int page, int limit,
			String search) throws UnsupportedEncodingException {
		if (search == null || search.trim().equals("")) {
			search = "";
		} else {
			search = search.trim();
		}
		// 获取登陆的用户
		User loginUser = (User) session.getAttribute("loginUser");
		int userId = loginUser.getUserId();
		return orderService.find(userId, search, page, limit);
	}

	/**
	 * 后台管理中,设置不可以添加订单,只有门户中下单才可以
	 *
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Order order) {
		try {
			orderService.add(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/order/list";
	}

	/**
	 * 根据消费用户ID 获取所有订单列表
	 */
//	@RequestMapping(value = "/listByUserId", method = RequestMethod.POST)
//	@ResponseBody
//	public List<Map<String, Object>> listByConsumeId(int userId) {
//		return orderService.listByConsumeId(userId);
//	}

	@RequestMapping(value = "/addLogistics", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj addLogistics(int orderId) {
		try {
			logisticsService.add(orderId);
			return new AjaxObj(200, "物流信息添加成功");
		} catch (Exception e) {
			return new AjaxObj(0, e.getMessage());
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj delete(int orderId) {
		try {
			orderService.delete(orderId);
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
				orderService.delete(id);
			}
			return new AjaxObj(200, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}
}
