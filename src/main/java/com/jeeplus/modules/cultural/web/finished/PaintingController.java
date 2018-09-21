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
import com.jeeplus.modules.cultural.entity.finished.Painting;
import com.jeeplus.modules.cultural.service.finished.PaintingService;

/**
 * 美术作品Controller
 * @author orange
 * @version 2018-09-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/finished/painting")
public class PaintingController extends BaseController {

	@Autowired
	private PaintingService paintingService;
	
	@ModelAttribute
	public Painting get(@RequestParam(required=false) String id) {
		Painting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paintingService.get(id);
		}
		if (entity == null){
			entity = new Painting();
		}
		return entity;
	}
	
	/**
	 * 美术作品列表页面
	 */
	@RequiresPermissions("cultural:finished:painting:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/finished/paintingList";
	}
	
		/**
	 * 美术作品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:painting:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Painting painting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Painting> page = paintingService.findPage(new Page<Painting>(request, response), painting); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑美术作品表单页面
	 */
	@RequiresPermissions(value={"cultural:finished:painting:view","cultural:finished:painting:add","cultural:finished:painting:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Painting painting, Model model) {
		model.addAttribute("painting", painting);
		return "modules/cultural/finished/paintingForm";
	}

	/**
	 * 保存美术作品
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:finished:painting:add","cultural:finished:painting:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Painting painting, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, painting)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		paintingService.save(painting);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存美术作品成功");
		return j;
	}
	
	/**
	 * 删除美术作品
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:painting:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Painting painting, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		paintingService.delete(painting);
		j.setMsg("删除美术作品成功");
		return j;
	}
	
	/**
	 * 批量删除美术作品
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:painting:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			paintingService.delete(paintingService.get(id));
		}
		j.setMsg("删除美术作品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:finished:painting:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Painting painting, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "美术作品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Painting> page = paintingService.findPage(new Page<Painting>(request, response, -1), painting);
    		new ExportExcel("美术作品", Painting.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出美术作品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:finished:painting:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Painting> list = ei.getDataList(Painting.class);
			for (Painting painting : list){
				try{
					paintingService.save(painting);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条美术作品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条美术作品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入美术作品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/finished/painting/?repage";
    }
	
	/**
	 * 下载导入美术作品数据模板
	 */
	@RequiresPermissions("cultural:finished:painting:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "美术作品数据导入模板.xlsx";
    		List<Painting> list = Lists.newArrayList(); 
    		new ExportExcel("美术作品数据", Painting.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/finished/painting/?repage";
    }

}