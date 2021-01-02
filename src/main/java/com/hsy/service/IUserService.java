package com.hsy.service;


import com.hsy.domain.User;
import com.hsy.util.Pager;
import com.hsy.util.ShopException;

public interface IUserService {
    /**
     * 登陆,需要判断,普通用户不能登录后台
     *
     * @param user
     * @return
     * @throws ShopException
     */
    public User login(User user) throws ShopException;

    /**
     * 门户登陆
     *
     * @param user
     * @return
     * @throws ShopException
     */
    public User doorLogin(User user) throws ShopException;

    /**
     * 查询所有用户并分页
     *
     * @param search
     * @param page
     * @param size
     * @return
     */
    public Pager<User> find(String search, int page, int size);

    /**
     * 普通用户注册 ,用户名存在时 抛出异常
     *
     * @param user
     * @throws ShopException
     */
    public void register(User user) throws ShopException;

    public User load(int userId);

    /**
     * 添加用户,是在后台管理中添加用户,这个用户一般是管理员或者商家
     *
     * @param user
     * @throws ShopException
     */
    public void add(User user) throws ShopException;

    /**
     * 删除用户需要先判断该用户是否还有商品在售
     */
    public void delete(int userId) throws ShopException;

    public void update(User user);
}
