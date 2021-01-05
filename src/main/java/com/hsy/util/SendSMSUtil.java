package com.hsy.util;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;

public class SendSMSUtil {

    @SuppressWarnings("unchecked")
    public static void sendSMS(String phone, String status)
            throws ShopException {

        //生产环境请求地址
        String serverIp = "app.cloopen.com";
        // 请求端口
        String serverPort = "8883";
        // 主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = "8aaf0708762cb1cf0176cb2cd7a836c8";
        String accountToken = "508a4086b3e64625b38cc53f7f727e6d";
        // 请使用管理控制台中已创建应用的APPID
        String appId = "8aaf0708762cb1cf0176cb2cd87c36cf";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort, true);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_XML);
        // 测试模板ID为1
        String templateId = "1";
        String[] datas = {"\r  \"Victory提醒你\r" + "  你的商品" + status + "\"", "10"};
        String subAppend = "1234"; // 可选 扩展码，四位数字 0~9999
        String reqId = "***"; // 可选 第三方自定义消息id，最大支持32位英文数字，同账号下同一自然天内不允许重复
        // HashMap<String, Object> result =
        // sdk.sendTemplateSMS(to,templateId,datas);
        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId,
                datas, subAppend, reqId);
        if ("000000".equals(result.get("statusCode"))) {

        } else {
            // 异常返回输出错误码和错误信息
            throw new ShopException("错误码=" + result.get("statusCode")
                    + " 错误信息= " + result.get("statusMsg"));
        }
    }

}
