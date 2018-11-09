/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.platform;

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
import com.jeeplus.modules.cultural.entity.platform.PlatIntro;
import com.jeeplus.modules.cultural.service.platform.PlatIntroService;

/**
 * 平台简介Controller
 * @author orange
 * @version 2018-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/platform/platIntro")
public class PlatIntroController extends BaseController {

	@Autowired
	private PlatIntroService platIntroService;
	
	@ModelAttribute
	public PlatIntro get(@RequestParam(required=false) String id) {
		PlatIntro entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = platIntroService.get(id);
		}
		if (entity == null){
			entity = new PlatIntro();
		}
		return entity;
	}
	
	/**
	 * 平台简介列表页面
	 */
	@RequiresPermissions("cultural:platform:platIntro:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/platform/platIntroList";
	}
	
		/**
	 * 平台简介列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:platIntro:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PlatIntro platIntro, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PlatIntro> page = platIntroService.findPage(new Page<PlatIntro>(request, response), platIntro); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑平台简介表单页面
	 */
	@RequiresPermissions(value={"cultural:platform:platIntro:view","cultural:platform:platIntro:add","cultural:platform:platIntro:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PlatIntro platIntro, Model model) {
		model.addAttribute("platIntro", platIntro);
		return "modules/cultural/platform/platIntroForm";
	}

	/**
	 * 保存平台简介
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:platform:platIntro:add","cultural:platform:platIntro:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PlatIntro platIntro, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, platIntro)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		platIntro.setName("平台简介");
		platIntroService.save(platIntro);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存平台简介成功");
		return j;
	}
	
	/**
	 * 删除平台简介
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:platIntro:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PlatIntro platIntro, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		platIntroService.delete(platIntro);
		j.setMsg("删除平台简介成功");
		return j;
	}
	
	/**
	 * 批量删除平台简介
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:platIntro:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			platIntroService.delete(platIntroService.get(id));
		}
		j.setMsg("删除平台简介成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:platIntro:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PlatIntro platIntro, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "平台简介"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PlatIntro> page = platIntroService.findPage(new Page<PlatIntro>(request, response, -1), platIntro);
    		new ExportExcel("平台简介", PlatIntro.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出平台简介记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:platform:platIntro:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PlatIntro> list = ei.getDataList(PlatIntro.class);
			for (PlatIntro platIntro : list){
				try{
					platIntroService.save(platIntro);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条平台简介记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条平台简介记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入平台简介失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/platform/platIntro/?repage";
    }
	
	/**
	 * 下载导入平台简介数据模板
	 */
	@RequiresPermissions("cultural:platform:platIntro:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "平台简介数据导入模板.xlsx";
    		List<PlatIntro> list = Lists.newArrayList(); 
    		new ExportExcel("平台简介数据", PlatIntro.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/platform/platIntro/?repage";
    }

}