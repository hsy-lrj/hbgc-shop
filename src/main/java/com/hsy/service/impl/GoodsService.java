package com.hsy.service.impl;

import com.hsy.dao.IGoodsDao;
import com.hsy.dao.IOrderDao;
import com.hsy.dao.IUserDao;
import com.hsy.domain.Goods;
import com.hsy.domain.Order;
import com.hsy.domain.User;
import com.hsy.service.IGoodsService;
import com.hsy.util.Pager;
import com.hsy.util.ShopException;
import com.hsy.util.SystemContext;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 商品管理
 */
@Service("goodsService")
public class GoodsService implements IGoodsService {

	@Autowired
	private IGoodsDao goodsDao;
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IUserDao userDao;

	// -----------------------------

	@Override
	public Goods findGoodsById(int id) {
		Goods goods = goodsDao.findGoodsById(id);
		return goods;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Pager<Map<String, Object>> find(int userId, String search, int page, int size) {
		search = "%" + search + "%";
		// 判断当前是管理员还是商家
		User user = userDao.load(userId);
		if (user.getRoleName().equals("管理员")) {
			return goodsDao.find(0, search, page, size);
		} else if (user.getRoleName().equals("商家")) {
			return goodsDao.find(user.getUserId(), search, page, size);
		}
		// 到这里说明不是商家也不是管理员,那就是出错了,因为只有管理员和商家可以进入管理系统
		Pager pager = new Pager();
		pager.setCode(500);
		pager.setMsg("登陆用户的角色有问题,请与管理员联系");
		return pager;
	}

	@Override
	public Pager<Map<String, Object>> find(String search, int page, int size) {
		search = "%" + search + "%";
		Pager<Map<String, Object>> goods = goodsDao.find(0, search, page, size);
		goods.setCode(200);
		return goods;
	}

	@Override
	public Goods load(int goodsId) {
		return goodsDao.load(goodsId);
	}

	@Override
	public void add(Goods goods, MultipartFile multipartFile)
			throws ShopException {
		// 获取上传的文件路径
		String realPath = SystemContext.getRealPath();
		// 图片存储我们路径为 /upload/用户ID/当前时间毫秒数-用户ID.xxx
		String filePath = realPath + goods.getUserId() + "/";
		String serverPath = SystemContext.getServerPath() + goods.getUserId()
				+ "/";
		// 判断路径是否存在,不存在就创建
		File file1 = new File(filePath);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		file1 = new File(serverPath);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		// getExtension : 得到文件的后缀名
		String extendName = FilenameUtils.getExtension(multipartFile
				.getOriginalFilename());
		String fileName = System.currentTimeMillis() + "-" + goods.getUserId()
				+ "." + extendName;
		// 拼接全路径
		filePath += fileName;
		try {
			// 图片转换为流
			InputStream inputStream = multipartFile.getInputStream();
			// 处理图片宽高
			// 获取到图片的信息对象
			BufferedImage oldBufferedImage = ImageIO.read(inputStream);
			// 获取上传图片的高度
			int height = oldBufferedImage.getHeight();
			// 对图片信息对象进行封装,获得文件比例缩放对象,并进行设置
			Builder<BufferedImage> bufferedImage = Thumbnails
					.of(oldBufferedImage);
			// 如果图片高度超过我们指定的宽度,就进行比例缩放
			if (height > SystemContext.getIMG_HEIGHT()) {
				// 内部是压缩比例8/10 = 0.8 那么所有的传入的1000,结果就是800,所有的就都是800了
				// scale : 方法传入的是一个比例,传入0.5 就说明要压缩图片比例为 50%
				// 所以这里用我们指定的高度去 除以 图片的宽度 就是比例
				bufferedImage.scale((double) SystemContext.getIMG_HEIGHT()
						/ (double) height);
			} else {
				// 如果图片的高度不大于我们指定的宽度,就不进行压缩,比例为 1.0 就是不压缩
				bufferedImage.scale(1.0f);
			}
			// 把压缩好的图片保存到指定位置,路径中需要由文件的名字
			bufferedImage.toFile(filePath);
			bufferedImage.toFile(serverPath + "/" + fileName);
		} catch (IOException e) {
			throw new ShopException(e.getMessage());
		}
		// 数据库添加
		goods.setImgUrl(SystemContext.getContentPath() + goods.getUserId()
				+ "/" + fileName);
		goodsDao.add(goods);
	}

	@Override
	public void delete(int goodsId) throws ShopException {
		// 删除文件
		Goods goods = goodsDao.load(goodsId);

		// http://127.0.0.1:8081/upload/1/1608368156083-1.jpg
		String contentPath = goods.getImgUrl();
		// 截取upload/1/1608368156083-1.jpg
		// contentPath.indexOf("/upload/") : 获取指定字符串的起始索引
		// 由于/upload/的长度是8 所以要+8 跳过/upload/
		contentPath = contentPath
				.substring(contentPath.indexOf("/upload/") + 8);

		String serverPath = SystemContext.getServerPath() + contentPath;
		String realPath = SystemContext.getRealPath() + contentPath;

		new File(serverPath).delete();
		new File(realPath).delete();

		// 判断该该商品是否有相关订单,有就不能删除
		List<Order> orders = orderDao.listByGoodsId(goodsId);
		if (orders != null && orders.size() > 0) {
			throw new ShopException("该商品 : " + goodsId + " 下还有订单,请先删除订单");
		}
		goodsDao.delete(goodsId);
	}

	@Override
	public void update(Goods goods) {
		goodsDao.update(goods);
	}

}
