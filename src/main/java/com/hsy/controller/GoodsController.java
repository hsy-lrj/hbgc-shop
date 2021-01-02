package com.hsy.controller;

import com.hsy.domain.Goods;
import com.hsy.domain.User;
import com.hsy.service.IGoodsService;
import com.hsy.util.AjaxObj;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 商品管理
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    @GetMapping("/detail")
    @ResponseBody
    public AjaxObj findGoodsById(int id) {
        Goods goods = goodsService.findGoodsById(id);
        if (goods!=null){
            return new AjaxObj(200,"查询成功",goods);
        }else {
            return new AjaxObj(0,"没有查询到信息");
        }
    }

    @RequestMapping("/list")
    public String list() {
        return "goods/list";
    }

    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pager<Map<String, Object>> pager(HttpSession session, int page, int limit, String search)
            throws UnsupportedEncodingException {
        if (search == null || search.trim().equals("")) {
            search = "";
        } else {
            search = search.trim();
        }
        // 获取登陆的用户
        User loginUser = (User) session.getAttribute("loginUser");
        int userId = loginUser.getUserId();
        return goodsService.find(userId, search, page, limit);
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "goods/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpSession session, Goods goods, @RequestParam("file") MultipartFile file) {
        try {
            // 获取登陆的用户
            User loginUser = (User) session.getAttribute("loginUser");
            int userId = loginUser.getUserId();
            goods.setUserId(userId);
            goodsService.add(goods, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/goods/list";
    }

    @RequestMapping("/update")
    public String update(int goodsId, Model model) {
        Goods goods = goodsService.load(goodsId);
        model.addAttribute("goods", goods);
        return "goods/update";
    }

    // 更新这里权限不能更改
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxObj update(Goods goods) {
        try {
            goodsService.update(goods);
            return new AjaxObj(200, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxObj(0, e.getMessage());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxObj delete(int goodsId) {
        try {
            goodsService.delete(goodsId);
            return new AjaxObj(200, "删除成功");
        } catch (Exception e) {
            return new AjaxObj(0, e.getMessage());
        }
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    @ResponseBody
    // 如果传递的是数组,会在name后面加[] 需要映射一下
    public AjaxObj delete(@RequestParam("ids[]") int[] ids) {

        try {
            String idsStr = "";
            for (int id : ids) {
                goodsService.delete(id);
            }
            return new AjaxObj(200, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxObj(0, e.getMessage());
        }
    }
}
