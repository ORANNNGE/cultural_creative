/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.order;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.cultural.entity.order.CoupletsPrice;
import com.jeeplus.modules.cultural.service.order.CoupletsPriceService;

/**
 * 成品楹联价格Controller
 * @author orange
 * @version 2018-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/order/coupletsPrice")
public class CoupletsPriceController extends BaseController {

	@Autowired
	private CoupletsPriceService coupletsPriceService;
	
	@ModelAttribute
	public CoupletsPrice get(@RequestParam(required=false) String id) {
		CoupletsPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = coupletsPriceService.get(id);
		}
		if (entity == null){
			entity = new CoupletsPrice();
		}
		return entity;
	}
	
	/**
	 * 成品楹联价格列表页面
	 */
	@RequiresPermissions("cultural:order:coupletsPrice:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/order/coupletsPriceList";
	}
	
		/**
	 * 成品楹联价格列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:coupletsPrice:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CoupletsPrice coupletsPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CoupletsPrice> page = coupletsPriceService.findPage(new Page<CoupletsPrice>(request, response), coupletsPrice); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑成品楹联价格表单页面
	 */
	@RequiresPermissions(value={"cultural:order:coupletsPrice:view","cultural:order:coupletsPrice:add","cultural:order:coupletsPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CoupletsPrice coupletsPrice, Model model) {
		model.addAttribute("coupletsPrice", coupletsPrice);
		return "modules/cultural/order/coupletsPriceForm";
	}

	/**
	 * 保存成品楹联价格
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:order:coupletsPrice:add","cultural:order:coupletsPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CoupletsPrice coupletsPrice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, coupletsPrice)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		coupletsPriceService.save(coupletsPrice);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存成品楹联价格成功");
		return j;
	}
	
	/**
	 * 删除成品楹联价格
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:coupletsPrice:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CoupletsPrice coupletsPrice, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		coupletsPriceService.delete(coupletsPrice);
		j.setMsg("删除成品楹联价格成功");
		return j;
	}
	
	/**
	 * 批量删除成品楹联价格
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:coupletsPrice:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			coupletsPriceService.delete(coupletsPriceService.get(id));
		}
		j.setMsg("删除成品楹联价格成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:coupletsPrice:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CoupletsPrice coupletsPrice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "成品楹联价格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CoupletsPrice> page = coupletsPriceService.findPage(new Page<CoupletsPrice>(request, response, -1), coupletsPrice);
    		new ExportExcel("成品楹联价格", CoupletsPrice.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出成品楹联价格记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:order:coupletsPrice:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CoupletsPrice> list = ei.getDataList(CoupletsPrice.class);
			for (CoupletsPrice coupletsPrice : list){
				try{
					coupletsPriceService.save(coupletsPrice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条成品楹联价格记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条成品楹联价格记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入成品楹联价格失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/coupletsPrice/?repage";
    }
	
	/**
	 * 下载导入成品楹联价格数据模板
	 */
	@RequiresPermissions("cultural:order:coupletsPrice:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "成品楹联价格数据导入模板.xlsx";
    		List<CoupletsPrice> list = Lists.newArrayList(); 
    		new ExportExcel("成品楹联价格数据", CoupletsPrice.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/coupletsPrice/?repage";
    }

}