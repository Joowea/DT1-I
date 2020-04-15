package com.glut.forestry_terminal.utils;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;

public class AliRequestParam {
    public static AlipayTradeAppPayRequest startRequestAli(String payMoney) {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo(OrderNo.getOutTradeNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(payMoney);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://www.baidu.com");
        // request.setNotifyUrl("http://www.baidu.com:8080/pay/alipay/notify");
        return request;
    }
}
