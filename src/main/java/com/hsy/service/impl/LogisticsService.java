package com.hsy.service.impl;

import com.hsy.dao.ILogisticsDao;
import com.hsy.dao.IOrderDao;
import com.hsy.dao.IUserDao;
import com.hsy.domain.Logistics;
import com.hsy.domain.Order;
import com.hsy.domain.User;
import com.hsy.service.ILogisticsService;
import com.hsy.util.Pager;
import com.hsy.util.SendSMSUtil;
import com.hsy.util.ShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 物流管理
 */
@Service("logisticsService")
public class LogisticsService implements ILogisticsService {

    @Autowired
    private ILogisticsDao logisticsDao;
    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IUserDao userDao;

    /**
     *发送信息
     */
    @Override
    public void sendSMS(int logisticsId) throws ShopException {
        // 根据物流id查询物流信息
        Logistics logistics = logisticsDao.load(logisticsId);
        // 根据物流信息里面的订单id查询出对应订单信息
        Order order = orderDao.load(logistics.getOrderId());
        // 根据订单信息里面的用户id查询出对应的用户
        User user = userDao.load(order.getConsumeId());
        // 发送短信，传递的参数：用户的手机号、物流的状态
        SendSMSUtil.sendSMS(user.getPhone(), logistics.getStatus());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Pager<Logistics> find(int userId, String search, int page, int size) {
        search = "%" + search + "%";
        // 判断当前是管理员还是商家
        User user = userDao.load(userId);
        if (user.getRoleName().equals("管理员")) {
            return logisticsDao.find(0, search, page, size);
        } else if (user.getRoleName().equals("商家")) {
            return logisticsDao.find(user.getUserId(), search, page, size);
        }
        // 到这里说明不是商家也不是管理员,那就是出错了,因为只有管理员和商家可以进入管理系统
        Pager pager = new Pager();
        pager.setCode(500);
        pager.setMsg("登陆用户的角色有问题,请与管理员联系");
        return pager;
    }

    @Override
    public void add(int orderId) throws ShopException {
        // 判断是否有该订单的物流信息
        Logistics oldLogistics = logisticsDao.loadByOrderId(orderId);
        if (oldLogistics != null) {
            throw new ShopException("该订单已有物流信息");
        }
        Logistics logistics = new Logistics();
        logistics.setOrderId(orderId);
        logistics.setStatus("已出库");
        logisticsDao.add(logistics);

    }

    @Override
    public Logistics loadByOrderId(int orderId) {
        return logisticsDao.loadByOrderId(orderId);
    }

    @Override
    public void delete(int logisticsId) {
        logisticsDao.delete(logisticsId);
    }

    @Override
    public void update(Logistics logistics) {
        logisticsDao.update(logistics);
    }

    @Override
    public Logistics load(int logisticsId) {
        return logisticsDao.load(logisticsId);
    }


}
