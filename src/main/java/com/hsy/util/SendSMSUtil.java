package com.hsy.util;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Set;

public class SendSMSUtil {

	@SuppressWarnings("unchecked")
	public static void sendSMS(String phone, String status)
			throws ShopException {

		// 生产环境请求地址：app.cloopen.com
		String serverIp = "app.cloopen.com";
		// 请求端口
		String serverPort = "8883";
		// 主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
		String accountSId = "8a216da8762cb457017692c14f2326b7";
		String accountToken = "b80dab5a2f0e4da1b238fe64b4f366df";
		// 请使用管理控制台中已创建应用的APPID
		String appId = "8a216da8762cb457017692c1502526be";
		CCPRestSmsSDK sdk = new CCPRestSmsSDK();
		sdk.init(serverIp, serverPort, true);
		sdk.setAccount(accountSId, accountToken);
		sdk.setAppId(appId);
		sdk.setBodyType(BodyType.Type_XML);
		// 测试模板ID为1
		String templateId = "1";
		// /r是换行
		String[] datas = { "\r   \"天亮教育提醒您\r" + "   你的商品已" + status+"\"", "10" };
		String subAppend = "1234"; // 可选 扩展码，四位数字 0~9999
		String reqId = "***"; // 可选 第三方自定义消息id，最大支持32位英文数字，同账号下同一自然天内不允许重复
		// HashMap<String, Object> result =
		// sdk.sendTemplateSMS(to,templateId,datas);
		HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId,
				datas, subAppend, reqId);
		if ("000000".equals(result.get("statusCode"))) {
			// 正常返回输出data包体信息（map）
			HashMap<String, Object> data = (HashMap<String, Object>) result
					.get("data");
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				Object object = data.get(key);
				System.out.println(key + " = " + object);
			}
		} else {
			// 异常返回输出错误码和错误信息
			throw new ShopException("错误码=" + result.get("statusCode")
					+ " 错误信息= " + result.get("statusMsg"));
		}

	}
}
