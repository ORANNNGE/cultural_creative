/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.order;

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
import com.jeeplus.modules.cultural.entity.order.Combo;
import com.jeeplus.modules.cultural.service.order.ComboService;

/**
 * 套餐Controller
 * @author orange
 * @version 2018-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/order/combo")
public class ComboController extends BaseController {

	@Autowired
	private ComboService comboService;
	
	@ModelAttribute
	public Combo get(@RequestParam(required=false) String id) {
		Combo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comboService.get(id);
		}
		if (entity == null){
			entity = new Combo();
		}
		return entity;
	}
	
	/**
	 * 套餐列表页面
	 */
	@RequiresPermissions("cultural:order:combo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/order/comboList";
	}
	
		/**
	 * 套餐列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:combo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Combo combo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Combo> page = comboService.findPage(new Page<Combo>(request, response), combo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑套餐表单页面
	 */
	@RequiresPermissions(value={"cultural:order:combo:view","cultural:order:combo:add","cultural:order:combo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Combo combo, Model model) {
		model.addAttribute("combo", combo);
		return "modules/cultural/order/comboForm";
	}

	/**
	 * 保存套餐
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:order:combo:add","cultural:order:combo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Combo combo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, combo)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		comboService.save(combo);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存套餐成功");
		return j;
	}
	
	/**
	 * 删除套餐
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:combo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Combo combo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comboService.delete(combo);
		j.setMsg("删除套餐成功");
		return j;
	}
	
	/**
	 * 批量删除套餐
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:combo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comboService.delete(comboService.get(id));
		}
		j.setMsg("删除套餐成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:combo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Combo combo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "套餐"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Combo> page = comboService.findPage(new Page<Combo>(request, response, -1), combo);
    		new ExportExcel("套餐", Combo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出套餐记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:order:combo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Combo> list = ei.getDataList(Combo.class);
			for (Combo combo : list){
				try{
					comboService.save(combo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条套餐记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条套餐记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入套餐失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/combo/?repage";
    }
	
	/**
	 * 下载导入套餐数据模板
	 */
	@RequiresPermissions("cultural:order:combo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "套餐数据导入模板.xlsx";
    		List<Combo> list = Lists.newArrayList(); 
    		new ExportExcel("套餐数据", Combo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/combo/?repage";
    }

}