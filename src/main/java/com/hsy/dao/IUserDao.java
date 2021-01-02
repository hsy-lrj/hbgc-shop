package com.hsy.dao;


import com.hsy.domain.User;
import com.hsy.util.Pager;
import com.hsy.util.ShopException;

public interface IUserDao {

	/**
	 * 查询所有用户并分页
	 * 
	 * @param sreach
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<User> find(String search, int page, int size);

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	public User LoadByUserName(String username);
	
	/**
	 * 用户添加
	 * 
	 * @param user
	 * @throws ShopException
	 */
	public void add(User user) ;
	
	public User load(int userId);

	public void delete(int userId);

	/**
	 * 用户名不可更改
	 * @param user
	 */
	public void update(User user);
}
