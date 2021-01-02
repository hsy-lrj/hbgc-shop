package com.hsy.dao.impl;

import com.hsy.dao.IOrderDao;
import com.hsy.domain.Order;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单管理
 */
@Repository("orderDao")
public class OrderDao implements IOrderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Order> listByGoodsId(int goodsId) {
        String sql = "select * from t_order where goodsid=?";
        List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
                Order.class), goodsId);
        return orders;
    }

    @Override
    public List<Map<String, Object>> listByConsumeId(int consumeId) {
        String sql = "select * from t_order tor  inner join t_goods tg on tor.goodsId = tg.goodsId  where tor.consumeid = ?";
        List<Map<String, Object>> orders = jdbcTemplate.queryForList(sql, consumeId);
        return orders;
    }

    @Override
    public Pager<Map<String, Object>> find(int userId, String search, int page, int size) {
        Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
        // 查询数据
        // 起始值 页数-1 除以总条数
        int pageOffset = (page - 1) * size;
//		"select tor.*,tu1.username as consumename,tu2.username as shopsname,tu2.nickname,tu2.phone,tg.* from t_order tor inner join t_user tu1 on tu1.userId = tor.consumeid   inner join t_user tu2 on tor.shopsId=tu2.userid  inner join t_goods tg on tor.goodsid = tg.goodsid"
        String sql = "select tor.*,tu1.username as consumename,tu2.username as shopsname,tu2.nickname,tu1.phone,tg.* from t_order tor" +
                " inner join t_user tu1 on tu1.userId = tor.consumeid  " +
                " inner join t_user tu2 on tor.shopsId=tu2.userid " +
                " inner join t_goods tg on tor.goodsid = tg.goodsid" +
                " where tg.goodsname like ? or tu2.username like  ? or tu1.username like  ?  order by tor.orderId asc  limit  ?,?  ";
        if (userId != 0) {
            sql = "select tor.*,tu1.username as consumename,tu2.username as shopsname,tu2.nickname,tu1.phone,tg.* from t_order tor " +
                    "inner join t_user tu1 on tu1.userId = tor.consumeid  " +
                    " inner join t_user tu2 on tor.shopsId=tu2.userid " +
                    " inner join t_goods tg on tor.goodsid = tg.goodsid" +
                    " where (tg.goodsname like ? or tu2.username like  ? or tu1.username like  ? ) and tor.shopsId="
                    + userId + "  order by tor.orderId asc  limit  ?,?  ";
        }
        List<Map<String, Object>> orders = jdbcTemplate.queryForList(sql, new Object[]{search, search, search,
                pageOffset, size});
        // 总条数
        sql = "select count(*) from t_order tor" +
                " inner join t_user tu1 on tu1.userId = tor.consumeid  " +
                " inner join t_user tu2 on tor.shopsId=tu2.userid " +
                " inner join t_goods tg on tor.goodsid = tg.goodsid " +
                "where tg.goodsname like ? or tu2.username like  ? or tu1.username like  ?";
        if (userId != 0) {
            sql = "select  count(*)  from t_order tor " +
                    "inner join t_user tu1 on tu1.userId = tor.consumeid  " +
                    " inner join t_user tu2 on tor.shopsId=tu2.userid " +
                    " inner join t_goods tg on tor.goodsid = tg.goodsid " +
                    "where (tg.goodsname like ? or tu2.username like  ? or tu1.username like  ? ) and tor.shopsId="
                    + userId;
        }

        Integer count = (Integer) jdbcTemplate.queryForObject(sql,
                Integer.class, search, search, search);
        pager.setCount(count);
        pager.setData(orders);
        pager.setCode(0);
        return pager;
    }

    /**
     *添加订单
     */
    @Override
    public void add(Order order) {
        String sql = "insert into t_order (consumeId,shopsId,goodsId,amount,orderDate,price) values (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, order.getConsumeId(), order.getShopsId(), order.getGoodsId(), order.getAmount(), order.getOrderDate(), order.getPrice());
    }

    /**
     *根据订单id查询订单
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Order load(int orderId) {
        String sql = "select * from t_order where orderId = ?";
        // 如果使用queryObject 查询不到就会报错
        List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
                Order.class), orderId);
        if (orders == null || orders.size() <= 0) {
            return null;
        }
        return orders.get(0);
    }

    /**
     *根据订单id删除订单
     */
    @Override
    public void delete(int orderId) {
        String sql = "delete from t_order where orderId = ?";
        jdbcTemplate.update(sql, orderId);
    }

    @Override
    public void update(Order order) {

    }


}
