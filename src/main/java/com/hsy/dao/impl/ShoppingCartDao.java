package com.hsy.dao.impl;

import com.hsy.dao.IShoppingCartDao;
import com.hsy.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * 购物车管理
 */
@Repository("shoppingCartDao")
public class ShoppingCartDao implements IShoppingCartDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> list(int userId) {
		String sql = "select * from t_shoppingCart ts inner join t_goods tg on tg.goodsId = ts.goodsId where ts.userId = ?";
		// 如果使用queryObject 查询不到就会报错
		List<Map<String, Object>> shoppingCarts = jdbcTemplate.queryForList(sql, userId);
		return shoppingCarts;
	}

	@Override
	public void add(ShoppingCart shoppingCart) {
		String sql = "insert into t_shoppingCart (userid,goodsId,amount) values (?,?,?)";
		jdbcTemplate.update(sql, shoppingCart.getUserId(),
				shoppingCart.getGoodsId(), shoppingCart.getAmount());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ShoppingCart load(int userId, int goodsId) {
		String sql = "select * from t_shoppingCart where userId = ? and goodsId = ? ";
		// 如果使用queryObject 查询不到就会报错
		List<ShoppingCart> shoppingCarts = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(ShoppingCart.class), userId, goodsId);
		if (shoppingCarts == null || shoppingCarts.size() <= 0) {
			return null;
		}
		return shoppingCarts.get(0);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ShoppingCart load(int shoppingCartId) {
		String sql = "select * from t_shoppingCart where shoppingCartId = ?  ";
		// 如果使用queryObject 查询不到就会报错
		List<ShoppingCart> shoppingCarts = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(ShoppingCart.class), shoppingCartId);
		if (shoppingCarts == null || shoppingCarts.size() <= 0) {
			return null;
		}
		return shoppingCarts.get(0);
	}
	@Override
	public void delete(int shoppingCartId) {
		String sql = "delete from t_shoppingCart where shoppingCartId = ?";
		jdbcTemplate.update(sql, shoppingCartId);
	}

	@Override
	public void update(ShoppingCart shoppingCart) {
		String sql = "update t_shoppingCart set amount=?  where shoppingCartId=?";
		jdbcTemplate.update(sql, shoppingCart.getAmount(),
				shoppingCart.getShoppingCartId());
	}



}
