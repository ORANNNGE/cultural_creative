/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.couplets;

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
import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.service.couplets.CoupletsService;

/**
 * 成品楹联Controller
 * @author orange
 * @version 2018-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/couplets/couplets")
public class CoupletsController extends BaseController {

	@Autowired
	private CoupletsService coupletsService;
	
	@ModelAttribute
	public Couplets get(@RequestParam(required=false) String id) {
		Couplets entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = coupletsService.get(id);
		}
		if (entity == null){
			entity = new Couplets();
		}
		return entity;
	}
	
	/**
	 * 成品楹联列表页面
	 */
	@RequiresPermissions("cultural:couplets:couplets:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/couplets/coupletsList";
	}
	
		/**
	 * 成品楹联列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:couplets:couplets:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Couplets couplets, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Couplets> page = coupletsService.findPage(new Page<Couplets>(request, response), couplets); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑成品楹联表单页面
	 */
	@RequiresPermissions(value={"cultural:couplets:couplets:view","cultural:couplets:couplets:add","cultural:couplets:couplets:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Couplets couplets, Model model) {
		model.addAttribute("couplets", couplets);
		return "modules/cultural/couplets/coupletsForm";
	}

	/**
	 * 保存成品楹联
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:couplets:couplets:add","cultural:couplets:couplets:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Couplets couplets, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
//		if (!beanValidator(model, couplets)){
//			j.setSuccess(false);
//			j.setMsg("非法参数！");
//			return j;
//		}
		coupletsService.save(couplets);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存成品楹联成功");
		return j;
	}
	
	/**
	 * 删除成品楹联
	 */
	@ResponseBody
	@RequiresPermissions("cultural:couplets:couplets:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Couplets couplets, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		coupletsService.delete(couplets);
		j.setMsg("删除成品楹联成功");
		return j;
	}
	
	/**
	 * 批量删除成品楹联
	 */
	@ResponseBody
	@RequiresPermissions("cultural:couplets:couplets:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			coupletsService.delete(coupletsService.get(id));
		}
		j.setMsg("删除成品楹联成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:couplets:couplets:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Couplets couplets, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "成品楹联"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Couplets> page = coupletsService.findPage(new Page<Couplets>(request, response, -1), couplets);
    		new ExportExcel("成品楹联", Couplets.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出成品楹联记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:couplets:couplets:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Couplets> list = ei.getDataList(Couplets.class);
			for (Couplets couplets : list){
				try{
					coupletsService.save(couplets);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条成品楹联记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条成品楹联记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入成品楹联失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/couplets/couplets/?repage";
    }
	
	/**
	 * 下载导入成品楹联数据模板
	 */
	@RequiresPermissions("cultural:couplets:couplets:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "成品楹联数据导入模板.xlsx";
    		List<Couplets> list = Lists.newArrayList(); 
    		new ExportExcel("成品楹联数据", Couplets.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/couplets/couplets/?repage";
    }

}