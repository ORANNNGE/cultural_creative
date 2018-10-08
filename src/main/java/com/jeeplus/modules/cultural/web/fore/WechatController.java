package com.jeeplus.modules.cultural.web.fore;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.cultural.entity.role.Customer;
import com.jeeplus.modules.cultural.service.role.CustomerService;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.service.WxAccountService;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.process.HttpMethod;
import com.jeeplus.wxapi.process.OAuthAccessToken;
import com.jeeplus.wxapi.process.WxApi;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value="wechat")
public class WechatController {
    private static Logger log = LogManager.getLogger(WechatController.class);
    @Autowired
    WxAccountService wxAccountService;
    @Autowired
    CustomerService customerService;

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
                }else{
                    request.getSession().setAttribute("customerId", uniCustomer.getId());
                }
                log.debug(customer.getId());
                log.debug(uniCustomer.getId());
                log.debug(jsonObject.toJSONString());
//                log.debug(jsonObject.getString("nickname"));
//                log.debug(jsonObject.getString("headimgurl"));
//                log.debug(jsonObject.getString("openid"));
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
}
