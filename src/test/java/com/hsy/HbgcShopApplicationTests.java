package com.hsy;

import com.hsy.util.SendSMSUtil;
import com.hsy.util.ShopException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HbgcShopApplicationTests {

    @Test
    void contextLoads() throws ShopException {
        SendSMSUtil.sendSMS("15128307076","哈哈哈");
    }

}
