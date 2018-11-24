package com.jeeplus.modules.cultural.web.fore;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.sdk.WXPayXmlUtil;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.cultural.entity.order.CoupletsOrder;
import com.jeeplus.modules.cultural.entity.order.FinishedOrder;
import com.jeeplus.modules.cultural.entity.order.LexiconOrder;
import com.jeeplus.modules.cultural.entity.role.Customer;
import com.jeeplus.modules.cultural.service.order.CoupletsOrderService;
import com.jeeplus.modules.cultural.service.order.FinishedOrderService;
import com.jeeplus.modules.cultural.service.order.LexiconOrderService;
import com.jeeplus.modules.cultural.service.role.CustomerService;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.service.WxAccountService;
import com.jeeplus.modules.weixin.util.WxUtil;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.process.HttpMethod;
import com.jeeplus.wxapi.process.OAuthAccessToken;
import com.jeeplus.wxapi.process.WxApi;
import com.jeeplus.wxapi.util.HttpClientUtils;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value="wechat")
public class WechatController {
    private static Logger log = LogManager.getLogger(WechatController.class);
    private String orderId = null;
    private String orderType = null;
    @Autowired
    WxAccountService wxAccountService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CoupletsOrderService coupletsOrderService;
    @Autowired
    LexiconOrderService lexiconOrderService;
    @Autowired
    FinishedOrderService finishedOrderService;

    /**
     * 微信授权页面回调地址
     * @param code
     * @param state
     * @param request
     * @return
     */
    @RequestMapping(value="login")
    @ResponseBody
    public AjaxJson getAccessToken(String code, String state, HttpServletRequest request){
        AjaxJson json = new AjaxJson();

        String getOAuthUserInfoUrl = null;
        WxAccount account = wxAccountService.findList(new WxAccount()).get(0);
        if(account != null){
            try{
                OAuthAccessToken token = WxApi.getOAuthAccessToken(account.getAppid(),account.getAppsecret(),code);
                getOAuthUserInfoUrl = WxApi.getOAuthUserinfoUrl(token.getAccessToken(),token.getOpenid());
                JSONObject jsonObject = WxApi.httpsRequest(getOAuthUserInfoUrl, HttpMethod.GET,null);
                //若用户第一次登录，则把微信相关信息持久化,并将用户的id存入session
                String nickname = jsonObject.getString("nickname");
                String headimgurl = jsonObject.getString("headimgurl");
                String openid = jsonObject.getString("openid");
                Customer uniCustomer = customerService.findUniqueByProperty("openid", openid);
                Customer customer = new Customer();
                if (uniCustomer == null){
                    customer.setNickname(nickname);
                    customer.setHeadimg(headimgurl);
                    customer.setOpenid(openid);
                    customerService.save(customer);
                    request.getSession().setAttribute("customerId", customer.getId());
                    request.getSession().setAttribute("customer", customer);
                }else{
                    request.getSession().setAttribute("customer", uniCustomer);
                    request.getSession().setAttribute("customerId", uniCustomer.getId());
                }
//                log.debug(customer.getId());
//                log.debug(uniCustomer.getId());
//                log.debug(jsonObject.toJSONString());
                json.put("data",jsonObject);
            }catch (WxErrorException e){
                e.printStackTrace();
                json.setSuccess(false);
                json.setMsg("获取access_token失败");
                return json;
            }
        }

        return json;
    }


    @RequestMapping("wxpay")
    @ResponseBody
    public AjaxJson  wxpay(String orderId,String type,String price,HttpServletRequest request) throws Exception {
        AjaxJson json = new AjaxJson();
//        Map<String,Object> orderMap= (Map<String, Object>) request.getSession().getAttribute("map");
        HttpSession session = request.getSession();
        //设置订单id和类型
        this.orderId = orderId;
        this.orderType = type;
        Customer customer = (Customer) session.getAttribute("customer");
        String url = MyWXConfig.url;
        Map<String,String> map = new HashMap<String,String>();
        map.put("appid",MyWXConfig.getAppid());
        map.put("body","test");
        map.put("mch_id",MyWXConfig.getMch_id());
        map.put("nonce_str",WXPayUtil.generateNonceStr());
        map.put("notify_url",MyWXConfig.getNotify_url());
        map.put("openid",customer.getOpenid());
        map.put("out_trade_no", orderId);
        map.put("spbill_create_ip",MyWXConfig.getIpAddr(request));
//        map.put("total_fee","1");//修改
        map.put("total_fee",price);//修改
        map.put("trade_type",MyWXConfig.getTrade_type());

        String sign = WXPayUtil.generateSignature(map,MyWXConfig.key);
        map.put("sign",sign);
        String mapXml = WXPayUtil.mapToXml(map);
        String result =  HttpClientUtils.sendHttpPost(url,mapXml);
        //将返回出来的xml转换为map
        Map<String,String>  xmlmap  = WXPayUtil.xmlToMap(result);
        Map<String,String> payData = unifiedOrder(xmlmap);
        payData.put("prepay_id",xmlmap.get("prepay_id"));
//        model.addAttribute("pay",map1);
        json.put("prepayId",xmlmap.get("prepay_id"));
        json.put("payData", payData);
        return json;
    }

    public Map unifiedOrder (Map<String,String> map){
        String sign = "";
        Map<String,String> map1 = new HashMap<>();
        try {
            map1.put("appId",MyWXConfig.getAppid());    //公众账号ID
            String time = new Date().getTime()+"";
            String times = time.substring(0,10);
            map1.put("timeStamp",times);  //时间戳
            map1.put("nonceStr",WXPayUtil.generateNonceStr());      //随机字符串
            map1.put("package","prepay_id="+map.get("prepay_id"));//订单详情扩展字符串
            map1.put("signType","MD5");     //签名方式
            //生成签名
            sign = WXPayUtil.generateSignature(map1,MyWXConfig.key);
            map1.put("paySign",sign);       //签名
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map1;
    }

    @ResponseBody
    @RequestMapping("wxPayCallBack")
    public void wxPayCallBack(HttpServletRequest request, HttpServletResponse response)  {
        String str = "";
        try {
            InputStream inputStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) != -1) {
                outSteam.write(buff, 0, len);
            }
            inputStream.close();
            outSteam.close();
            String result = new String(outSteam.toByteArray(), "utf-8");
            //验证签名
            if (!WXPayUtil.isSignatureValid(result,MyWXConfig.key)){
                return;
            }
            //把微信传过来的xml数据转换为map
            Map map = WXPayUtil.xmlToMap(result);
            //------------------------------------------
//            syOrderService.updateOrderStatus(oId);
            //-------------------------------------------
            if(!"".equals(this.orderId) && "1".equals(this.orderType)){
                CoupletsOrder order = coupletsOrderService.get(orderId);
                order.setStatus("2");
                coupletsOrderService.save(order);
                System.out.println("***************************************************************************");
            }
            if(!"".equals(this.orderId) && "2".equals(this.orderType)){
                LexiconOrder order = lexiconOrderService.get(orderId);
                order.setStatus("2");
                lexiconOrderService.save(order);
                System.out.println("***************************************************************************");
            }
            if(!"".equals(this.orderId) && "3".equals(this.orderType)){
                FinishedOrder order = finishedOrderService.get(orderId);
                order.setStatus("2");
                finishedOrderService.save(order);
                System.out.println("***************************************************************************");
            }
            System.out.println(str);
            str =
                    "<xml>" +
                    " <return_code><![CDATA[SUCCESS]]></return_code> " +
                    " <return_msg><![CDATA[OK]]></return_msg> " +
                    "</xml>";

            System.out.println("微信 支付 成功");
            //Map<String,String> rtnMap = new HashMap<String, String>();
            //rtnMap.put("str","success");
            //String strXml = MyWXConfig.mapToXml(rtnMap);
            System.out.println(str);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(str);
            response.getWriter().flush();
            if (response.getWriter()!=null){
                response.getWriter().close();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
