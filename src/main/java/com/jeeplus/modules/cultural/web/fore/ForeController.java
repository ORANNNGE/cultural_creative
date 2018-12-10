package com.jeeplus.modules.cultural.web.fore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.entity.couplets.Lexicon;
import com.jeeplus.modules.cultural.entity.finished.Calligraphy;
import com.jeeplus.modules.cultural.entity.finished.Decoration;
import com.jeeplus.modules.cultural.entity.finished.NewYearPic;
import com.jeeplus.modules.cultural.entity.finished.Painting;
import com.jeeplus.modules.cultural.entity.order.*;
import com.jeeplus.modules.cultural.entity.platform.*;
import com.jeeplus.modules.cultural.entity.role.Author;
import com.jeeplus.modules.cultural.entity.role.Customer;
import com.jeeplus.modules.cultural.entity.spec.Craft;
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.entity.spec.Typeface;
import com.jeeplus.modules.cultural.service.couplets.CoupletsService;
import com.jeeplus.modules.cultural.service.couplets.LexiconService;
import com.jeeplus.modules.cultural.service.finished.*;
import com.jeeplus.modules.cultural.service.order.*;
import com.jeeplus.modules.cultural.service.platform.*;
import com.jeeplus.modules.cultural.service.role.AuthorService;
import com.jeeplus.modules.cultural.service.role.CustomerService;
import com.jeeplus.modules.cultural.service.spec.CraftService;
import com.jeeplus.modules.cultural.service.spec.FrameService;
import com.jeeplus.modules.cultural.service.spec.SizeService;
import com.jeeplus.modules.cultural.service.spec.TypefaceService;
import com.jeeplus.modules.cultural.utils.MsgUtil;
import com.sun.source.tree.TypeCastTree;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.commons.collections.iterators.IteratorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("fore")
public class ForeController {
    @Autowired
    CoupletsService coupletsService;
    @Autowired
    CalligraphyService calligraphyService;
    @Autowired
    DecorationService decorationService;
    @Autowired
    NewYearPicService newYearPicService;
    @Autowired
    PaintingService paintingService;
    @Autowired
    LexiconService lexiconService;
    @Autowired
    CustomerService customerService;
    @Autowired
    AddressService addressService;
    @Autowired
    SizeService sizeService;
    @Autowired
    CraftService craftService;
    @Autowired
    FrameService frameService;
    @Autowired
    CoupletsPriceService coupletsPriceService;
    @Autowired
    CoupletsOrderService coupletsOrderService;
    @Autowired
    TypefaceService typefaceService;
    @Autowired
    LexiconPriceService lexiconPriceService;
    @Autowired
    AuthorService authorService;
    @Autowired
    LexiconOrderService lexiconOrderService;
    @Autowired
    FinishedOrderService finishedOrderService;
    @Autowired
    ComboService comboService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    PlatIntroService platIntroService;
    @Autowired
    CustomerServService customerServService;
    @Autowired
    LeaveMsgService leaveMsgService;
    @Autowired
    PlatHelpService platHelpService;
    private Logger logger = LoggerFactory.getLogger(ForeController.class);

    /**
     * 返回首页的数据
     * @return
     */
    @RequestMapping("getIndexData")
    @ResponseBody
    public AjaxJson indexContent(){
        AjaxJson json = new AjaxJson();
        //获取全部成品楹联
        List<Couplets> couplets = coupletsService.findList(new Couplets());
        //修改图片路径
        for (Couplets c : couplets) {
            c.setPicture(c.getPicture().replace("|",""));
        }
        //首页推荐的机关楹联
        List<Couplets>  government = coupletsService.getIndexRecommendCoupletsList("1");
        //单位 楹联
        List<Couplets>  company = coupletsService.getIndexRecommendCoupletsList("2");
        //家庭 楹联
        List<Couplets>  home = coupletsService.getIndexRecommendCoupletsList("3");
        // 分别给三种楹联赋值
/*        for (Couplets c : couplets) {
            if("1".equals(c.getLexicon().getType()) && government.size() < 4){
                government.add(c);
            }
            if("2".equals(c.getLexicon().getType()) && company.size() < 4){
                  company.add(c);
            }
            if("3".equals(c.getLexicon().getType()) && home.size() < 4){
                home.add(c);
            }
        }*/
        //获取书画作品、装饰品和年画
        List<Calligraphy> calligraphies = calligraphyService.getNewest();
        List<Painting> paintings = paintingService.getNewest();
        List<Decoration> decorations = decorationService.getNewest();
        List<NewYearPic> newYearPics = newYearPicService.getNewest();
        //put到json
        json.put("government",government);
        json.put("company",company);
        json.put("home",home);
        json.put("calligraphies",calligraphies);
        json.put("decorations",decorations);
        json.put("newYearPics",newYearPics);
        json.put("paintings",paintings);

        return json;
    }

    /**
     * 使用PageHelper分页插件，根据楹联类型、页码和每页的大小返回不同的楹联成品
     * @param type 楹联类型
     * @param pageSize 每页的大小（每页有几条数据）
     * @param pageNum 页码
     * @return
     */
    @RequestMapping(value = "getCoupletsList")
    @ResponseBody
    public AjaxJson getCoupletsList(String type,Integer pageSize,Integer pageNum){
        List<Couplets> recommendCouplets = new ArrayList<>();
        if(pageNum == 1){
            recommendCouplets = coupletsService.getRecommendCoupletsList(type);
        }
        AjaxJson json = new AjaxJson();
        //调用PageHelper静态方法startPage()，必须要在查询数据库之前调用
        PageHelper.startPage(pageNum,pageSize);
        List<Couplets> coupletsList = coupletsService.getNotRecommendCoupletsList(type);
//        coupletsList.addAll(recommendCouplets);
        coupletsList.addAll(0, recommendCouplets);
        //将查询出来的数据放入PageInfo
        PageInfo page = new PageInfo(coupletsList);

        json.put("page",page);
        return json;
    }

    /**
     * 使用PageHelper分页插件，根据页码和每页的大小返回楹联词库
     * @param pageSize 每页的大小
     * @param pageNum 页码
     * @return
     */
    @RequestMapping(value = "getLexiconList")
    @ResponseBody
    public AjaxJson getLexiconList(String type, Integer pageSize,Integer pageNum){
        AjaxJson json = new AjaxJson();
        //调用PageHelper静态方法startPage()，必须要在查询数据库之前调用
        PageHelper.startPage(pageNum,pageSize);
        Lexicon selectLexicon = new Lexicon();
        selectLexicon.setType(type);
        List<Lexicon> lexiconList = lexiconService.findList(selectLexicon);
        //将查询出来的数据放入PageInfo
        PageInfo page = new PageInfo(lexiconList);
        json.put("page",page);
        return json;
    }

    /**
     * 使用PageHelper分页插件，根据页码和每页的大小返回年画
     * @param pageSize 每页的大小
     * @param pageNum 页码
     * @return
     */
    @RequestMapping(value="getNewYearPicList")
    @ResponseBody
    public AjaxJson getNewYearPicList(Integer pageSize,Integer pageNum){
        AjaxJson json = new AjaxJson();
        //调用PageHelper静态方法startPage()，必须要在查询数据库之前调用
        PageHelper.startPage(pageNum,pageSize);
        List<NewYearPic> newYearPicList = newYearPicService.findList(new NewYearPic());
        //将查询出来的数据放入PageInfo
        PageInfo page = new PageInfo(newYearPicList);
        json.put("page",page);
        return json;
    }

    /**
     * 使用PageHelper分页插件，根据页码和每页的大小返回书法作品
     * @param pageSize 每页的大小
     * @param pageNum 页码
     * @return
     */
    @RequestMapping(value="getCalligraphyList")
    @ResponseBody
    public AjaxJson getCalligraphyList(Integer pageSize,Integer pageNum){
        AjaxJson json = new AjaxJson();
        //调用PageHelper静态方法startPage()，必须要在查询数据库之前调用
        PageHelper.startPage(pageNum,pageSize);
        List<Calligraphy> calligraphyList = calligraphyService.findList(new Calligraphy());
        //将查询出来的数据放入PageInfo
        PageInfo page = new PageInfo(calligraphyList);
        json.put("page",page);
        return json;
    }

    /**
     * 使用PageHelper分页插件，根据页码和每页的大小返回美术作品
     * @param pageSize 每页的大小
     * @param pageNum 页码
     * @return
     */
    @RequestMapping(value="getPaintingList")
    @ResponseBody
    public AjaxJson getPaintingList(Integer pageSize,Integer pageNum){
        AjaxJson json = new AjaxJson();
        //调用PageHelper静态方法startPage()，必须要在查询数据库之前调用
        PageHelper.startPage(pageNum,pageSize);
        List<Painting> paintingList = paintingService.findList(new Painting());
        //将查询出来的数据放入PageInfo
        PageInfo page = new PageInfo(paintingList);
        json.put("page",page);
        return json;
    }

    /**
     * 使用PageHelper分页插件，根据页码和每页的大小返回美术作品
     * @param pageSize 每页的大小
     * @param pageNum 页码
     * @return
     */
    @RequestMapping(value="getDecorationList")
    @ResponseBody
    public AjaxJson getDecorationList(Integer pageSize,Integer pageNum){
        AjaxJson json = new AjaxJson();
        //调用PageHelper静态方法startPage()，必须要在查询数据库之前调用
        PageHelper.startPage(pageNum,pageSize);
        List<Decoration> decorationList = decorationService.findList(new Decoration());
        //将查询出来的数据放入PageInfo
        PageInfo page = new PageInfo(decorationList);
        json.put("page",page);
        return json;
    }

    /**
     *  根据id获取楹联
     * @param id
     * @return
     */
    @RequestMapping(value="getCoupletsById")
    @ResponseBody
    public AjaxJson getCoupletsById(String id){
        AjaxJson json = new AjaxJson();
        Couplets data = coupletsService.get(id);
        if(data != null){
            json.setSuccess(true);
            json.setErrorCode("1");
            json.setMsg("查询couplets成功");
            json.put("data",data);
        }else{
            json.setSuccess(false);
            json.setErrorCode("0");
            json.setMsg("未查询到");
        }
        return json;
    }

    /**
     *  根据id获取词库
     * @param id
     * @return
     */
    @RequestMapping(value="getLexiconById")
    @ResponseBody
    public AjaxJson getLexiconById(String id){
        AjaxJson json = new AjaxJson();
        Lexicon data = lexiconService.get(id);
        if(data != null){
            json.setSuccess(true);
            json.setErrorCode("1");
            json.setMsg("查询lexicon成功");
            json.put("data",data);
        }else{
            json.setSuccess(false);
            json.setErrorCode("0");
            json.setMsg("未查询到");
        }
        return json;
    }

    /**
     *  根据id获取书法作品
     * @param id
     * @return
     */
    @RequestMapping(value="getCalligraphyById")
    @ResponseBody
    public AjaxJson getCalligraphyById(String id){
        AjaxJson json = new AjaxJson();
        Calligraphy data = calligraphyService.get(id);
        if(data != null){
            json.setSuccess(true);
            json.setErrorCode("1");
            json.setMsg("查询calligraphy成功");
            json.put("data",data);
        }else{
            json.setSuccess(false);
            json.setErrorCode("0");
            json.setMsg("未查询到");
        }
        return json;
    }

    /**
     *  根据id获取书法作品
     * @param id
     * @return
     */
    @RequestMapping(value="getPaintingById")
    @ResponseBody
    public AjaxJson getPaintingById(String id){
        AjaxJson json = new AjaxJson();
        Painting data = paintingService.get(id);
        if(data != null){
            json.setSuccess(true);
            json.setErrorCode("1");
            json.setMsg("查询painting成功");
            json.put("data",data);
        }else{
            json.setSuccess(false);
            json.setErrorCode("0");
            json.setMsg("未查询到");
        }
        return json;
    }

    /**
     *  根据id获取书法作品
     * @param id
     * @return
     */
    @RequestMapping(value="getDecorationById")
    @ResponseBody
    public AjaxJson getDecorationById(String id){
        AjaxJson json = new AjaxJson();
        Decoration data = decorationService.get(id);
        if(data != null){
            json.setSuccess(true);
            json.setErrorCode("1");
            json.setMsg("查询decoration成功");
            json.put("data",data);
        }else{
            json.setSuccess(false);
            json.setErrorCode("0");
            json.setMsg("未查询到");
        }
        return json;
    }

    /**
     *  根据id获取书法作品
     * @param id
     * @return
     */
    @RequestMapping(value="getNewYearPicById")
    @ResponseBody
    public AjaxJson getNewYearPicById(String id){
        AjaxJson json = new AjaxJson();
        NewYearPic data = newYearPicService.get(id);
        if(data != null){
            json.setSuccess(true);
            json.setErrorCode("1");
            json.setMsg("查询newYearPic成功");
            json.put("data",data);
        }else{
            json.setSuccess(false);
            json.setErrorCode("0");
            json.setMsg("未查询到");
        }
        return json;
    }

    /**
     * 根据在session中存放的用户id，查询该用户的信息
     * @param request
     * @return
     */
    @RequestMapping(value="getCustomerInfo")
    @ResponseBody
    public AjaxJson getCustomerInfo(HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        String id = (String) request.getSession().getAttribute("customerId");
        Customer customer = customerService.get(id);
        if(customer != null){
            customer.setOpenid(null);
            json.setMsg("查询用户成功");
            json.put("data", customer);
            return json;
        }else{
            json.setSuccess(false);
            json.setMsg("查询失败");
            return json;
        }
    }

   /* @RequestMapping(value="getVerifyCode")
    @ResponseBody
    public AjaxJson getVerifyCode(String phoneNum,HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        //从session 获取用户id
        String customerId = (String)request.getSession().getAttribute("customerId");
        //是否存在该用户
        boolean isExist = customerService.get(customerId)==null?false:true;
        //判断手机号格式
        if(customerId == null || "".equals(customerId)){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }
        if(phoneNum == null || "".equals(phoneNum)){
            json.setSuccess(false);
            json.setMsg("手机号不能为空");
            return json;
        }
        boolean isPhoneNum = Pattern.matches("^1[356789]\\d{9}$", phoneNum);
        if(!isPhoneNum){
            json.setSuccess(false);
            json.setMsg("手机号格式不正确");
            return json;
        }
        if(!isExist){
            json.setSuccess(false);
            json.setMsg("用户不存在，请联系客服");
            return json;
        }
        String respMsg = MsgUtil.sendVerifyCode(phoneNum, request);
        json.setMsg("已发送");
        json.put("respMsg", respMsg);
        return json;
    }*/

    @RequestMapping(value="bindPhoneNum")
    @ResponseBody
    public AjaxJson bindPhoneNum(String phoneNum,HttpServletRequest request){
        AjaxJson json = new AjaxJson();

        //从session 获取用户id
        String customerId = (String)request.getSession().getAttribute("customerId");
        //从session 获取用户之前验证的手机号
//        String phoneNum = (String)request.getSession().getAttribute("verifyPhoneNum");
//        //从session 获取发送的验证码
//        String code = (String)request.getSession().getAttribute("code");

        //登录是否过期
        if(customerId == null || "".equals(customerId)){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }

        //是否存在该用户
        Customer customer = customerService.get(customerId);
        boolean isExist = customer==null?false:true;
        if(!isExist){
            json.setSuccess(false);
            json.setMsg("用户不存在");
            return json;
        }

//        if(verifyCode == null || "".equals(verifyCode)){
//            json.setSuccess(false);
//            json.setMsg("请输入验证码");
//            return json;
//        }

        //验证手机号
//        if(!verifyCode.equals(code)){
//            json.setSuccess(false);
//            json.setMsg("验证码不正确");
//            return json;
//        }

        customer.setPhonenum(phoneNum);
        customerService.save(customer);
        json.setMsg("绑定成功");
        return json;
    }

    /**
     * 添加收货地址
     * @param address
     * @param request
     * @return
     */
    @RequestMapping(value="addAddress")
    @ResponseBody
    public AjaxJson addAddress(Address address,HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        Customer customer = (Customer) request.getSession().getAttribute("customer");
//        Customer customer  = customerService.get("dfbad77e2574458fa06b209ebbe9e6e5");
        //登录是否过期
        if(customer == null){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }
        if(!StringUtils.isEmpty(address.getId())){
            address.setCustomer(customer);
            addressService.save(address);
            return json;
        }
        //是否存在该用户
        Address selectAddress = new Address();
        address.setCustomer(customer);
        List<Address> addressList = addressService.findList(selectAddress);
        if(addressList.size()>0){
            address.setIsDefault("0");
        }else {
            address.setIsDefault("1");
        }
        address.setCustomer(customer);
        addressService.save(address);
        json.setMsg("添加成功");
        return json;
    }

    @RequestMapping(value="setDefaultAddr")
    @ResponseBody
    public AjaxJson setDefaultAddr(String id,HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        Customer customer = (Customer) request.getSession().getAttribute("customer");
//        Customer customer = customerService.get("dfbad77e2574458fa06b209ebbe9e6e5");
        if(customer == null){
            json.setSuccess(false);
            json.setMsg("请重新登录");
        }
        Address selectAddress = new Address();
        selectAddress.setCustomer(customer);
        List<Address> addressList = addressService.findList(selectAddress);
        for (Address address : addressList) {
            if(id.equals(address.getId())){
                address.setIsDefault("1");
            }else {
                address.setIsDefault("0");
            }
            addressService.save(address);
        }
        return json;
    }

    @RequestMapping(value="delAddr")
    @ResponseBody
    public AjaxJson delAddr(String id,HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        Customer customer = (Customer) request.getSession().getAttribute("customer");
//        Customer customer  = customerService.get("dfbad77e2574458fa06b209ebbe9e6e5");
        if(customer == null){
            json.setSuccess(false);
            json.setMsg("请重新登录");
        }
        Address address = new Address();
        address.setId(id);
        addressService.delete(address);
        return json;
    }

    @RequestMapping(value="getAddr")
    @ResponseBody
    public AjaxJson getAddr(String id,HttpServletRequest request){
        AjaxJson json = new AjaxJson();
//        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Customer customer  = customerService.get("dfbad77e2574458fa06b209ebbe9e6e5");
        if(customer == null){
            json.setSuccess(false);
            json.setMsg("请重新登录");
        }
        Address address = addressService.get(id);

        if(address == null){
            json.setSuccess(false);
            json.setMsg("地址不存在");
            return json;
        }
        json.put("address", address);
        return json;
    }

    /**
     * 收货地址列表
     * @param request
     * @return
     */
    @RequestMapping(value="getAddressList")
    @ResponseBody
    public AjaxJson getAddressList(HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        Customer customer = (Customer) request.getSession().getAttribute("customer");
//        Customer customer  = customerService.get("dfbad77e2574458fa06b209ebbe9e6e5");
        //登录是否过期
        if(customer == null ){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }

        List<Address> addressList = addressService.getListByCustomerId(customer.getId());
        json.setMsg("总计"+addressList.size()+"条记录");
        json.put("data", addressList);
        return json;
    }

    /**
     * 获取成品楹联规格
     * @return
     */
    @RequestMapping(value="getCoupletsSize")
    @ResponseBody
    public AjaxJson getCoupletsSize(String type){
        AjaxJson json = new AjaxJson();
        CoupletsPrice coupletsPrice = new CoupletsPrice();
        //根据楹联类型查询尺寸和套餐
        coupletsPrice.setType(type);
        List<CoupletsPrice> coupletsPriceList = coupletsPriceService.findList(coupletsPrice);
        Map<String,String> sizeMap = new HashMap<>();
        List<Size> sizeList =  new ArrayList<>();
        //遍历couplets，并获取couplets中size和combo
        for (CoupletsPrice price : coupletsPriceList) {
            sizeMap.put(price.getSize().getId(), price.getSize().getName());
        }


        Iterator<Map.Entry<String,String>> sizeMapIt = sizeMap.entrySet().iterator();

        while (sizeMapIt.hasNext()){
            Map.Entry<String,String> sizeEntry = sizeMapIt.next();
            Size size = new Size();
            size.setId(sizeEntry.getKey());
            size.setName(sizeEntry.getValue());
            sizeList.add(size);
        }

        Collections.sort(sizeList, new Comparator<Size>() {
            @Override
            public int compare(Size o1, Size o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        json.put("sizeList", sizeList);

        return json;
    }

    /**
     * 获取成品楹联规格
     * @return
     */
    @RequestMapping(value="getCoupletsCombo")
    @ResponseBody
    public AjaxJson getCoupletsCombo(String type,String sizeId){
        AjaxJson json = new AjaxJson();
        CoupletsPrice coupletsPrice = new CoupletsPrice();
        Size size = new Size();
        size.setId(sizeId);
        //根据楹联类型查询尺寸和套餐
        coupletsPrice.setType(type);
        coupletsPrice.setSize(size);
        List<CoupletsPrice> coupletsPriceList = coupletsPriceService.findList(coupletsPrice);
        List<Combo> comboList = new ArrayList<>();

        for (CoupletsPrice price : coupletsPriceList) {
//            if(sizeId.equals(price.getSize().getId())){
                comboList.add(price.getCombo());
//            }
        }
        Collections.sort(comboList, new Comparator<Combo>() {
            @Override
            public int compare(Combo o1, Combo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        json.put("comboList", comboList);

        return json;
    }


    /**
     *
     * @param sizeId
     * @param comboId
     * @param type
     * @return
     */
    @RequestMapping(value="getCoupletsPrice")
    @ResponseBody
    public AjaxJson getCoupletsPrice(String sizeId,String comboId,String type){
        AjaxJson json = new AjaxJson();
        Size size = sizeService.get(sizeId);
        Combo combo = comboService.get(comboId);
        if(size == null || combo == null){
            json.setSuccess(false);
            json.setMsg("操作失败");
        }
        CoupletsPrice price = new CoupletsPrice();
        price.setSize(size);
        price.setCombo(combo);
        price.setType(type);
        List<CoupletsPrice> coupletsPriceServiceList = coupletsPriceService.findList(price);
        if(coupletsPriceServiceList.size() == 1){
            json.put("price", coupletsPriceServiceList.get(0));
        }else{
            json.setSuccess(false);
            json.setMsg("操作失败");
        }
        return json;
    }

    /**
     * 添加成品楹联订单
     * @param coupletsPriceId
     * @param coupletsId
     * @param request
     * @return
     */
    @RequestMapping(value="addCoupletsOrder")
    @ResponseBody
    public AjaxJson addCoupletsOrder(String coupletsPriceId, String coupletsId, HttpServletRequest request,Integer num,Double totalPrice){
        AjaxJson json = new AjaxJson();
        String customerId = (String) request.getSession().getAttribute("customerId");
//        customerId = "1538968164093";
        //登录是否过期
        if(customerId == null || "".equals(customerId)){
            json.setErrorCode("0");
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }

        //是否存在该用户
        Customer customer = customerService.get(customerId);
        boolean isExist = customer==null?false:true;
        if(!isExist){
            json.setErrorCode("0");
            json.setSuccess(false);
            json.setMsg("用户不存在");
            return json;
        }
        //成品楹联
        Couplets couplets = coupletsService.get(coupletsId);
        //成品楹联价格
        CoupletsPrice coupletsPrice = coupletsPriceService.get(coupletsPriceId);
        //查询收货地址参数
        Address selectAddress = new Address();
        //设置参数
        selectAddress.setCustomer(customer);
        selectAddress.setIsDefault("1");
        List<Address> addressList = addressService.findList(selectAddress);
        //收货地址
        Address address = null;
        for (Address addr : addressList) {
            //找到默认收货地址
            if("1".equals(addr.getIsDefault())){
                address = addr;
            }
        }
        //没有默认收货地址则返回失败
        if(address == null){
            json.setErrorCode("-1");
            json.setSuccess(false);
            json.setMsg("请设置默认收货地址");
            return json;
        }
        //成品楹联订单
        CoupletsOrder coupletsOrder = new CoupletsOrder();
        coupletsOrder.setAddress(address);
        coupletsOrder.setCouplets(couplets);
        coupletsOrder.setCoupletsPrice(coupletsPrice);
        coupletsOrder.setCustomer(customer);
        coupletsOrder.setInstaller(null);
        coupletsOrder.setNum(num);
        coupletsOrder.setTotalPrice(totalPrice);
        coupletsOrder.setStatus("1");
        //持久化
        coupletsOrderService.save(coupletsOrder);

        json.setErrorCode("1");
        json.setMsg("购买成功");
        json.put("orderId", coupletsOrder.getId());

        return json;
    }

    @RequestMapping(value="getLexiconSize")
    @ResponseBody
    public AjaxJson getLexiconSize(String type){
        AjaxJson json = new AjaxJson();
        List<Typeface> typefaceList = typefaceService.findList(new Typeface());
        //查询此类型下的所有价格
        LexiconPrice lexiconPrice = new LexiconPrice();
        lexiconPrice.setType(type);
        List<LexiconPrice> lexiconPriceList = lexiconPriceService.findList(lexiconPrice);

        List<Size> sizeList = new ArrayList<>();
        Map<String,String> sizeMap = new HashMap<>();
        //去除重复的size
        for (LexiconPrice price : lexiconPriceList) {
            sizeMap.put(price.getSize().getId(), price.getSize().getName());
        }

        Iterator<Map.Entry<String,String>> sizeMapIt = sizeMap.entrySet().iterator();

        while(sizeMapIt.hasNext()){
            Map.Entry<String,String> entry = sizeMapIt.next();
            Size size = new Size();
            size.setId(entry.getKey());
            size.setName(entry.getValue());
            sizeList.add(size);
        }
        Collections.sort(sizeList, new Comparator<Size>() {
            @Override
            public int compare(Size o1, Size o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        json.put("sizeList", sizeList);
        json.put("typefaceList", typefaceList);

        return json;
    }

    /**
     * 获取成品楹联规格
     * @return
     */
    @RequestMapping(value="getLexiconCombo")
    @ResponseBody
    public AjaxJson getLexiconCombo(String type,String sizeId,String typefaceId){
        AjaxJson json = new AjaxJson();
        //查询符合条件的价格list
        LexiconPrice lexiconPrice = new LexiconPrice();
        Size size = new Size();
        size.setId(sizeId);
        Typeface typeface = new Typeface();
        typeface.setId(typefaceId);
        lexiconPrice.setTypeface(typeface);
        lexiconPrice.setSize(size);
        lexiconPrice.setType(type);

        List<LexiconPrice> lexiconPriceList = lexiconPriceService.findList(lexiconPrice);

        List<Combo> comboList = new ArrayList<>();
        //取出combo
        for (LexiconPrice price : lexiconPriceList) {
                comboList.add(price.getCombo());
        }
        //按照combo.name进行排序
        Collections.sort(comboList, new Comparator<Combo>() {
            @Override
            public int compare(Combo o1, Combo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        json.put("comboList", comboList);

        return json;
    }


    @RequestMapping(value="getLexiconPrice")
    @ResponseBody
    public AjaxJson getLexiconPrice(String sizeId,String typefaceId,String comboId,String type){
        AjaxJson json = new AjaxJson();
        if("".equals(sizeId) || "".equals(typefaceId) || "".equals(type) ||"".equals(comboId)){
            json.setSuccess(false);
            json.setMsg("请输入规格");
        }
        Size size = new Size();
        size.setId(sizeId);
        Typeface typeface = new Typeface();
        typeface.setId(typefaceId);
        Combo combo = new Combo();
        combo.setId(comboId);

        LexiconPrice price = new LexiconPrice();
        price.setSize(size);
        price.setTypeface(typeface);
        price.setCombo(combo);
        price.setType(type);
        List<LexiconPrice> lexiconPriceList = lexiconPriceService.findList(price);

        if(lexiconPriceList.size() == 1){
            json.put("price", lexiconPriceList.get(0));
        }else{
            json.setSuccess(false);
            json.setMsg("操作失败");
        }
        return json;
    }

    @RequestMapping(value="addLexiconOrder")
    @ResponseBody
    public AjaxJson addLexiconOrder(String lexiconPriceId, String lexiconId, HttpServletRequest request,Integer num,Double totalPrice){
        AjaxJson json = new AjaxJson();
        String customerId = (String) request.getSession().getAttribute("customerId");
//        customerId = "dfbad77e2574458fa06b209ebbe9e6e5";
        //登录是否过期
        if(customerId == null || "".equals(customerId)){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }

        //是否存在该用户
        Customer customer = customerService.get(customerId);
        boolean isExist = customer==null?false:true;
        if(!isExist){
            json.setErrorCode("0");
            json.setSuccess(false);
            json.setMsg("用户不存在");
            return json;
        }
        //成品楹联
        Lexicon lexicon = lexiconService.get(lexiconId);
        //成品楹联价格
        LexiconPrice lexiconPrice = lexiconPriceService.get(lexiconPriceId);
        //查询收货地址参数
        Address selectAddress = new Address();
        //设置参数
        selectAddress.setCustomer(customer);
        selectAddress.setIsDefault("1");
        List<Address> addressList = addressService.findList(selectAddress);
        //收货地址
        Address address = null;
        for (Address addr : addressList) {
            //找到默认收货地址
            if("1".equals(addr.getIsDefault())){
                address = addr;
            }
        }
        //没有默认收货地址则返回失败
        if(address == null){
            json.setErrorCode("-1");
            json.setSuccess(false);
            json.setMsg("请设置默认收货地址");
            return json;
        }
        //成品楹联订单
//        CoupletsOrder coupletsOrder = new CoupletsOrder();
        LexiconOrder lexiconOrder = new LexiconOrder();
        lexiconOrder.setCustomer(customer);
        lexiconOrder.setAddress(address);
        lexiconOrder.setLexicon(lexicon);
        lexiconOrder.setLexiconPrice(lexiconPrice);
        lexiconOrder.setInstaller(null);
        lexiconOrder.setStatus("1");
        lexiconOrder.setNum(num);

        lexiconOrder.setTotalPrice(totalPrice);
        lexiconOrderService.save(lexiconOrder);

        json.setErrorCode("1");
        json.setMsg("购买成功");
        json.put("orderId", lexiconOrder.getId());

        return json;
    }

    /**
     * 添加其他成品订单
     * @param type
     * @param finishedId
     * @param price
     * @param request
     * @return
     */
    @RequestMapping(value="addFinishedOrder")
    @ResponseBody
    public AjaxJson addFinishedOrder(String type, String finishedId,Double price, Integer num,HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        String customerId = (String) request.getSession().getAttribute("customerId");
//        customerId = "1538968164093";
        //登录是否过期
        if(customerId == null || "".equals(customerId)){
            json.setErrorCode("0");
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }

        //是否存在该用户
        Customer customer = customerService.get(customerId);
        boolean isExist = customer==null?false:true;
        if(!isExist){
            json.setErrorCode("0");
            json.setSuccess(false);
            json.setMsg("用户不存在");
            return json;
        }
        String finishedName = "";
        if("1".equals(type)){
            NewYearPic newYearPic = newYearPicService.get(finishedId);
            finishedName = newYearPic.getTitle();
        };
        if("3".equals(type)){
            Painting painting = paintingService.get(finishedId);
            finishedName = painting.getTitle();
        };
        if("2".equals(type)){
            Calligraphy calligraphy = calligraphyService.get(finishedId);
            finishedName = calligraphy.getTitle();
        };
        if("4".equals(type)){
            Decoration decoration = decorationService.get(finishedId);
            finishedName = decoration.getTitle();
        };
        //查询收货地址参数
        Address selectAddress = new Address();
        //设置参数
        selectAddress.setCustomer(customer);
        selectAddress.setIsDefault("1");
        List<Address> addressList = addressService.findList(selectAddress);
        //收货地址
        Address address = null;
        for (Address addr : addressList) {
            //找到默认收货地址
            if("1".equals(addr.getIsDefault())){
                address = addr;
            }
        }
        //没有默认收货地址则返回失败
        if(address == null){
            json.setErrorCode("-1");
            json.setSuccess(false);
            json.setMsg("请设置默认收货地址");
            return json;
        }
        //成品楹联订单
        FinishedOrder finishedOrder = new FinishedOrder();
        finishedOrder.setType(type);
        finishedOrder.setName(finishedName);
        finishedOrder.setFinishedId(finishedId);
        finishedOrder.setPrice(price);
        finishedOrder.setAddress(address);
        finishedOrder.setCustomer(customer);
        finishedOrder.setInstaller(null);
        finishedOrder.setStatus("1");
        finishedOrder.setNum(num);
        finishedOrderService.save(finishedOrder);

        json.setErrorCode("1");
        json.setMsg("购买成功");
        json.put("orderId", finishedOrder.getId());
        return json;
    }


    @RequestMapping(value="getOrderList")
    @ResponseBody
    public AjaxJson getOrderList(HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        String customerId = (String) request.getSession().getAttribute("customerId");
//        customerId = "1538968164093";
        //登录是否过期
        if(customerId == null || "".equals(customerId)){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }

        //是否存在该用户
        Customer customer = customerService.get(customerId);
        boolean isExist = customer==null?false:true;
        if(!isExist){
            json.setSuccess(false);
            json.setMsg("用户不存在");
            return json;
        }

        CoupletsOrder selectCoupletsOrder = new CoupletsOrder();
        selectCoupletsOrder.setCustomer(customer);
        List<CoupletsOrder> coupletsOrderList = coupletsOrderService.findList(selectCoupletsOrder);

        LexiconOrder selectLexiconOrder = new LexiconOrder();
        selectLexiconOrder.setCustomer(customer);
        List<LexiconOrder> lexiconOrderList = lexiconOrderService.findList(selectLexiconOrder);

        FinishedOrder selectFinishedOrder = new FinishedOrder();
        selectFinishedOrder.setCustomer(customer);
        List<FinishedOrder> finishedOrderList = finishedOrderService.findList(selectFinishedOrder);

        json.put("coupletsOrderList", coupletsOrderList);
        json.put("lexiconOrderList", lexiconOrderList);
        json.put("finishedOrderList", finishedOrderList);


        return json;
    }

    /**
     * 删除订单
     * @param orderId
     * @param orderType
     * @return
     */
    @RequestMapping(value="delOrder")
    @ResponseBody
    public AjaxJson delOrder(String orderId,String orderType,HttpServletRequest request){

        AjaxJson json = new AjaxJson();
        String customerId = (String) request.getSession().getAttribute("customerId");
        //登录是否过期
        if(customerId == null || "".equals(customerId)){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }
        if(orderId == null || orderType == null){
            json.setSuccess(false);
            json.setMsg("订单不存在");
            return json;
        }

        if("1".equals(orderType)){
            CoupletsOrder order = coupletsOrderService.get(orderId);
            if(order == null){
                json.setSuccess(false);
                json.setMsg("订单不存在");
                return json;
            }
            coupletsOrderService.delete(order);
            System.out.println("***************************************************************************");
        }
        if("2".equals(orderType)){
            LexiconOrder order = lexiconOrderService.get(orderId);
            if(order == null){
                json.setSuccess(false);
                json.setMsg("订单不存在");
                return json;
            }
            lexiconOrderService.delete(order);
            System.out.println("***************************************************************************");
        }
        if("3".equals(orderType)){
            FinishedOrder order = finishedOrderService.get(orderId);
            if(order == null){
                json.setSuccess(false);
                json.setMsg("订单不存在");
                return json;
            }
            finishedOrderService.delete(order);
            System.out.println("***************************************************************************");
        }

        return json;
    }

    /**
     * 确认订单
     * @param orderId
     * @param orderType
     * @return
     */
    @RequestMapping(value="ensureOrder")
    @ResponseBody
    public AjaxJson ensureOrder(String orderId,String orderType,HttpServletRequest request){
        AjaxJson json = new AjaxJson();
        String customerId = (String) request.getSession().getAttribute("customerId");
        //登录是否过期
        if(customerId == null || "".equals(customerId)){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }
        if(orderId == null || orderType == null){
            json.setSuccess(false);
            json.setMsg("请订单不存在");
            return json;
        }
        if("1".equals(orderType)){
            CoupletsOrder order = coupletsOrderService.get(orderId);
            if(order == null){
                json.setSuccess(false);
                json.setMsg("请订单不存在");
                return json;
            }
            order.setStatus("3");
            coupletsOrderService.save(order);
            System.out.println("***************************************************************************");
        }
        if("2".equals(orderType)){
            LexiconOrder order = lexiconOrderService.get(orderId);
            if(order == null){
                json.setSuccess(false);
                json.setMsg("请订单不存在");
                return json;
            }
            order.setStatus("3");
            lexiconOrderService.save(order);
            System.out.println("***************************************************************************");
        }
        if("3".equals(orderType)){
            FinishedOrder order = finishedOrderService.get(orderId);
            if(order == null){
                json.setSuccess(false);
                json.setMsg("请订单不存在");
                return json;
            }
            order.setStatus("3");
            finishedOrderService.save(order);
            System.out.println("***************************************************************************");
        }
        return json;
    }

    /**
     * 公告列表
     * @return
     */
    @RequestMapping(value="getNoticeList")
    @ResponseBody
    public AjaxJson getNoticeList() throws ParseException {
        AjaxJson json = new AjaxJson();
        List<Notice> noticeList = noticeService.findList(new Notice());
        if(noticeList.size() == 0){
            json.setSuccess(false);
            json.setMsg("暂无公告");
            return json;
        }
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        for (Notice notice : noticeList) {
            notice.setCurrentUser(null);
            notice.setDataScope(null);
            notice.setPage(null);
            notice.setCreateBy(null);
            notice.setUpdateBy(null);
            notice.setDetails(null);
        }
        json.put("noticeList", noticeList);
        return json;
    }

    /**
     * 公告详情
     * @param id
     * @return
     */
    @RequestMapping(value="getNoticeDetails")
    @ResponseBody
    public AjaxJson getNoticeDetails(String id){
        AjaxJson json = new AjaxJson();
        Notice notice = noticeService.get(id);
        if(notice == null){
            json.setSuccess(false);
            json.setMsg("公告不存在");
            return json;
        }
            notice.setCurrentUser(null);
            notice.setDataScope(null);
            notice.setPage(null);
            notice.setCreateBy(null);
            notice.setUpdateBy(null);
        json.put("notice", notice);
        return json;
    }

    /**
     * 平台简介
     * @return
     */
    @RequestMapping(value="getPlatIntro")
    @ResponseBody
    public AjaxJson getPlatIntro(){
        AjaxJson json = new AjaxJson();
        List<PlatIntro> platIntroList = platIntroService.findList(new PlatIntro());
        Author selectAuthor = new Author();
        selectAuthor.setType("4");
        List<Author> authorList = authorService.findList(selectAuthor);
        if(platIntroList.size() == 0 || authorList.size() == 0 ){
            json.setSuccess(false);
            json.setMsg("无数据");
            return json;
        }
        PlatIntro platIntro = platIntroList.get(0);
        json.put("platIntro", platIntro);
        json.put("authorList", authorList);
        return json;
    }

    /**
     * 客服联系方式
     * @return
     */
    @RequestMapping(value="getCustomerServ")
    @ResponseBody
    public AjaxJson getCustomerServ(){
        AjaxJson json = new AjaxJson();
        List<CustomerServ> customerServList = customerServService.findList(new CustomerServ());
        if(customerServList.size() == 0 || customerServList.size() == 0 ){
            json.setSuccess(false);
            json.setMsg("无数据");
            return json;
        }
        CustomerServ customerServ = customerServList.get(0);
        json.put("customerServ", customerServ);
        return json;
    }

    /**
     * 帮助
     * @return
     */
    @RequestMapping(value="getPlatHelp")
    @ResponseBody
    public AjaxJson getPlatHelp(){
        AjaxJson json = new AjaxJson();
        List<PlatHelp> platHelpList = platHelpService.findList(new PlatHelp());
        if(platHelpList.size() == 0){
            json.setSuccess(false);
            json.setMsg("无数据");
            return json;
        }
        PlatHelp platHelp = platHelpList.get(0);
        json.put("platHelp", platHelp);
        return json;
    }

    /**
     * 用户留言
      * @param msg
     * @param request
     * @return
     */
    @RequestMapping(value="addLeaveMsg")
    @ResponseBody
    public AjaxJson addLeaveMsg(String msg,HttpServletRequest request){

        AjaxJson json = new AjaxJson();
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        //登录是否过期
        if(customer == null){
            json.setSuccess(false);
            json.setMsg("登录已过期，请重新授权登录");
            return json;
        }
        if(StringUtils.isEmpty(msg)){
            json.setSuccess(false);
            json.setMsg("请输入内容");
            return json;
        }
        LeaveMsg leaveMsg = new LeaveMsg();
        leaveMsg.setCustomer(customer);
        leaveMsg.setContent(msg);
        leaveMsgService.save(leaveMsg);
        return json;
    }




   }
