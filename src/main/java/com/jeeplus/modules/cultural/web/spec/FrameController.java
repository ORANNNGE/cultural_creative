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
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.service.spec.FrameService;

/**
 * 楹联框Controller
 * @author orange
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/spec/frame")
public class FrameController extends BaseController {

	@Autowired
	private FrameService frameService;
	
	@ModelAttribute
	public Frame get(@RequestParam(required=false) String id) {
		Frame entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = frameService.get(id);
		}
		if (entity == null){
			entity = new Frame();
		}
		return entity;
	}
	
	/**
	 * 楹联框列表页面
	 */
	@RequiresPermissions("cultural:spec:frame:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/spec/frameList";
	}
	
		/**
	 * 楹联框列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:frame:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Frame frame, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Frame> page = frameService.findPage(new Page<Frame>(request, response), frame); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑楹联框表单页面
	 */
	@RequiresPermissions(value={"cultural:spec:frame:view","cultural:spec:frame:add","cultural:spec:frame:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Frame frame, Model model) {
		model.addAttribute("frame", frame);
		return "modules/cultural/spec/frameForm";
	}

	/**
	 * 保存楹联框
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:spec:frame:add","cultural:spec:frame:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Frame frame, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, frame)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		frameService.save(frame);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存楹联框成功");
		return j;
	}
	
	/**
	 * 删除楹联框
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:frame:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Frame frame, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		frameService.delete(frame);
		j.setMsg("删除楹联框成功");
		return j;
	}
	
	/**
	 * 批量删除楹联框
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:frame:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			frameService.delete(frameService.get(id));
		}
		j.setMsg("删除楹联框成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:spec:frame:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Frame frame, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "楹联框"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Frame> page = frameService.findPage(new Page<Frame>(request, response, -1), frame);
    		new ExportExcel("楹联框", Frame.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出楹联框记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:spec:frame:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Frame> list = ei.getDataList(Frame.class);
			for (Frame frame : list){
				try{
					frameService.save(frame);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条楹联框记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条楹联框记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入楹联框失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/spec/frame/?repage";
    }
	
	/**
	 * 下载导入楹联框数据模板
	 */
	@RequiresPermissions("cultural:spec:frame:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "楹联框数据导入模板.xlsx";
    		List<Frame> list = Lists.newArrayList(); 
    		new ExportExcel("楹联框数据", Frame.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/spec/frame/?repage";
    }

}