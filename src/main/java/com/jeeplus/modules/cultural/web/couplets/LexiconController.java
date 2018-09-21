/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.couplets;

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
import com.jeeplus.modules.cultural.entity.couplets.Lexicon;
import com.jeeplus.modules.cultural.service.couplets.LexiconService;

/**
 * 词库Controller
 * @author orange
 * @version 2018-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/couplets/lexicon")
public class LexiconController extends BaseController {

	@Autowired
	private LexiconService lexiconService;
	
	@ModelAttribute
	public Lexicon get(@RequestParam(required=false) String id) {
		Lexicon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lexiconService.get(id);
		}
		if (entity == null){
			entity = new Lexicon();
		}
		return entity;
	}
	
	/**
	 * 词库列表页面
	 */
	@RequiresPermissions("cultural:couplets:lexicon:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/couplets/lexiconList";
	}
	
		/**
	 * 词库列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:couplets:lexicon:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Lexicon lexicon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Lexicon> page = lexiconService.findPage(new Page<Lexicon>(request, response), lexicon); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑词库表单页面
	 */
	@RequiresPermissions(value={"cultural:couplets:lexicon:view","cultural:couplets:lexicon:add","cultural:couplets:lexicon:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Lexicon lexicon, Model model) {
		model.addAttribute("lexicon", lexicon);
		return "modules/cultural/couplets/lexiconForm";
	}

	/**
	 * 保存词库
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:couplets:lexicon:add","cultural:couplets:lexicon:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Lexicon lexicon, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, lexicon)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		lexiconService.save(lexicon);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存词库成功");
		return j;
	}
	
	/**
	 * 删除词库
	 */
	@ResponseBody
	@RequiresPermissions("cultural:couplets:lexicon:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Lexicon lexicon, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		lexiconService.delete(lexicon);
		j.setMsg("删除词库成功");
		return j;
	}
	
	/**
	 * 批量删除词库
	 */
	@ResponseBody
	@RequiresPermissions("cultural:couplets:lexicon:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			lexiconService.delete(lexiconService.get(id));
		}
		j.setMsg("删除词库成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:couplets:lexicon:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Lexicon lexicon, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "词库"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Lexicon> page = lexiconService.findPage(new Page<Lexicon>(request, response, -1), lexicon);
    		new ExportExcel("词库", Lexicon.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出词库记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:couplets:lexicon:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Lexicon> list = ei.getDataList(Lexicon.class);
			for (Lexicon lexicon : list){
				try{
					lexiconService.save(lexicon);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条词库记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条词库记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入词库失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/couplets/lexicon/?repage";
    }
	
	/**
	 * 下载导入词库数据模板
	 */
	@RequiresPermissions("cultural:couplets:lexicon:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "词库数据导入模板.xlsx";
    		List<Lexicon> list = Lists.newArrayList(); 
    		new ExportExcel("词库数据", Lexicon.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/couplets/lexicon/?repage";
    }

}