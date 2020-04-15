package com.glut.forestry_terminal.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.glut.forestry_terminal.entity.BaseEntity;
import com.glut.forestry_terminal.config.AlipayConfig;
import com.glut.forestry_terminal.utils.AliRequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AlipayController {
    @RequestMapping(value = "/alipay",method = RequestMethod.POST)
    public Object alipayment(@RequestParam String payMoney){

        //实例化客户端https://openapi.alipay.com/gateway.do
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
                AlipayConfig.APP_ID,AlipayConfig.APP_PRIVATE_KEY,"json",
                AlipayConfig.CHARSET,AlipayConfig.ALIPAY_PUBLIC_KEY,"RSA2");

        //构建支付宝请求参数
        AlipayTradeAppPayRequest request = AliRequestParam.startRequestAli(payMoney);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，客户端可以直接发起支付，无需再做处理。

            Map map = new HashMap();
            map.put("orderString", response.getBody());
            BaseEntity baseEntity = new BaseEntity("200", "支付宝下单成功", map);
            return baseEntity;

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        BaseEntity baseEntity = new BaseEntity("200", "支付宝下单失败", null);

        return baseEntity;
    }
}
