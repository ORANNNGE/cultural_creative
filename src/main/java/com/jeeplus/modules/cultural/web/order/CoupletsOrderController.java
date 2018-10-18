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
import com.jeeplus.modules.cultural.entity.order.CoupletsOrder;
import com.jeeplus.modules.cultural.service.order.CoupletsOrderService;

/**
 * 成品楹联订单Controller
 * @author orange
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/order/coupletsOrder")
public class CoupletsOrderController extends BaseController {

	@Autowired
	private CoupletsOrderService coupletsOrderService;
	
	@ModelAttribute
	public CoupletsOrder get(@RequestParam(required=false) String id) {
		CoupletsOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = coupletsOrderService.get(id);
		}
		if (entity == null){
			entity = new CoupletsOrder();
		}
		return entity;
	}
	
	/**
	 * 成品楹联订单列表页面
	 */
	@RequiresPermissions("cultural:order:coupletsOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/order/coupletsOrderList";
	}
	
		/**
	 * 成品楹联订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:coupletsOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CoupletsOrder coupletsOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CoupletsOrder> page = coupletsOrderService.findPage(new Page<CoupletsOrder>(request, response), coupletsOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑成品楹联订单表单页面
	 */
	@RequiresPermissions(value={"cultural:order:coupletsOrder:view","cultural:order:coupletsOrder:add","cultural:order:coupletsOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CoupletsOrder coupletsOrder, Model model) {
		model.addAttribute("coupletsOrder", coupletsOrder);
		return "modules/cultural/order/coupletsOrderForm";
	}

	/**
	 * 保存成品楹联订单
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:order:coupletsOrder:add","cultural:order:coupletsOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CoupletsOrder coupletsOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, coupletsOrder)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		coupletsOrderService.save(coupletsOrder);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存成品楹联订单成功");
		return j;
	}
	
	/**
	 * 删除成品楹联订单
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:coupletsOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CoupletsOrder coupletsOrder, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		coupletsOrderService.delete(coupletsOrder);
		j.setMsg("删除成品楹联订单成功");
		return j;
	}
	
	/**
	 * 批量删除成品楹联订单
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:coupletsOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			coupletsOrderService.delete(coupletsOrderService.get(id));
		}
		j.setMsg("删除成品楹联订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:coupletsOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CoupletsOrder coupletsOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "成品楹联订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CoupletsOrder> page = coupletsOrderService.findPage(new Page<CoupletsOrder>(request, response, -1), coupletsOrder);
    		new ExportExcel("成品楹联订单", CoupletsOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出成品楹联订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:order:coupletsOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CoupletsOrder> list = ei.getDataList(CoupletsOrder.class);
			for (CoupletsOrder coupletsOrder : list){
				try{
					coupletsOrderService.save(coupletsOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条成品楹联订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条成品楹联订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入成品楹联订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/coupletsOrder/?repage";
    }
	
	/**
	 * 下载导入成品楹联订单数据模板
	 */
	@RequiresPermissions("cultural:order:coupletsOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "成品楹联订单数据导入模板.xlsx";
    		List<CoupletsOrder> list = Lists.newArrayList(); 
    		new ExportExcel("成品楹联订单数据", CoupletsOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/coupletsOrder/?repage";
    }

}