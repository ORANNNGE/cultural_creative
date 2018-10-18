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
import com.jeeplus.modules.cultural.entity.order.Address;
import com.jeeplus.modules.cultural.service.order.AddressService;

/**
 * 收货地址Controller
 * @author orange
 * @version 2018-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/order/address")
public class AddressController extends BaseController {

	@Autowired
	private AddressService addressService;
	
	@ModelAttribute
	public Address get(@RequestParam(required=false) String id) {
		Address entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = addressService.get(id);
		}
		if (entity == null){
			entity = new Address();
		}
		return entity;
	}
	
	/**
	 * 收货地址列表页面
	 */
	@RequiresPermissions("cultural:order:address:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/order/addressList";
	}
	
		/**
	 * 收货地址列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:address:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Address address, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Address> page = addressService.findPage(new Page<Address>(request, response), address); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑收货地址表单页面
	 */
	@RequiresPermissions(value={"cultural:order:address:view","cultural:order:address:add","cultural:order:address:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Address address, Model model) {
		model.addAttribute("address", address);
		return "modules/cultural/order/addressForm";
	}

	/**
	 * 保存收货地址
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:order:address:add","cultural:order:address:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Address address, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, address)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		addressService.save(address);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存收货地址成功");
		return j;
	}
	
	/**
	 * 删除收货地址
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:address:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Address address, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		addressService.delete(address);
		j.setMsg("删除收货地址成功");
		return j;
	}
	
	/**
	 * 批量删除收货地址
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:address:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			addressService.delete(addressService.get(id));
		}
		j.setMsg("删除收货地址成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:order:address:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Address address, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "收货地址"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Address> page = addressService.findPage(new Page<Address>(request, response, -1), address);
    		new ExportExcel("收货地址", Address.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出收货地址记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:order:address:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Address> list = ei.getDataList(Address.class);
			for (Address address : list){
				try{
					addressService.save(address);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条收货地址记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条收货地址记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入收货地址失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/address/?repage";
    }
	
	/**
	 * 下载导入收货地址数据模板
	 */
	@RequiresPermissions("cultural:order:address:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "收货地址数据导入模板.xlsx";
    		List<Address> list = Lists.newArrayList(); 
    		new ExportExcel("收货地址数据", Address.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/order/address/?repage";
    }

}