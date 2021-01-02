package com.hsy.dao;

import com.hsy.domain.Goods;
import com.hsy.util.Pager;
import com.hsy.util.ShopException;

import java.util.List;
import java.util.Map;

public interface IGoodsDao {

	/**
	 * 根据商品id查询商品的详细内容
	 * @param id
	 * @return
	 */
	public Goods findGoodsById(int id);

	/**
	 * 根据用户ID查询该用户所有的商品
	 * @param userId
	 * @return
	 */
	public List<Goods> listByUserId(int userId);
	
	/**
	 * 查询指定用户的商品并分页,如果userId是0,就查询所有商品并分页
	 * @param sreach
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<Map<String, Object>> find(int userId,String search, int page, int size);
	/**
	 * 商品添加
	 * @param goods
	 * @throws ShopException
	 */
	public void add(Goods goods) ;
	
	public Goods load(int goodsId);

	public void delete(int goodsId);
	/**
	 * 商品更改 只能更改商品名,价格
	 * @param goods
	 */
	public void update(Goods goods);

}
