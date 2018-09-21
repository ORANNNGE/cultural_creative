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
import com.jeeplus.modules.cultural.service.couplets.CoupletsService;
import com.jeeplus.modules.cultural.service.couplets.LexiconService;
import com.jeeplus.modules.cultural.service.finished.*;
import com.jeeplus.modules.cultural.utils.PageUtils;
import com.jeeplus.modules.sys.entity.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.List;

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
}
