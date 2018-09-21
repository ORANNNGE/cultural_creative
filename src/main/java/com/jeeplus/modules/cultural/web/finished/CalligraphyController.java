/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.finished;

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
import com.jeeplus.modules.cultural.entity.finished.Calligraphy;
import com.jeeplus.modules.cultural.service.finished.CalligraphyService;

/**
 * 书法作品Controller
 * @author orange
 * @version 2018-09-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/finished/calligraphy")
public class CalligraphyController extends BaseController {

	@Autowired
	private CalligraphyService calligraphyService;
	
	@ModelAttribute
	public Calligraphy get(@RequestParam(required=false) String id) {
		Calligraphy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = calligraphyService.get(id);
		}
		if (entity == null){
			entity = new Calligraphy();
		}
		return entity;
	}
	
	/**
	 * 书法作品列表页面
	 */
	@RequiresPermissions("cultural:finished:calligraphy:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/finished/calligraphyList";
	}
	
		/**
	 * 书法作品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:calligraphy:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Calligraphy calligraphy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Calligraphy> page = calligraphyService.findPage(new Page<Calligraphy>(request, response), calligraphy); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑书法作品表单页面
	 */
	@RequiresPermissions(value={"cultural:finished:calligraphy:view","cultural:finished:calligraphy:add","cultural:finished:calligraphy:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Calligraphy calligraphy, Model model) {
		model.addAttribute("calligraphy", calligraphy);
		return "modules/cultural/finished/calligraphyForm";
	}

	/**
	 * 保存书法作品
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:finished:calligraphy:add","cultural:finished:calligraphy:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Calligraphy calligraphy, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, calligraphy)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		calligraphyService.save(calligraphy);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存书法作品成功");
		return j;
	}
	
	/**
	 * 删除书法作品
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:calligraphy:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Calligraphy calligraphy, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		calligraphyService.delete(calligraphy);
		j.setMsg("删除书法作品成功");
		return j;
	}
	
	/**
	 * 批量删除书法作品
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:calligraphy:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			calligraphyService.delete(calligraphyService.get(id));
		}
		j.setMsg("删除书法作品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:calligraphy:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Calligraphy calligraphy, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "书法作品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Calligraphy> page = calligraphyService.findPage(new Page<Calligraphy>(request, response, -1), calligraphy);
    		new ExportExcel("书法作品", Calligraphy.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出书法作品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:finished:calligraphy:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Calligraphy> list = ei.getDataList(Calligraphy.class);
			for (Calligraphy calligraphy : list){
				try{
					calligraphyService.save(calligraphy);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条书法作品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条书法作品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入书法作品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/finished/calligraphy/?repage";
    }
	
	/**
	 * 下载导入书法作品数据模板
	 */
	@RequiresPermissions("cultural:finished:calligraphy:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "书法作品数据导入模板.xlsx";
    		List<Calligraphy> list = Lists.newArrayList(); 
    		new ExportExcel("书法作品数据", Calligraphy.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/finished/calligraphy/?repage";
    }

}