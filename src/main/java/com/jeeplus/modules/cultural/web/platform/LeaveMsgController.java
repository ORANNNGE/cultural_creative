/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.platform;

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
import com.jeeplus.modules.cultural.entity.platform.LeaveMsg;
import com.jeeplus.modules.cultural.service.platform.LeaveMsgService;

/**
 * 留言Controller
 * @author orange
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/platform/leaveMsg")
public class LeaveMsgController extends BaseController {

	@Autowired
	private LeaveMsgService leaveMsgService;
	
	@ModelAttribute
	public LeaveMsg get(@RequestParam(required=false) String id) {
		LeaveMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leaveMsgService.get(id);
		}
		if (entity == null){
			entity = new LeaveMsg();
		}
		return entity;
	}
	
	/**
	 * 留言列表页面
	 */
	@RequiresPermissions("cultural:platform:leaveMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/platform/leaveMsgList";
	}
	
		/**
	 * 留言列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:leaveMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LeaveMsg leaveMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeaveMsg> page = leaveMsgService.findPage(new Page<LeaveMsg>(request, response), leaveMsg); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑留言表单页面
	 */
	@RequiresPermissions(value={"cultural:platform:leaveMsg:view","cultural:platform:leaveMsg:add","cultural:platform:leaveMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeaveMsg leaveMsg, Model model) {
		model.addAttribute("leaveMsg", leaveMsg);
		return "modules/cultural/platform/leaveMsgForm";
	}

	/**
	 * 保存留言
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:platform:leaveMsg:add","cultural:platform:leaveMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LeaveMsg leaveMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, leaveMsg)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		leaveMsgService.save(leaveMsg);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存留言成功");
		return j;
	}
	
	/**
	 * 删除留言
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:leaveMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LeaveMsg leaveMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		leaveMsgService.delete(leaveMsg);
		j.setMsg("删除留言成功");
		return j;
	}
	
	/**
	 * 批量删除留言
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:leaveMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leaveMsgService.delete(leaveMsgService.get(id));
		}
		j.setMsg("删除留言成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:platform:leaveMsg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LeaveMsg leaveMsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "留言"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeaveMsg> page = leaveMsgService.findPage(new Page<LeaveMsg>(request, response, -1), leaveMsg);
    		new ExportExcel("留言", LeaveMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出留言记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:platform:leaveMsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeaveMsg> list = ei.getDataList(LeaveMsg.class);
			for (LeaveMsg leaveMsg : list){
				try{
					leaveMsgService.save(leaveMsg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条留言记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条留言记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入留言失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/platform/leaveMsg/?repage";
    }
	
	/**
	 * 下载导入留言数据模板
	 */
	@RequiresPermissions("cultural:platform:leaveMsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "留言数据导入模板.xlsx";
    		List<LeaveMsg> list = Lists.newArrayList(); 
    		new ExportExcel("留言数据", LeaveMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/platform/leaveMsg/?repage";
    }

}