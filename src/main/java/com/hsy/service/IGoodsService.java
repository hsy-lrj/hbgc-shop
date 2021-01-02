package com.hsy.service;


import com.hsy.domain.Goods;
import com.hsy.util.Pager;
import com.hsy.util.ShopException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IGoodsService {

	/**
	 * 根据商品id查询商品的详细内容
	 * @param id
	 * @return
	 */
	public Goods findGoodsById(int id);


	/**
	 * 查询所有商品并分页 如果登陆的是管理员就显示所有商品 如果登陆的是商家,就只显示该商家下所有商品
	 * 
	 * @param search
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<Map<String, Object>> find(int userId, String search, int page, int size);
	/**
	 * 获取所有商品
	 * @param search
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<Map<String, Object>> find(String search, int page, int size);

	public Goods load(int goodsId);

	/**
	 * 添加商品 需要文件上传,所以我们需要一个文件对象
	 * 
	 * @param goods
	 * @throws ShopException
	 */
	public void add(Goods goods,MultipartFile multipartFile) throws ShopException;

	/**
	 * 删除商品需要判断是否有相关订单,如果有就不能删,否则查看订单就会有问题
	 * 	
	 * 	ps : 正常情况应该是上架和下架,不可以删除,但是可以下架,我们这里就不搞那么麻烦了
	 */
	public void delete(int goodsId) throws ShopException;

	public void update(Goods goods);
}
