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
import com.jeeplus.modules.cultural.entity.order.LexiconPrice;
import com.jeeplus.modules.cultural.service.order.LexiconPriceService;

/**
 * 楹联词库价格Controller
 * @author orange
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/order/lexiconPrice")
public class LexiconPriceController extends BaseController {

	@Autowired
	private LexiconPriceService lexiconPriceService;
	
	@ModelAttribute
	public LexiconPrice get(@RequestParam(required=false) String id) {
		LexiconPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lexiconPriceService.get(id);
		}
		if (entity == null){
			entity = new LexiconPrice();
		}
		return entity;
	}
	
	/**
	 * 词库价格列表页面
	 */
	@RequiresPermissions("cultural:order:lexiconPrice:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/order/lexiconPriceList";
	}
	
		/**
	 * 词库价格列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:lexiconPrice:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LexiconPrice lexiconPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LexiconPrice> page = lexiconPriceService.findPage(new Page<LexiconPrice>(request, response), lexiconPrice); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑词库价格表单页面
	 */
	@RequiresPermissions(value={"cultural:order:lexiconPrice:view","cultural:order:lexiconPrice:add","cultural:order:lexiconPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LexiconPrice lexiconPrice, Model model) {
		model.addAttribute("lexiconPrice", lexiconPrice);
		return "modules/cultural/order/lexiconPriceForm";
	}

	/**
	 * 保存词库价格
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:order:lexiconPrice:add","cultural:order:lexiconPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LexiconPrice lexiconPrice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, lexiconPrice)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		lexiconPriceService.save(lexiconPrice);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存词库价格成功");
		return j;
	}
	
	/**
	 * 删除词库价格
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:lexiconPrice:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LexiconPrice lexiconPrice, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		lexiconPriceService.delete(lexiconPrice);
		j.setMsg("删除词库价格成功");
		return j;
	}
	
	/**
	 * 批量删除词库价格
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:lexiconPrice:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			lexiconPriceService.delete(lexiconPriceService.get(id));
		}
		j.setMsg("删除词库价格成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:lexiconPrice:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LexiconPrice lexiconPrice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "词库价格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LexiconPrice> page = lexiconPriceService.findPage(new Page<LexiconPrice>(request, response, -1), lexiconPrice);
    		new ExportExcel("词库价格", LexiconPrice.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出词库价格记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:order:lexiconPrice:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LexiconPrice> list = ei.getDataList(LexiconPrice.class);
			for (LexiconPrice lexiconPrice : list){
				try{
					lexiconPriceService.save(lexiconPrice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条词库价格记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条词库价格记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入词库价格失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/lexiconPrice/?repage";
    }
	
	/**
	 * 下载导入词库价格数据模板
	 */
	@RequiresPermissions("cultural:order:lexiconPrice:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "词库价格数据导入模板.xlsx";
    		List<LexiconPrice> list = Lists.newArrayList(); 
    		new ExportExcel("词库价格数据", LexiconPrice.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/lexiconPrice/?repage";
    }

}