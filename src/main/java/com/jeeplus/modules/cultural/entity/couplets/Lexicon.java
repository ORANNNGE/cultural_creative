/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.couplets;

import org.hibernate.validator.constraints.Length;
import com.jeeplus.modules.cultural.entity.role.Author;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 词库Entity
 * @author orange
 * @version 2018-09-17
 */
public class Lexicon extends DataEntity<Lexicon> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String rightline;		// 上联
	private String leftline;		// 下联
	private String topline;		// 横批
	private String type;		// 类型
	private String isOriginal;		// 是否原创
	private Author author;		// 作者
	private String meaning;		// 寓意
	private String picture;		// 图片
	
	public Lexicon() {
		super();
	}

	public Lexicon(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=4, max=32, message="上联长度必须介于 4 和 32 之间")
	@ExcelField(title="上联", align=2, sort=2)
	public String getRightline() {
		return rightline;
	}

	public void setRightline(String rightline) {
		this.rightline = rightline;
	}
	
	@Length(min=4, max=32, message="下联长度必须介于 4 和 32 之间")
	@ExcelField(title="下联", align=2, sort=3)
	public String getLeftline() {
		return leftline;
	}

	public void setLeftline(String leftline) {
		this.leftline = leftline;
	}
	
	@ExcelField(title="横批", align=2, sort=4)
	public String getTopline() {
		return topline;
	}

	public void setTopline(String topline) {
		this.topline = topline;
	}
	
	@ExcelField(title="类型", dictType="cultural_lexicon_type", align=2, sort=5)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="是否原创", dictType="cultural_lexicon_original", align=2, sort=6)
	public String getIsOriginal() {
		return isOriginal;
	}

	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}
	
	@ExcelField(title="作者", fieldType=Author.class, value="author.name", align=2, sort=7)
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	@ExcelField(title="寓意", align=2, sort=8)
	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	
	@ExcelField(title="图片", align=2, sort=9)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}