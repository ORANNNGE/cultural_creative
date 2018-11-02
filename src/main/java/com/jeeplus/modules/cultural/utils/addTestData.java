package com.jeeplus.modules.cultural.utils;

import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.entity.couplets.Lexicon;
import com.jeeplus.modules.cultural.entity.order.Combo;
import com.jeeplus.modules.cultural.entity.order.CoupletsPrice;
import com.jeeplus.modules.cultural.entity.order.LexiconPrice;
import com.jeeplus.modules.cultural.entity.role.Author;
import com.jeeplus.modules.cultural.entity.spec.Craft;
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.entity.spec.Typeface;
import com.jeeplus.modules.cultural.service.couplets.CoupletsService;
import com.jeeplus.modules.cultural.service.couplets.LexiconService;
import com.jeeplus.modules.cultural.service.order.ComboService;
import com.jeeplus.modules.cultural.service.order.CoupletsPriceService;
import com.jeeplus.modules.cultural.service.order.LexiconPriceService;
import com.jeeplus.modules.cultural.service.role.AuthorService;
import com.jeeplus.modules.cultural.service.spec.CraftService;
import com.jeeplus.modules.cultural.service.spec.FrameService;
import com.jeeplus.modules.cultural.service.spec.SizeService;
import com.jeeplus.modules.cultural.service.spec.TypefaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
@Controller
public class addTestData {

    @Autowired
    CoupletsService coupletsService;
    @Autowired
    CoupletsPriceService coupletsPriceService;
    @Autowired
    FrameService frameService;
    @Autowired
    SizeService sizeService;
    @Autowired
    CraftService craftService;
    @Autowired
    AuthorService authorService;
    @Autowired
    TypefaceService typefaceService;
    @Autowired
    LexiconService lexiconService;
    @Autowired
    LexiconPriceService lexiconPriceService;
    @Autowired
    ComboService comboService;
    @RequestMapping("addCoupletsPrice")
    @ResponseBody
    public String addCoupletsPrice(){
        List<Couplets> coupletsList = coupletsService.findList(new Couplets());
        List<Size> sizeList = sizeService.findList(new Size());
        List<Combo> comboList = comboService.findList(new Combo());
        List<String> types = new ArrayList<>();
        types.add("1");
        types.add("2");
        types.add("3");
        List<CoupletsPrice> coupletsPriceList = new ArrayList<>();
        int count = 0;
                for (int k = 0; k < sizeList.size(); k++) {
                    for (int j = 0; j < comboList.size(); j++) {
                        for (int l = 0; l < types.size(); l++) {
                            CoupletsPrice coupletsPrice = new CoupletsPrice();
                            coupletsPrice.setSize(sizeList.get(k));
                            coupletsPrice.setSizeName(sizeList.get(k).getName());
                            coupletsPrice.setCombo(comboList.get(j));
                            coupletsPrice.setComboName(comboList.get(j).getName());
                            coupletsPrice.setType(types.get(l));
                            coupletsPrice.setPrice(Double.valueOf(new DecimalFormat("#.00").format(Math.random()*100+100)));
                            coupletsPriceList.add(coupletsPrice);
                        }

                    }
                }
        System.out.println(coupletsPriceList.size());
        for (CoupletsPrice coupletsPrice : coupletsPriceList) {
            System.out.println(
                    coupletsPrice.getSize().getName()+","+
                    coupletsPrice.getSizeName()+","+
                    coupletsPrice.getComboName()+","+
                    coupletsPrice.getPrice()
            );
            coupletsPriceService.save(coupletsPrice);
        }
        return "success";
    }

    @RequestMapping("addLexiconPrice")
    @ResponseBody
    public String addLexiconPrice(){
        List<Size> sizeList = sizeService.findList(new Size());
        List<Lexicon> lexiconList = lexiconService.findList(new Lexicon());
        Author selectAuthor = new Author();
        selectAuthor.setType("1");
        List<Typeface> typefaceList = typefaceService.findList(new Typeface());
        List<Combo> comboList = comboService.findList(new Combo());
        List<String> types = new ArrayList<>();
        types.add("1");
        types.add("2");
        types.add("3");
        List<LexiconPrice> lexiconPriceList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < types.size(); i++) {
                for (int k = 0; k < sizeList.size(); k++) {
                    for (int l = 0; l < typefaceList.size(); l++) {
                        for (int m = 0; m < comboList.size(); m++) {
                            LexiconPrice price = new LexiconPrice();
                            price.setType(types.get(i));
                            price.setSize(sizeList.get(k));
                            price.setSizeName(sizeList.get(k).getName());
                            price.setTypeface(typefaceList.get(l));
                            price.setTypefaceName(typefaceList.get(l).getName());
                            price.setCombo(comboList.get(m));
                            price.setComboName(comboList.get(m).getName());

                            price.setPrice(Double.valueOf(new DecimalFormat("#.00").format(Math.random()*100+100)));
                            lexiconPriceList.add(price);
                        }
                    }
                }


        }
        System.out.println(lexiconPriceList.size());
        for (LexiconPrice lexiconPrice : lexiconPriceList) {
            System.out.println(
                    lexiconPrice.getSizeName()+","+
                    lexiconPrice.getTypefaceName()+","+
                    lexiconPrice.getComboName()+","+
                    lexiconPrice.getPrice()
            );
            lexiconPriceService.save(lexiconPrice);
        }
        return "success";
    }


}
