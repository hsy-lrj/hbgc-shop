package com.hsy.controller;

import com.hsy.domain.Goods;
import com.hsy.domain.ShoppingCart;
import com.hsy.domain.User;
import com.hsy.service.IGoodsService;
import com.hsy.service.IOrderService;
import com.hsy.service.IShoppingCartService;
import com.hsy.service.IUserService;
import com.hsy.util.AjaxObj;
import com.hsy.util.Pager;
import com.hsy.util.ShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 购物车
 */
@Controller
@RequestMapping("/door")
public class DoorController {
	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IShoppingCartService shoppingCartService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrderService orderService;

	/**
	 * 登陆
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj login(User user) {

		try {
			User loginUser = userService.doorLogin(user);
			return new AjaxObj(200, "登录成功", loginUser);
		} catch (ShopException e) {
			return new AjaxObj(0, e.getMessage());
		}
	}

	/**
	 * 所有商品
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/goods/pager")
	@ResponseBody
	public Pager<Map<String, Object>> goodsPager(int page, int limit, String search) {

		if (search == null || search.trim().equals("")) {
			search = "";
		} else {
			search = search.trim();
		}
		return goodsService.find(search, page, limit);
	}

	/**
	 * 根据商品ID 获取商品详情
	 * 
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value = "/goods/show", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj load(int goodsId) {
		Goods goods = goodsService.load(goodsId);
		return new AjaxObj(200, "加载成功", goods);
	}

	/**
	 * 加入购物车
	 * 
	 * @param shoppingCart
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj add(ShoppingCart shoppingCart) {
		try {
			shoppingCartService.add(shoppingCart);
			return new AjaxObj(200, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}

	/**
	 * 购物车列表
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/list", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> list(int userId) {
		return shoppingCartService.list(userId);
	}

	/**
	 * 购物车删除
	 * 
	 * @param shoppingCartId
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj delete(int shoppingCartId) {
		try {
			shoppingCartService.delete(shoppingCartId);
			return new AjaxObj(200, "删除成功");
		} catch (Exception e) {
			return new AjaxObj(0, e.getMessage());
		}
	}

	/**
	 * 购物车结算
	 * 
	 * @param shoppingCartIds
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/accounts", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj accounts(
			@RequestParam("shoppingCartIds") int[] shoppingCartIds) {
		try {
			shoppingCartService.accounts(shoppingCartIds);
			return new AjaxObj(200, "结算成功,已生成订单");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}

	/**
	 * 更改购物车已有商品的数量
	 * 
	 * @param shoppingCart
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/update", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj update(ShoppingCart shoppingCart) {
		try {
			shoppingCartService.update(shoppingCart);
			return new AjaxObj(200, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}

	/**
	 * 根据消费用户ID 获取所有订单列表
	 */
	@RequestMapping(value = "/order/listByUserId", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> listByConsumeId(int userId) {
		return orderService.listByConsumeId(userId);
	}
}
