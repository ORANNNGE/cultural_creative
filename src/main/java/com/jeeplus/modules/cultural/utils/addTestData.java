package com.jeeplus.modules.cultural.utils;

import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.entity.order.CoupletsPrice;
import com.jeeplus.modules.cultural.entity.spec.Craft;
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.service.couplets.CoupletsService;
import com.jeeplus.modules.cultural.service.order.CoupletsPriceService;
import com.jeeplus.modules.cultural.service.spec.CraftService;
import com.jeeplus.modules.cultural.service.spec.FrameService;
import com.jeeplus.modules.cultural.service.spec.SizeService;
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
                        coupletsPrice.setSize(sizeList.get(k));
                        coupletsPrice.setCraft(craftList.get(l));
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

}
