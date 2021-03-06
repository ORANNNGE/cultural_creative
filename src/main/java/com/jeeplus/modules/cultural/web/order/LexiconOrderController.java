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
import com.jeeplus.modules.cultural.entity.order.LexiconOrder;
import com.jeeplus.modules.cultural.service.order.LexiconOrderService;

/**
 * 楹联词库订单Controller
 * @author orange
 * @version 2018-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/order/lexiconOrder")
public class LexiconOrderController extends BaseController {

	@Autowired
	private LexiconOrderService lexiconOrderService;
	
	@ModelAttribute
	public LexiconOrder get(@RequestParam(required=false) String id) {
		LexiconOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lexiconOrderService.get(id);
		}
		if (entity == null){
			entity = new LexiconOrder();
		}
		return entity;
	}
	
	/**
	 * 楹联词库订单列表页面
	 */
	@RequiresPermissions("cultural:order:lexiconOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/order/lexiconOrderList";
	}
	
		/**
	 * 楹联词库订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:lexiconOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LexiconOrder lexiconOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LexiconOrder> page = lexiconOrderService.findPage(new Page<LexiconOrder>(request, response), lexiconOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑楹联词库订单表单页面
	 */
	@RequiresPermissions(value={"cultural:order:lexiconOrder:view","cultural:order:lexiconOrder:add","cultural:order:lexiconOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LexiconOrder lexiconOrder, Model model) {
		model.addAttribute("lexiconOrder", lexiconOrder);
		return "modules/cultural/order/lexiconOrderForm";
	}

	/**
	 * 保存楹联词库订单
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:order:lexiconOrder:add","cultural:order:lexiconOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LexiconOrder lexiconOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, lexiconOrder)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		lexiconOrderService.save(lexiconOrder);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存楹联词库订单成功");
		return j;
	}
	
	/**
	 * 删除楹联词库订单
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:lexiconOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LexiconOrder lexiconOrder, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		lexiconOrderService.delete(lexiconOrder);
		j.setMsg("删除楹联词库订单成功");
		return j;
	}
	
	/**
	 * 批量删除楹联词库订单
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:lexiconOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			lexiconOrderService.delete(lexiconOrderService.get(id));
		}
		j.setMsg("删除楹联词库订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:lexiconOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LexiconOrder lexiconOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "楹联词库订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LexiconOrder> page = lexiconOrderService.findPage(new Page<LexiconOrder>(request, response, -1), lexiconOrder);
    		new ExportExcel("楹联词库订单", LexiconOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出楹联词库订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:order:lexiconOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LexiconOrder> list = ei.getDataList(LexiconOrder.class);
			for (LexiconOrder lexiconOrder : list){
				try{
					lexiconOrderService.save(lexiconOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条楹联词库订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条楹联词库订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入楹联词库订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/lexiconOrder/?repage";
    }
	
	/**
	 * 下载导入楹联词库订单数据模板
	 */
	@RequiresPermissions("cultural:order:lexiconOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "楹联词库订单数据导入模板.xlsx";
    		List<LexiconOrder> list = Lists.newArrayList(); 
    		new ExportExcel("楹联词库订单数据", LexiconOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/lexiconOrder/?repage";
    }

}