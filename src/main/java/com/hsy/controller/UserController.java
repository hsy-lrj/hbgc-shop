package com.hsy.controller;

import com.hsy.domain.User;
import com.hsy.service.IUserService;
import com.hsy.util.AjaxObj;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;

	@RequestMapping("/list")
	public String list() {
		return "user/list";
	}

	/**
	 * 分页查询
	 * a.应该有哪些接口
			1.页面加载时的分页查询
			2.根据用户名或昵称或角色分页查询
			可以就是一个接口
		b.参数：页码，每页的容量，查询条件（若没有查询条件）
		c.返回值：数组或者list，每一个元素都是一个user对象
	 * @param page 页码
	 * @param limit 每页的容量
	 * @param search 查询条件
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/pager")
	@ResponseBody
	public Pager<User> pager(int page, int limit, String search)
			throws UnsupportedEncodingException {
		//判断search 查询条件是否为空
		if (search == null || search.trim().equals("")) {
			search = "";
		} else {
			//若不为空时，去掉查询条件中的空格
			search = search.trim();
		}
		return userService.find(search, page, limit);
	}

	//访问页面资源
	@RequestMapping("/add")
	public String add(Model model) {
		return "user/add";
	}

	/**
	 * 添加用户接口
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj add(User user) {
		try {
			userService.add(user);
			return new AjaxObj(200, "添加成功");
		} catch (Exception e) {
			return new AjaxObj(0, e.getMessage());
		}
	}

	/**
	 * 访问用户修改的页面资源
	 * @param userId 获取用户的id，可以确定是哪个用户
	 * @param model 用来返回查询出的用户信息
	 * @return
	 */
	@RequestMapping("/update")
	public String update(int userId, Model model) {
		//1.根据用户id，查询出用户详情
		User user = userService.load(userId);
		model.addAttribute("user", user);
		//2.返回页面资源
		return "user/update";
	}

	/**
	 * 编辑用户
	 * @param user
	 * @return
	 */
	// 更新这里权限不能更改
	//value定义接口路径，method定义请求方式
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	//定义参数和返回值，参数就是一个user对象
	public AjaxObj update(User user) {
		try {
			//调用service层的方法
			userService.update(user);
			return new AjaxObj(200, "更新成功");
		} catch (Exception e) {
			//打印异常信息
			e.printStackTrace();
			//返回执行结果
			return new AjaxObj(0, e.getMessage());
		}
	}
	
	/**
	 * 单个删除
	 * @param userId
	 * @return
	 */
	//定义路径和请求方式
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxObj delete(int userId) {
		try {
			//调用delete方法
			userService.delete(userId);
			return new AjaxObj(200, "删除成功");
		} catch (Exception e) {
			return new AjaxObj(0, e.getMessage());
		}
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	//忽略掉某些报警信息
	@SuppressWarnings("unused")
	//定义路径和请求方式
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	// 如果传递的是数组,会在name后面加[] 需要映射一下
	// 通过数组获取多个用户id
	// @RequestParam 用来定义请求时传递的参数
	public AjaxObj delete(@RequestParam("ids[]") int[] ids) {

		try {
			String idsStr = "";
			//通过for循环遍历id数组，一个一个的删除
			for (int id : ids) {
				userService.delete(id);
			}
			return new AjaxObj(200, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxObj(0, e.getMessage());
		}
	}

}
