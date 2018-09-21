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
import com.jeeplus.modules.cultural.entity.spec.Typeface;
import com.jeeplus.modules.cultural.service.spec.TypefaceService;

/**
 * 字体Controller
 * @author orange
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/spec/typeface")
public class TypefaceController extends BaseController {

	@Autowired
	private TypefaceService typefaceService;
	
	@ModelAttribute
	public Typeface get(@RequestParam(required=false) String id) {
		Typeface entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = typefaceService.get(id);
		}
		if (entity == null){
			entity = new Typeface();
		}
		return entity;
	}
	
	/**
	 * 字体列表页面
	 */
	@RequiresPermissions("cultural:spec:typeface:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/spec/typefaceList";
	}
	
		/**
	 * 字体列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:typeface:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Typeface typeface, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Typeface> page = typefaceService.findPage(new Page<Typeface>(request, response), typeface); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑字体表单页面
	 */
	@RequiresPermissions(value={"cultural:spec:typeface:view","cultural:spec:typeface:add","cultural:spec:typeface:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Typeface typeface, Model model) {
		model.addAttribute("typeface", typeface);
		return "modules/cultural/spec/typefaceForm";
	}

	/**
	 * 保存字体
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:spec:typeface:add","cultural:spec:typeface:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Typeface typeface, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, typeface)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		typefaceService.save(typeface);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存字体成功");
		return j;
	}
	
	/**
	 * 删除字体
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:typeface:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Typeface typeface, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		typefaceService.delete(typeface);
		j.setMsg("删除字体成功");
		return j;
	}
	
	/**
	 * 批量删除字体
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:typeface:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			typefaceService.delete(typefaceService.get(id));
		}
		j.setMsg("删除字体成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:typeface:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Typeface typeface, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "字体"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Typeface> page = typefaceService.findPage(new Page<Typeface>(request, response, -1), typeface);
    		new ExportExcel("字体", Typeface.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出字体记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:spec:typeface:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Typeface> list = ei.getDataList(Typeface.class);
			for (Typeface typeface : list){
				try{
					typefaceService.save(typeface);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条字体记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条字体记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入字体失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/spec/typeface/?repage";
    }
	
	/**
	 * 下载导入字体数据模板
	 */
	@RequiresPermissions("cultural:spec:typeface:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "字体数据导入模板.xlsx";
    		List<Typeface> list = Lists.newArrayList(); 
    		new ExportExcel("字体数据", Typeface.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/spec/typeface/?repage";
    }

}