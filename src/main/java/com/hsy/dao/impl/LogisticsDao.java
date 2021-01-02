package com.hsy.dao.impl;


import com.hsy.dao.ILogisticsDao;
import com.hsy.domain.Logistics;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 物流管理
 */
@Repository("logisticsDao")
public class LogisticsDao implements ILogisticsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void deleteByOrderId(int orderId) {
		String sql = "delete from t_logistics where orderId = ?";
		jdbcTemplate.update(sql, orderId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Pager<Logistics> find(int userId, String search, int page, int size) {
		Pager<Logistics> pager = new Pager<Logistics>();
		// 查询数据
		// 起始值 页数-1 除以总条数
		int pageOffset = (page - 1) * size;
		String sql = "select * from t_logistics where orderId like ? or status like ?   order by logisticsId asc  limit  ?,?  ";
		if (userId != 0) {
			sql = "select tl.* from t_logistics tl inner join t_order tor on (tl.orderId = tor.orderId and tor.shopsId = "+userId+")  where tl.orderId like ? or tl.status like ?   order by tl.logisticsId asc  limit  ?,?  ";
		}
		List<Logistics> logistics = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
				Logistics.class), new Object[] { search, search,
				pageOffset, size });
		// 总条数
		sql = "select count(*) from t_logistics where orderId like ? or status like ?    ";
		if (userId != 0) {
			sql = "select count(*)  from t_logistics tl inner join t_order tor on (tl.orderId = tor.orderId and tor.shopsId =  "+userId+")  where tl.orderId like ? or tl.status like ?   ";
		}
		Integer count = (Integer) jdbcTemplate.queryForObject(sql,
				Integer.class, search,search);
		pager.setCount(count);
		pager.setData(logistics);
		pager.setCode(0);
		return pager;
	}

	@Override
	public void add(Logistics logistics) {
		String sql = "insert into t_logistics (orderId,status) values (?,?)";
		jdbcTemplate.update(sql, logistics.getOrderId(),logistics.getStatus());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Logistics loadByOrderId(int orderId) {
		String sql = "select * from t_logistics where orderId = ?";
		// 如果使用queryObject 查询不到就会报错
		List<Logistics> logistics = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
				Logistics.class), orderId);
		if (logistics == null || logistics.size() <= 0) {
			return null;
		}
		return logistics.get(0);
	}

	@Override
	public void delete(int logisticsId) {
		String sql = "delete from t_logistics where logisticsId = ?";
		jdbcTemplate.update(sql, logisticsId);
	}

	@Override
	public void update(Logistics logistics) {
		String sql = "update t_logistics set status=? where logisticsId=?";
		jdbcTemplate.update(sql, logistics.getStatus(),logistics.getLogisticsId());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Logistics load(int logisticsId) {
		String sql = "select * from t_logistics where logisticsId = ?";
		// 如果使用queryObject 查询不到就会报错
		List<Logistics> logistics = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
				Logistics.class), logisticsId);
		if (logistics == null || logistics.size() <= 0) {
			return null;
		}
		return logistics.get(0);
	}
}
