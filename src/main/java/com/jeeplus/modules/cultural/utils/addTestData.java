package com.jeeplus.modules.cultural.utils;

import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.entity.couplets.Lexicon;
import com.jeeplus.modules.cultural.entity.order.CoupletsPrice;
import com.jeeplus.modules.cultural.entity.order.LexiconPrice;
import com.jeeplus.modules.cultural.entity.role.Author;
import com.jeeplus.modules.cultural.entity.spec.Craft;
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.entity.spec.Typeface;
import com.jeeplus.modules.cultural.service.couplets.CoupletsService;
import com.jeeplus.modules.cultural.service.couplets.LexiconService;
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
    @RequestMapping("addCoupletsPrice")
    public void addCoupletsPrice(){
        List<Couplets> coupletsList = coupletsService.findList(new Couplets());
        List<Frame> frameList = frameService.findList(new Frame());
        List<Size> sizeList = sizeService.findList(new Size());
        List<Craft> craftList = craftService.findList(new Craft());

        List<CoupletsPrice> coupletsPriceList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < coupletsList.size(); i++) {
            for (int j = 0; j < frameList.size(); j++) {
                for (int k = 0; k < sizeList.size(); k++) {
                    for (int l = 0; l < craftList.size(); l++) {
                        CoupletsPrice coupletsPrice = new CoupletsPrice();
                        coupletsPrice.setCouplets(coupletsList.get(i));
                        coupletsPrice.setFrame(frameList.get(j));
//                        coupletsPrice.setFrameName(frameList.get(j).getName());
                        coupletsPrice.setSize(sizeList.get(k));
//                        coupletsPrice.setSizeName(sizeList.get(k).getName());
                        coupletsPrice.setCraft(craftList.get(l));
//                        coupletsPrice.setCraftName(craftList.get(l).getName());

                        coupletsPrice.setPrice(Double.valueOf(new DecimalFormat("#.00").format(Math.random()*100+100)));
                        coupletsPriceList.add(coupletsPrice);
                    }
                }

            }

        }
        System.out.println(coupletsPriceList.size());
        for (CoupletsPrice coupletsPrice : coupletsPriceList) {
            System.out.println(
                    coupletsPrice.getCouplets().getName()+","+
                    coupletsPrice.getSize().getName()+","+
                    coupletsPrice.getCraft().getName()+","+
                    coupletsPrice.getFrame().getName()+","+
                    coupletsPrice.getPrice()
            );
            coupletsPriceService.save(coupletsPrice);
        }
    }

    @RequestMapping("addLexiconPrice")
    public void addLexiconPrice(){
        List<Frame> frameList = frameService.findList(new Frame());
        List<Size> sizeList = sizeService.findList(new Size());
        List<Craft> craftList = craftService.findList(new Craft());
        List<Lexicon> lexiconList = lexiconService.findList(new Lexicon());
        Author selectAuthor = new Author();
        selectAuthor.setType("1");
        List<Author> authorList = authorService.findList(selectAuthor);
        List<Typeface> typefaceList = typefaceService.findList(new Typeface());


        List<LexiconPrice> lexiconPriceList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < lexiconList.size(); i++) {
            for (int j = 0; j < frameList.size(); j++) {
                for (int k = 0; k < sizeList.size(); k++) {
                    for (int l = 0; l < craftList.size(); l++) {
                        for (int m = 0; m < authorList.size(); m++) {
                            /*
                             *书法家和字体只能选一项，所以分开插入
                             */
                            if(m == authorList.size()-1){
                                for (int n = 0; n < typefaceList.size(); n++) {
                                    LexiconPrice price = new LexiconPrice();
                                    price.setFrame(frameList.get(j));
                                    price.setSize(sizeList.get(k));
                                    price.setCraft(craftList.get(l));
                                    price.setTypeface(typefaceList.get(n));
                                    price.setPrice(Double.valueOf(new DecimalFormat("#.00").format(Math.random()*100+100)));
                                    price.setLexicon(lexiconList.get(i));
                                    lexiconPriceList.add(price);
                                }
                            }
                            LexiconPrice price = new LexiconPrice();
                            price.setLexicon(lexiconList.get(i));
                            price.setFrame(frameList.get(j));
                            price.setSize(sizeList.get(k));
                            price.setCraft(craftList.get(l));
                            price.setAuthor(authorList.get(m));
                            price.setPrice(Double.valueOf(new DecimalFormat("#.00").format(Math.random()*100+100)));
                            lexiconPriceList.add(price);
                        }

                    }
                }

            }

        }
        System.out.println(lexiconPriceList.size());
        for (LexiconPrice lexiconPrice : lexiconPriceList) {
            System.out.println(
                    lexiconPrice.getSize().getName()+","+
                    lexiconPrice.getCraft().getName()+","+
                    lexiconPrice.getFrame().getName()+","+
                    lexiconPrice.getPrice()
            );
            lexiconPriceService.save(lexiconPrice);
        }
    }

}
