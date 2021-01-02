package com.hsy.filter;

import com.hsy.util.SystemContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
@WebServlet(loadOnStartup = 1, urlPatterns = "/upload/updateUrl")
public class InitServlet extends HttpServlet {
	@Override
	public void init() throws ServletException {
		// 获取文件上传目录的绝对位置
		 SystemContext.setServerPath(getServletContext().getRealPath("/upload/"));
		SystemContext
				.setRealPath("E:\\Java_Project\\hbgc-shop\\src\\main\\resources\\static\\upload\\");
		SystemContext.setContentPath("http://127.0.0.1:8081/upload/");
	}
}
