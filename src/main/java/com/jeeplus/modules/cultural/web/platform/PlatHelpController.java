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
import com.jeeplus.modules.cultural.entity.platform.PlatHelp;
import com.jeeplus.modules.cultural.service.platform.PlatHelpService;

/**
 * 帮助Controller
 * @author orange
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/platform/platHelp")
public class PlatHelpController extends BaseController {

	@Autowired
	private PlatHelpService platHelpService;
	
	@ModelAttribute
	public PlatHelp get(@RequestParam(required=false) String id) {
		PlatHelp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = platHelpService.get(id);
		}
		if (entity == null){
			entity = new PlatHelp();
		}
		return entity;
	}
	
	/**
	 * 帮助列表页面
	 */
	@RequiresPermissions("cultural:platform:platHelp:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/platform/platHelpList";
	}
	
		/**
	 * 帮助列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:platHelp:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PlatHelp platHelp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PlatHelp> page = platHelpService.findPage(new Page<PlatHelp>(request, response), platHelp); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑帮助表单页面
	 */
	@RequiresPermissions(value={"cultural:platform:platHelp:view","cultural:platform:platHelp:add","cultural:platform:platHelp:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PlatHelp platHelp, Model model) {
		model.addAttribute("platHelp", platHelp);
		return "modules/cultural/platform/platHelpForm";
	}

	/**
	 * 保存帮助
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:platform:platHelp:add","cultural:platform:platHelp:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PlatHelp platHelp, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, platHelp)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		platHelp.setName("帮助");
		platHelpService.save(platHelp);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存帮助成功");
		return j;
	}
	
	/**
	 * 删除帮助
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:platHelp:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PlatHelp platHelp, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		platHelpService.delete(platHelp);
		j.setMsg("删除帮助成功");
		return j;
	}
	
	/**
	 * 批量删除帮助
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:platHelp:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			platHelpService.delete(platHelpService.get(id));
		}
		j.setMsg("删除帮助成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:platHelp:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PlatHelp platHelp, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "帮助"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PlatHelp> page = platHelpService.findPage(new Page<PlatHelp>(request, response, -1), platHelp);
    		new ExportExcel("帮助", PlatHelp.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出帮助记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:platform:platHelp:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PlatHelp> list = ei.getDataList(PlatHelp.class);
			for (PlatHelp platHelp : list){
				try{
					platHelpService.save(platHelp);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条帮助记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条帮助记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入帮助失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/platform/platHelp/?repage";
    }
	
	/**
	 * 下载导入帮助数据模板
	 */
	@RequiresPermissions("cultural:platform:platHelp:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "帮助数据导入模板.xlsx";
    		List<PlatHelp> list = Lists.newArrayList(); 
    		new ExportExcel("帮助数据", PlatHelp.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/platform/platHelp/?repage";
    }

}