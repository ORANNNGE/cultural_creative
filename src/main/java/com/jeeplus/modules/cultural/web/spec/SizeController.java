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
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.service.spec.SizeService;

/**
 * 尺寸Controller
 * @author orange
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/spec/size")
public class SizeController extends BaseController {

	@Autowired
	private SizeService sizeService;
	
	@ModelAttribute
	public Size get(@RequestParam(required=false) String id) {
		Size entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sizeService.get(id);
		}
		if (entity == null){
			entity = new Size();
		}
		return entity;
	}
	
	/**
	 * 尺寸列表页面
	 */
	@RequiresPermissions("cultural:spec:size:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/spec/sizeList";
	}
	
		/**
	 * 尺寸列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:size:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Size size, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Size> page = sizeService.findPage(new Page<Size>(request, response), size); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑尺寸表单页面
	 */
	@RequiresPermissions(value={"cultural:spec:size:view","cultural:spec:size:add","cultural:spec:size:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Size size, Model model) {
		model.addAttribute("size", size);
		return "modules/cultural/spec/sizeForm";
	}

	/**
	 * 保存尺寸
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:spec:size:add","cultural:spec:size:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Size size, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, size)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		sizeService.save(size);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存尺寸成功");
		return j;
	}
	
	/**
	 * 删除尺寸
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:size:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Size size, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		sizeService.delete(size);
		j.setMsg("删除尺寸成功");
		return j;
	}
	
	/**
	 * 批量删除尺寸
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:size:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sizeService.delete(sizeService.get(id));
		}
		j.setMsg("删除尺寸成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:size:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Size size, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "尺寸"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Size> page = sizeService.findPage(new Page<Size>(request, response, -1), size);
    		new ExportExcel("尺寸", Size.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出尺寸记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:spec:size:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Size> list = ei.getDataList(Size.class);
			for (Size size : list){
				try{
					sizeService.save(size);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条尺寸记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条尺寸记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入尺寸失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/spec/size/?repage";
    }
	
	/**
	 * 下载导入尺寸数据模板
	 */
	@RequiresPermissions("cultural:spec:size:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "尺寸数据导入模板.xlsx";
    		List<Size> list = Lists.newArrayList(); 
    		new ExportExcel("尺寸数据", Size.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/spec/size/?repage";
    }

}