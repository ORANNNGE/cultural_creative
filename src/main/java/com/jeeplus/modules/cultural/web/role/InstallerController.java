/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.role;

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
import com.jeeplus.modules.cultural.entity.role.Installer;
import com.jeeplus.modules.cultural.service.role.InstallerService;

/**
 * 安装人员Controller
 * @author orange
 * @version 2018-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/role/installer")
public class InstallerController extends BaseController {

	@Autowired
	private InstallerService installerService;
	
	@ModelAttribute
	public Installer get(@RequestParam(required=false) String id) {
		Installer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = installerService.get(id);
		}
		if (entity == null){
			entity = new Installer();
		}
		return entity;
	}
	
	/**
	 * 安装人员列表页面
	 */
	@RequiresPermissions("cultural:role:installer:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/role/installerList";
	}
	
		/**
	 * 安装人员列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:role:installer:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Installer installer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Installer> page = installerService.findPage(new Page<Installer>(request, response), installer); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑安装人员表单页面
	 */
	@RequiresPermissions(value={"cultural:role:installer:view","cultural:role:installer:add","cultural:role:installer:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Installer installer, Model model) {
		model.addAttribute("installer", installer);
		return "modules/cultural/role/installerForm";
	}

	/**
	 * 保存安装人员
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:role:installer:add","cultural:role:installer:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Installer installer, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, installer)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		installerService.save(installer);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存安装人员成功");
		return j;
	}
	
	/**
	 * 删除安装人员
	 */
	@ResponseBody
	@RequiresPermissions("cultural:role:installer:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Installer installer, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		installerService.delete(installer);
		j.setMsg("删除安装人员成功");
		return j;
	}
	
	/**
	 * 批量删除安装人员
	 */
	@ResponseBody
	@RequiresPermissions("cultural:role:installer:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			installerService.delete(installerService.get(id));
		}
		j.setMsg("删除安装人员成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:role:installer:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Installer installer, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "安装人员"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Installer> page = installerService.findPage(new Page<Installer>(request, response, -1), installer);
    		new ExportExcel("安装人员", Installer.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出安装人员记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:role:installer:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Installer> list = ei.getDataList(Installer.class);
			for (Installer installer : list){
				try{
					installerService.save(installer);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条安装人员记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条安装人员记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入安装人员失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/role/installer/?repage";
    }
	
	/**
	 * 下载导入安装人员数据模板
	 */
	@RequiresPermissions("cultural:role:installer:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "安装人员数据导入模板.xlsx";
    		List<Installer> list = Lists.newArrayList(); 
    		new ExportExcel("安装人员数据", Installer.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/role/installer/?repage";
    }

}