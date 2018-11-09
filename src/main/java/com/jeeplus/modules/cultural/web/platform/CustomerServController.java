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
import com.jeeplus.modules.cultural.entity.platform.CustomerServ;
import com.jeeplus.modules.cultural.service.platform.CustomerServService;

/**
 * 客服Controller
 * @author orange
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/platform/customerServ")
public class CustomerServController extends BaseController {

	@Autowired
	private CustomerServService customerServService;
	
	@ModelAttribute
	public CustomerServ get(@RequestParam(required=false) String id) {
		CustomerServ entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerServService.get(id);
		}
		if (entity == null){
			entity = new CustomerServ();
		}
		return entity;
	}
	
	/**
	 * 客服列表页面
	 */
	@RequiresPermissions("cultural:platform:customerServ:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/platform/customerServList";
	}
	
		/**
	 * 客服列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:customerServ:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CustomerServ customerServ, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerServ> page = customerServService.findPage(new Page<CustomerServ>(request, response), customerServ); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客服表单页面
	 */
	@RequiresPermissions(value={"cultural:platform:customerServ:view","cultural:platform:customerServ:add","cultural:platform:customerServ:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CustomerServ customerServ, Model model) {
		model.addAttribute("customerServ", customerServ);
		return "modules/cultural/platform/customerServForm";
	}

	/**
	 * 保存客服
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:platform:customerServ:add","cultural:platform:customerServ:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CustomerServ customerServ, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, customerServ)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		customerServService.save(customerServ);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存客服成功");
		return j;
	}
	
	/**
	 * 删除客服
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:customerServ:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CustomerServ customerServ, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		customerServService.delete(customerServ);
		j.setMsg("删除客服成功");
		return j;
	}
	
	/**
	 * 批量删除客服
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:customerServ:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerServService.delete(customerServService.get(id));
		}
		j.setMsg("删除客服成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:customerServ:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CustomerServ customerServ, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客服"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CustomerServ> page = customerServService.findPage(new Page<CustomerServ>(request, response, -1), customerServ);
    		new ExportExcel("客服", CustomerServ.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客服记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:platform:customerServ:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerServ> list = ei.getDataList(CustomerServ.class);
			for (CustomerServ customerServ : list){
				try{
					customerServService.save(customerServ);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客服记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条客服记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入客服失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/platform/customerServ/?repage";
    }
	
	/**
	 * 下载导入客服数据模板
	 */
	@RequiresPermissions("cultural:platform:customerServ:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客服数据导入模板.xlsx";
    		List<CustomerServ> list = Lists.newArrayList(); 
    		new ExportExcel("客服数据", CustomerServ.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/platform/customerServ/?repage";
    }

}