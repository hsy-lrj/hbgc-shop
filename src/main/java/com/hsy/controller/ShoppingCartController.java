package com.hsy.controller;

import com.hsy.service.IShoppingCartService;
import com.hsy.util.AjaxObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 购物车管理
 */
@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

	@Autowired
	private IShoppingCartService shoppingCartService;

//	@RequestMapping(value = "/pager")
//	@ResponseBody
//	public List<ShoppingCart> find(int userId) {
//		return shoppingCartService.find(userId);
//	}

//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	public AjaxObj add(ShoppingCart shoppingCart) {
//		try {
//			shoppingCartService.add(shoppingCart);
//			return new AjaxObj(200, "添加成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new AjaxObj(0, e.getMessage());
//		}
//	}

//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxObj delete(int shoppingCartId) {
//		try {
//			shoppingCartService.delete(shoppingCartId);
//			return new AjaxObj(200, "删除成功");
//		} catch (Exception e) {
//			return new AjaxObj(0, e.getMessage());
//		}
//	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	// 如果传递的是数组,会在name后面加[] 需要映射一下
	public AjaxObj delete(@RequestParam("ids[]") int[] ids) {

		try {
			String idsStr = "";
			for (int id : ids) {
				shoppingCartService.delete(id);
			}
			return new AjaxObj(200, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}

//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxObj update(ShoppingCart shoppingCart) {
//		try {
//			shoppingCartService.update(shoppingCart);
//			return new AjaxObj(200, "更新成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new AjaxObj(0, e.getMessage());
//		}
//	}
}
