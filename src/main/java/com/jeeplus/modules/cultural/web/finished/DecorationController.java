/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.finished;

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
import com.jeeplus.modules.cultural.entity.finished.Decoration;
import com.jeeplus.modules.cultural.service.finished.DecorationService;

/**
 * 装饰品Controller
 * @author orange
 * @version 2018-09-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/finished/decoration")
public class DecorationController extends BaseController {

	@Autowired
	private DecorationService decorationService;
	
	@ModelAttribute
	public Decoration get(@RequestParam(required=false) String id) {
		Decoration entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = decorationService.get(id);
		}
		if (entity == null){
			entity = new Decoration();
		}
		return entity;
	}
	
	/**
	 * 装饰品列表页面
	 */
	@RequiresPermissions("cultural:finished:decoration:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/finished/decorationList";
	}
	
		/**
	 * 装饰品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:decoration:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Decoration decoration, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Decoration> page = decorationService.findPage(new Page<Decoration>(request, response), decoration); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑装饰品表单页面
	 */
	@RequiresPermissions(value={"cultural:finished:decoration:view","cultural:finished:decoration:add","cultural:finished:decoration:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Decoration decoration, Model model) {
		model.addAttribute("decoration", decoration);
		return "modules/cultural/finished/decorationForm";
	}

	/**
	 * 保存装饰品
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:finished:decoration:add","cultural:finished:decoration:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Decoration decoration, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, decoration)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		decorationService.save(decoration);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存装饰品成功");
		return j;
	}
	
	/**
	 * 删除装饰品
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:decoration:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Decoration decoration, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		decorationService.delete(decoration);
		j.setMsg("删除装饰品成功");
		return j;
	}
	
	/**
	 * 批量删除装饰品
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:decoration:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			decorationService.delete(decorationService.get(id));
		}
		j.setMsg("删除装饰品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:decoration:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Decoration decoration, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "装饰品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Decoration> page = decorationService.findPage(new Page<Decoration>(request, response, -1), decoration);
    		new ExportExcel("装饰品", Decoration.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出装饰品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:finished:decoration:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Decoration> list = ei.getDataList(Decoration.class);
			for (Decoration decoration : list){
				try{
					decorationService.save(decoration);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条装饰品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条装饰品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入装饰品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/finished/decoration/?repage";
    }
	
	/**
	 * 下载导入装饰品数据模板
	 */
	@RequiresPermissions("cultural:finished:decoration:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "装饰品数据导入模板.xlsx";
    		List<Decoration> list = Lists.newArrayList(); 
    		new ExportExcel("装饰品数据", Decoration.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/finished/decoration/?repage";
    }

}