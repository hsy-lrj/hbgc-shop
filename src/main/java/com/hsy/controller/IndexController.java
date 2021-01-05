package com.hsy.controller;

import com.hsy.domain.User;
import com.hsy.service.IUserService;
import com.hsy.util.AjaxObj;
import com.hsy.util.ShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class IndexController {

    @Autowired
    private IUserService userService;

    // -------------

    // 登陆页面
    @RequestMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    // 登陆校验
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxObj doorLogin(User user, HttpSession session,
                             HttpServletRequest request) {
        try {
            User loginUser = userService.login(user);
            session.setAttribute("loginUser", loginUser);
            return new AjaxObj(200, "登录成功", loginUser);
        } catch (ShopException e) {
            return new AjaxObj(0, e.getMessage());
        }
    }

    /**
     * 退出登陆
      * @param session
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public AjaxObj logout(HttpSession session) {
        // 清空session
        session.invalidate();
        return new AjaxObj(200, "退出登录");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public AjaxObj register(User user) {
        try {
            userService.register(user);
            return new AjaxObj(200, "注册成功");
        } catch (ShopException e) {
            return new AjaxObj(0, e.getMessage());
        }
    }

    // 首页
    @RequestMapping("/index")
    public String index(Model model, HttpSession session) {
        // 获取登陆的用户,用于判断该用户是否是管理员,然后判断是否加载系统模块
        User loginUser = (User) session.getAttribute("loginUser");
        session.setAttribute("loginUser", loginUser);
        return "index";
    }

    // 欢迎统计页
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

}
