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
import com.jeeplus.modules.cultural.entity.finished.NewYearPic;
import com.jeeplus.modules.cultural.service.finished.NewYearPicService;

/**
 * 年画Controller
 * @author orange
 * @version 2018-09-12
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/finished/newYearPic")
public class NewYearPicController extends BaseController {

	@Autowired
	private NewYearPicService newYearPicService;
	
	@ModelAttribute
	public NewYearPic get(@RequestParam(required=false) String id) {
		NewYearPic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = newYearPicService.get(id);
		}
		if (entity == null){
			entity = new NewYearPic();
		}
		return entity;
	}
	
	/**
	 * 年画列表页面
	 */
	@RequiresPermissions("cultural:finished:newYearPic:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/finished/newYearPicList";
	}
	
		/**
	 * 年画列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:newYearPic:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(NewYearPic newYearPic, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NewYearPic> page = newYearPicService.findPage(new Page<NewYearPic>(request, response), newYearPic); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑年画表单页面
	 */
	@RequiresPermissions(value={"cultural:finished:newYearPic:view","cultural:finished:newYearPic:add","cultural:finished:newYearPic:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(NewYearPic newYearPic, Model model) {
		model.addAttribute("newYearPic", newYearPic);
		return "modules/cultural/finished/newYearPicForm";
	}

	/**
	 * 保存年画
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:finished:newYearPic:add","cultural:finished:newYearPic:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(NewYearPic newYearPic, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, newYearPic)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		newYearPicService.save(newYearPic);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存年画成功");
		return j;
	}
	
	/**
	 * 删除年画
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:newYearPic:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(NewYearPic newYearPic, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		newYearPicService.delete(newYearPic);
		j.setMsg("删除年画成功");
		return j;
	}
	
	/**
	 * 批量删除年画
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:newYearPic:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			newYearPicService.delete(newYearPicService.get(id));
		}
		j.setMsg("删除年画成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:newYearPic:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(NewYearPic newYearPic, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "年画"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<NewYearPic> page = newYearPicService.findPage(new Page<NewYearPic>(request, response, -1), newYearPic);
    		new ExportExcel("年画", NewYearPic.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出年画记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:finished:newYearPic:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<NewYearPic> list = ei.getDataList(NewYearPic.class);
			for (NewYearPic newYearPic : list){
				try{
					newYearPicService.save(newYearPic);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条年画记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条年画记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入年画失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/finished/newYearPic/?repage";
    }
	
	/**
	 * 下载导入年画数据模板
	 */
	@RequiresPermissions("cultural:finished:newYearPic:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "年画数据导入模板.xlsx";
    		List<NewYearPic> list = Lists.newArrayList(); 
    		new ExportExcel("年画数据", NewYearPic.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/finished/newYearPic/?repage";
    }

}