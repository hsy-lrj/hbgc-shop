package com.hsy.dao.impl;

import com.hsy.dao.IGoodsDao;
import com.hsy.domain.Goods;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 商品管理
 */
@Repository("goodsDao")
public class GoodsDao implements IGoodsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Goods findGoodsById(int id) {
        String sql = "select * from t_goods where goodsid = ?";
        List<Goods> goodsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Goods.class), id);
        return goodsList.get(0);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Goods> listByUserId(int userId) {
        String sql = "select * from t_goods where userid=?";
        List<Goods> goodses = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper(Goods.class), userId);
        return goodses;
    }

    @Override
    public Pager<Map<String, Object>> find(int userId, String search, int page, int size) {
        Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
        // 查询数据
        // 起始值 页数-1 除以总条数
        int pageOffset = (page - 1) * size;
        String sql = "select * from t_goods tg inner join t_user tu on tu.userId = tg.userId where tg.goodsname like ?  order by tg.goodsid asc  limit  ?,?  ";
        //判断userid值的可取性
        if (userId != 0) {
            sql = "select * from t_goods tg inner join t_user tu on tu.userId = tg.userId  where tg.goodsname like ? and tg.userid=" + userId + "  order by tg.goodsid asc  limit  ?,?  ";
        }
        List<Map<String, Object>> goodses = jdbcTemplate.queryForList(sql, new Object[]{search,
                pageOffset, size});
        // 总条数
        sql = "select count(*) from t_goods tg inner join t_user tu on tu.userId = tg.userId where tg.goodsname like ?   ";
        if (userId != 0) {
            sql = "select count(*) from t_goods tg inner join t_user tu on tu.userId = tg.userId  where tg.goodsname like ? and tg.userid=" + userId;
        }
        Integer count = (Integer) jdbcTemplate.queryForObject(sql,
                Integer.class, search);
        pager.setCount(count);
        pager.setData(goodses);
        pager.setCode(0);
        return pager;
    }

    @Override
    public void add(Goods goods) {
        String sql = "insert into t_goods (userid,goodsname,price,imgurl,`desc`) values (?,?,?,?,?)";
        jdbcTemplate.update(sql, goods.getUserId(), goods.getGoodsName(), goods.getPrice(), goods.getImgUrl(), goods.getDesc());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Goods load(int goodsId) {
        String sql = "select * from t_goods where goodsid = ?";
        // 如果使用queryObject 查询不到就会报错
        List<Goods> goodses = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
                Goods.class), goodsId);
        if (goodses == null || goodses.size() <= 0) {
            return null;
        }
        return goodses.get(0);
    }

    @Override
    public void delete(int goodsId) {
        String sql = "delete from t_goods where goodsid = ?";
        jdbcTemplate.update(sql, goodsId);
    }

    @Override
    public void update(Goods goods) {
        String sql = "update t_goods set goodsname=? , price=?,`desc`=? where goodsid=?";
        jdbcTemplate.update(sql, goods.getGoodsName(), goods.getPrice(), goods.getDesc(), goods.getGoodsId());
    }


}
