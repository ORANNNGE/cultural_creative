/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.order;

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
import com.jeeplus.modules.cultural.entity.order.FinishedOrder;
import com.jeeplus.modules.cultural.service.order.FinishedOrderService;

/**
 * 其他成品订单Controller
 * @author orange
 * @version 2018-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/order/finishedOrder")
public class FinishedOrderController extends BaseController {

	@Autowired
	private FinishedOrderService finishedOrderService;
	
	@ModelAttribute
	public FinishedOrder get(@RequestParam(required=false) String id) {
		FinishedOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = finishedOrderService.get(id);
		}
		if (entity == null){
			entity = new FinishedOrder();
		}
		return entity;
	}
	
	/**
	 * 其他成品订单列表页面
	 */
	@RequiresPermissions("cultural:order:finishedOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/order/finishedOrderList";
	}
	
		/**
	 * 其他成品订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:finishedOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FinishedOrder finishedOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FinishedOrder> page = finishedOrderService.findPage(new Page<FinishedOrder>(request, response), finishedOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑其他成品订单表单页面
	 */
	@RequiresPermissions(value={"cultural:order:finishedOrder:view","cultural:order:finishedOrder:add","cultural:order:finishedOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FinishedOrder finishedOrder, Model model) {
		model.addAttribute("finishedOrder", finishedOrder);
		return "modules/cultural/order/finishedOrderForm";
	}

	/**
	 * 保存其他成品订单
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:order:finishedOrder:add","cultural:order:finishedOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FinishedOrder finishedOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, finishedOrder)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		finishedOrderService.save(finishedOrder);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存其他成品订单成功");
		return j;
	}
	
	/**
	 * 删除其他成品订单
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:finishedOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FinishedOrder finishedOrder, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		finishedOrderService.delete(finishedOrder);
		j.setMsg("删除其他成品订单成功");
		return j;
	}
	
	/**
	 * 批量删除其他成品订单
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:finishedOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			finishedOrderService.delete(finishedOrderService.get(id));
		}
		j.setMsg("删除其他成品订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:finishedOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FinishedOrder finishedOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "其他成品订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FinishedOrder> page = finishedOrderService.findPage(new Page<FinishedOrder>(request, response, -1), finishedOrder);
    		new ExportExcel("其他成品订单", FinishedOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出其他成品订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:order:finishedOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FinishedOrder> list = ei.getDataList(FinishedOrder.class);
			for (FinishedOrder finishedOrder : list){
				try{
					finishedOrderService.save(finishedOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条其他成品订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条其他成品订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入其他成品订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/finishedOrder/?repage";
    }
	
	/**
	 * 下载导入其他成品订单数据模板
	 */
	@RequiresPermissions("cultural:order:finishedOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "其他成品订单数据导入模板.xlsx";
    		List<FinishedOrder> list = Lists.newArrayList(); 
    		new ExportExcel("其他成品订单数据", FinishedOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/finishedOrder/?repage";
    }

}