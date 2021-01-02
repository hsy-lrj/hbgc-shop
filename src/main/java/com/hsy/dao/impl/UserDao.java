package com.hsy.dao.impl;

import com.hsy.dao.IUserDao;
import com.hsy.domain.User;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDao implements IUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 分页查询
	 * 通过like关键词实现模糊查询
	 * 通过limit 实现分页功能
	 * 
	 * 先查询出 用户列表  -- list
	 * 通过count(*)再查询出总条数
	 * 
	 * 将查询结构装填到pager对象中
	 * 
	 * 返回pager对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Pager<User> find(String search, int page, int size) {
		Pager<User> pager = new Pager<User>();
		// 查询数据
		// 起始值 页数-1 除以总条数
		int pageOffset = (page - 1) * size;
		// 通过用户名和昵称以及角色名进行查询
		//查询出 用户列表  -- list
		String sql = "select * from t_user where username like ? or nickname like ? or rolename like ? order by userid asc  limit  ?,?  ";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
				User.class), new Object[] { search, search, search, pageOffset,
				size });
		// 总条数 通过count(*)再查询出总条数
		sql = "select count(*) from t_user where username like ?";
		Integer count = (Integer) jdbcTemplate.queryForObject(sql,
				Integer.class, search);
		//将查询结构装填到pager对象中
		pager.setCount(count);
		pager.setData(users);
		pager.setCode(0);
		return pager;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public User LoadByUserName(String username) {
		String sql = "select * from t_user where username = ?";
		// 如果使用queryObject 查询不到就会报错
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
				User.class), username);
		if (users == null || users.size() <= 0) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public void add(User user) {
		String sql = "insert into t_user (username,password,nickname,rolename,sex,email,phone) values (?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(),
				user.getNickname(), user.getRoleName(), user.getSex(),
				user.getEmail(), user.getPhone());
	}

	/**
	 * 查询用户详情
	 * 返回值为用户对象
	 * 参数userId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public User load(int userId) {
		//1.写sql语句，查询用户
		//2.通过jdbc执行sql语句
		String sql = "select * from t_user where userid = ?";
		// 如果使用queryObject 查询不到就会报错
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
				User.class), userId);
		//判断是否查询出结果
		if (users == null || users.size() <= 0) {
			return null;
		}
		//返回list集合中的第一项
		return users.get(0);
	}

	/**
	 * 根据用户id删除用户
	 */
	@Override
	public void delete(int userId) {
		String sql = "delete from t_user where userid = ?";
		jdbcTemplate.update(sql, userId);
	}

	/**
	 * 更新用户信息
	 */
	@Override
	public void update(User user) {
		String sql = "update t_user set password=? , nickname=?,sex=?,rolename=?,email=?,phone=? where userid=?";
		//执行sql语句
		jdbcTemplate.update(sql, user.getPassword(), user.getNickname(),
				user.getSex(), user.getRoleName(), user.getEmail(),
				user.getPhone(), user.getUserId());
	}

}
