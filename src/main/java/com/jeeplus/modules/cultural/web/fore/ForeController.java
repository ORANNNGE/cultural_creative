package com.jeeplus.modules.cultural.web.fore;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.entity.couplets.Lexicon;
import com.jeeplus.modules.cultural.entity.finished.Calligraphy;
import com.jeeplus.modules.cultural.entity.finished.Decoration;
import com.jeeplus.modules.cultural.entity.finished.NewYearPic;
import com.jeeplus.modules.cultural.entity.finished.Painting;
import com.jeeplus.modules.cultural.entity.order.*;
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
import com.jeeplus.modules.cultural.service.role.AuthorService;
import com.jeeplus.modules.cultural.service.role.CustomerService;
import com.jeeplus.modules.cultural.service.spec.CraftService;
import com.jeeplus.modules.cultural.service.spec.FrameService;
import com.jeeplus.modules.cultural.service.spec.SizeService;
import com.jeeplus.modules.cultural.service.spec.TypefaceService;
import com.jeeplus.modules.cultural.utils.MsgUtil;
import com.jeeplus.modules.cultural.utils.PageUtils;
import com.jeeplus.modules.sys.entity.Log;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
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
        //机关 楹联
        List<Couplets>  government = new ArrayList<>();
        //单位 楹联
        List<Couplets>  company = new ArrayList<>();
        //家庭 楹联
        List<Couplets>  home = new ArrayList<>();
        // 分别给三种楹联赋值
        for (Couplets c : couplets) {
            if("1".equals(c.getLexicon().getType()) && government.size() < 4){
                government.add(c);
            }
            if("2".equals(c.getLexicon().getType()) && company.size() < 4){
                  company.add(c);
            }
            if("3".equals(c.getLexicon().getType()) && home.size() < 4){
                home.add(c);
            }
        }
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
        AjaxJson json = new AjaxJson();
        //调用PageHelper静态方法startPage()，必须要在查询数据库之前调用
        PageHelper.startPage(pageNum,pageSize);
        List<Couplets> coupletsList = coupletsService.getCoupletsList(type);
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
    public AjaxJson getLexiconList(Integer pageSize,Integer pageNum){
        AjaxJson json = new AjaxJson();
        //调用PageHelper静态方法startPage()，必须要在查询数据库之前调用
        PageHelper.startPage(pageNum,pageSize);
        List<Lexicon> lexiconList = lexiconService.findList(new Lexicon());
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

    @RequestMapping(value="getVerifyCode")
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
    }

    @RequestMapping(value="bindPhoneNum")
    @ResponseBody
    public AjaxJson bindPhoneNum(String verifyCode,HttpServletRequest request){
        AjaxJson json = new AjaxJson();

        //从session 获取用户id
        String customerId = (String)request.getSession().getAttribute("customerId");
        //从session 获取用户之前验证的手机号
        String phoneNum = (String)request.getSession().getAttribute("verifyPhoneNum");
        //从session 获取发送的验证码
        String code = (String)request.getSession().getAttribute("code");

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

        if(verifyCode == null || "".equals(verifyCode)){
            json.setSuccess(false);
            json.setMsg("请输入验证码");
            return json;
        }

        //验证手机号
        if(!verifyCode.equals(code)){
            json.setSuccess(false);
            json.setMsg("验证码不正确");
            return json;
        }

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
        String customerId = (String)request.getSession().getAttribute("customerId");
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
        address.setCustomer(customer);
        address.setIsDefault("0");
        addressService.save(address);
        json.setMsg("添加成功");
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
        String customerId = (String)request.getSession().getAttribute("customerId");

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

        List<Address> addressList = addressService.getListByCustomerId(customerId);
        json.setMsg("总计"+addressList.size()+"条记录");
        json.put("data", addressList);
        return json;
    }

    /**
     * 获取成品楹联规格
     * @return
     */
    @RequestMapping(value="getCoupletsSpec")
    @ResponseBody
    public AjaxJson getCoupletsSpec(){
        AjaxJson json = new AjaxJson();
        List<Size> sizeList = sizeService.findList(new Size());
        List<Frame> frameList = frameService.findList(new Frame());
        List<Craft> craftList = craftService.findList(new Craft());

        json.put("sizeList", sizeList);
        json.put("frameList", frameList);
        json.put("craftList", craftList);

        return json;
    }
    

    /**
     * 获取成品楹联价格
     * @param sizeId
     * @param frameId
     * @param craftId
     * @param coupletsId
     * @return
     */
    @RequestMapping(value="getCoupletsPrice")
    @ResponseBody
    public AjaxJson getCoupletsPrice(String sizeId,String frameId,String craftId,String coupletsId){
        AjaxJson json = new AjaxJson();
        Size size = sizeService.get(sizeId);
        Frame frame = frameService.get(frameId);
        Craft craft = craftService.get(craftId);
        Couplets couplets = coupletsService.get(coupletsId);
        CoupletsPrice price = new CoupletsPrice();
        price.setSize(size);
        price.setFrame(frame);
        price.setCraft(craft);
        price.setCouplets(couplets);

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
            json.setSuccess(false);
            json.setMsg("请设置默认收货地址");
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
        json.setMsg("购买成功");
        return json;
    }

    @RequestMapping(value="getLexiconSpec")
    @ResponseBody
    public AjaxJson getLexiconSpec(){
        AjaxJson json = new AjaxJson();
        List<Size> sizeList = sizeService.findList(new Size());
        List<Frame> frameList = frameService.findList(new Frame());
        List<Craft> craftList = craftService.findList(new Craft());
        List<Typeface> typefaceList = typefaceService.findList(new Typeface());
        List<Author> authors = authorService.findList(new Author());
        List<Author> authorList = new ArrayList<>();
        //只返回书法家
        for (Author author : authors) {
            if("1".equals(author.getType())){
                authorList.add(author);
            }
        }
        json.put("sizeList", sizeList);
        json.put("frameList", frameList);
        json.put("craftList", craftList);
        json.put("typefaceList", typefaceList);
        json.put("authorList", authorList);

        return json;
    }

    /**
     *
     * @param sizeId
     * @param frameId
     * @param craftId
     * @param lexiconId
     * @param authorId
     * @param typefaceId
     * @return
     */
    @RequestMapping(value="getLexiconPrice")
    @ResponseBody
    public AjaxJson getLexiconPrice(String sizeId,String frameId,String craftId,String lexiconId,String authorId,String typefaceId){
        AjaxJson json = new AjaxJson();
        Size size = sizeService.get(sizeId);
        Frame frame = frameService.get(frameId);
        Craft craft = craftService.get(craftId);
        Lexicon lexicon = lexiconService.get(lexiconId);
        LexiconPrice price = new LexiconPrice();
        price.setSize(size);
        price.setFrame(frame);
        price.setCraft(craft);
        price.setLexicon(lexicon);
        if(authorId != null){
            Author author = authorService.get(authorId);
            price.setAuthor(author);
        }
        if(typefaceId != null){
            Typeface typeface = typefaceService.get(typefaceId);
            price.setTypeface(typeface);
        }
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
            json.setSuccess(false);
            json.setMsg("请设置默认收货地址");
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

        return json;
    }

    @RequestMapping(value="addFinishedOrder")
    @ResponseBody
    public AjaxJson addFinishedOrder(String type, String finishedId, HttpServletRequest request,Integer num,Double totalPrice,Double price){
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
        String finishedName = "";
        if("1".equals(type)){
            NewYearPic newYearPic = newYearPicService.get(finishedId);
            finishedName = newYearPic.getTitle();
        };
        if("2".equals(type)){
            Painting painting = paintingService.get(finishedId);
            finishedName = painting.getTitle();
        };
        if("3".equals(type)){
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
            json.setSuccess(false);
            json.setMsg("请设置默认收货地址");
        }
        //成品楹联订单
        FinishedOrder finishedOrder = new FinishedOrder();
        finishedOrder.setFinishedName(finishedName);
        finishedOrder.setNum(num);
        finishedOrder.setFinishedId(finishedId);
        finishedOrder.setPrice(price);
        finishedOrder.setAddress(address);
        finishedOrder.setCustomer(customer);
        finishedOrder.setInstaller(null);
        finishedOrderService.save(finishedOrder);
        return json;
    }

}
