package com.hsy.service.impl;

import com.hsy.dao.IGoodsDao;
import com.hsy.dao.IUserDao;
import com.hsy.domain.User;
import com.hsy.service.IUserService;
import com.hsy.util.Pager;
import com.hsy.util.ShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IGoodsDao goodsDao;

	// -----------------------------
	public User login(User user) throws ShopException {
		User loginUser = userDao.LoadByUserName(user.getUsername());
		if (loginUser == null) {
			throw new ShopException("用户名不存在");
		} else if (!loginUser.getPassword().equals(user.getPassword())) {
				throw new ShopException("密码不正确");
		}else if(loginUser.getRoleName().equals("普通用户")){
			throw new ShopException("您没有权限登陆后台管理系统");
		}
		return loginUser;
	}
	public User doorLogin(User user) throws ShopException {
		User loginUser = userDao.LoadByUserName(user.getUsername());
		if (loginUser == null) {
			throw new ShopException("用户名不存在");
		} else if (!loginUser.getPassword().equals(user.getPassword())) {
				throw new ShopException("密码不正确");
		}
		return loginUser;
	}
	
	/**
	 * 分页查询
	 * 1.吧查询条件前后加上%
	 */
	@Override
	public Pager<User> find(String search, int page, int size) {
		//查询条件前后加上%
		search = "%" + search + "%";
		return userDao.find(search, page, size);
	}

	@Override
	public void register(User user) throws ShopException {
		// 注册是针对普通用户,并且和add方法非常类似,不过注册的账号权限就是普通用户
		//
		// 设置为普通用户
		user.setRoleName("普通用户");
		this.add(user);
	}

	/**
	 * 查询某用户的详情
	 */
	@Override
	public User load(int userId) {
		//调用dao中的方法
		return userDao.load(userId);
	}

	@Override
	public void add(User user) throws ShopException {
		// 根据用户名查询,如果用户名存在 就抛异常
		User oldUser = userDao.LoadByUserName(user.getUsername());
		if (oldUser != null) {
			throw new ShopException("用户名已存在");
		}
		userDao.add(user);
	}

	/**
	 * 删除用户信息
	 */
	@Override
	public void delete(int userId) throws ShopException {
		// todo 判断该用户下是否还有商品
//		List<Goods> goodses = goodsDao.listByUserId(userId);
//		if (goodses != null && goodses.size() > 0) {
//			throw new ShopException("该用户 : "+userId+" 下还有商品在售,请先删除商品");
//		}
		//调用dao层的方法
		userDao.delete(userId);
	}

	/**
	 * 编辑用户信息
	 */
	@Override
	public void update(User user) {
		//调用dao层方法
		userDao.update(user);
	}


}
