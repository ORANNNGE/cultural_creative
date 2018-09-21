/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.spec;

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
import com.jeeplus.modules.cultural.entity.spec.Craft;
import com.jeeplus.modules.cultural.service.spec.CraftService;

/**
 * 制作工艺Controller
 * @author orange
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/spec/craft")
public class CraftController extends BaseController {

	@Autowired
	private CraftService craftService;
	
	@ModelAttribute
	public Craft get(@RequestParam(required=false) String id) {
		Craft entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = craftService.get(id);
		}
		if (entity == null){
			entity = new Craft();
		}
		return entity;
	}
	
	/**
	 * 制作工艺列表页面
	 */
	@RequiresPermissions("cultural:spec:craft:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/spec/craftList";
	}
	
		/**
	 * 制作工艺列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:craft:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Craft craft, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Craft> page = craftService.findPage(new Page<Craft>(request, response), craft); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑制作工艺表单页面
	 */
	@RequiresPermissions(value={"cultural:spec:craft:view","cultural:spec:craft:add","cultural:spec:craft:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Craft craft, Model model) {
		model.addAttribute("craft", craft);
		return "modules/cultural/spec/craftForm";
	}

	/**
	 * 保存制作工艺
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:spec:craft:add","cultural:spec:craft:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Craft craft, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, craft)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		craftService.save(craft);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存制作工艺成功");
		return j;
	}
	
	/**
	 * 删除制作工艺
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:craft:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Craft craft, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		craftService.delete(craft);
		j.setMsg("删除制作工艺成功");
		return j;
	}
	
	/**
	 * 批量删除制作工艺
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:craft:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			craftService.delete(craftService.get(id));
		}
		j.setMsg("删除制作工艺成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:craft:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Craft craft, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "制作工艺"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Craft> page = craftService.findPage(new Page<Craft>(request, response, -1), craft);
    		new ExportExcel("制作工艺", Craft.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出制作工艺记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:spec:craft:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Craft> list = ei.getDataList(Craft.class);
			for (Craft craft : list){
				try{
					craftService.save(craft);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条制作工艺记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条制作工艺记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入制作工艺失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/spec/craft/?repage";
    }
	
	/**
	 * 下载导入制作工艺数据模板
	 */
	@RequiresPermissions("cultural:spec:craft:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "制作工艺数据导入模板.xlsx";
    		List<Craft> list = Lists.newArrayList(); 
    		new ExportExcel("制作工艺数据", Craft.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/spec/craft/?repage";
    }

}