/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.web.role;

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
import com.jeeplus.modules.cultural.entity.role.Author;
import com.jeeplus.modules.cultural.service.role.AuthorService;

/**
 * 作者Controller
 * @author orange
 * @version 2018-09-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cultural/role/author")
public class AuthorController extends BaseController {

	@Autowired
	private AuthorService authorService;
	
	@ModelAttribute
	public Author get(@RequestParam(required=false) String id) {
		Author entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = authorService.get(id);
		}
		if (entity == null){
			entity = new Author();
		}
		return entity;
	}
	
	/**
	 * 作者列表页面
	 */
	@RequiresPermissions("cultural:role:author:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cultural/role/authorList";
	}
	
		/**
	 * 作者列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cultural:role:author:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Author author, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Author> page = authorService.findPage(new Page<Author>(request, response), author); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑作者表单页面
	 */
	@RequiresPermissions(value={"cultural:role:author:view","cultural:role:author:add","cultural:role:author:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Author author, Model model) {
		model.addAttribute("author", author);
		return "modules/cultural/role/authorForm";
	}

	/**
	 * 保存作者
	 */
	@ResponseBody
	@RequiresPermissions(value={"cultural:role:author:add","cultural:role:author:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Author author, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, author)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		authorService.save(author);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存作者成功");
		return j;
	}
	
	/**
	 * 删除作者
	 */
	@ResponseBody
	@RequiresPermissions("cultural:role:author:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Author author, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		authorService.delete(author);
		j.setMsg("删除作者成功");
		return j;
	}
	
	/**
	 * 批量删除作者
	 */
	@ResponseBody
	@RequiresPermissions("cultural:role:author:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			authorService.delete(authorService.get(id));
		}
		j.setMsg("删除作者成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cultural:role:author:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Author author, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "作者"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Author> page = authorService.findPage(new Page<Author>(request, response, -1), author);
    		new ExportExcel("作者", Author.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出作者记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cultural:role:author:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Author> list = ei.getDataList(Author.class);
			for (Author author : list){
				try{
					authorService.save(author);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条作者记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条作者记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入作者失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/role/author/?repage";
    }
	
	/**
	 * 下载导入作者数据模板
	 */
	@RequiresPermissions("cultural:role:author:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "作者数据导入模板.xlsx";
    		List<Author> list = Lists.newArrayList(); 
    		new ExportExcel("作者数据", Author.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cultural/role/author/?repage";
    }

}