/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.order;

import com.jeeplus.modules.cultural.entity.couplets.Lexicon;
import com.jeeplus.modules.cultural.entity.role.Author;
import com.jeeplus.modules.cultural.entity.spec.Typeface;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.entity.spec.Craft;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 楹联词库价格Entity
 * @author orange
 * @version 2018-10-20
 */
public class LexiconPrice extends DataEntity<LexiconPrice> {
	
	private static final long serialVersionUID = 1L;
	private Lexicon lexicon;		// 楹联词库
	private Author author;		// 作者
	private Typeface typeface;		// 字体
	private Size size;		// 尺寸
	private Frame frame;		// 楹联框
	private Craft craft;		// 制作工艺
	private String lexiconName;		// 词库标题
	private String authorName;		// 作者姓名
	private String typefaceName;		// 字体名
	private String sizeName;		// 尺寸名
	private String frameName;		// 楹联框名
	private String craftName;		// 工艺名
	private Double price;		// 价格
	
	public LexiconPrice() {
		super();
	}

	public LexiconPrice(String id){
		super(id);
	}

	@ExcelField(title="楹联词库", fieldType=Lexicon.class, value="lexicon.title", align=2, sort=1)
	public Lexicon getLexicon() {
		return lexicon;
	}

	public void setLexicon(Lexicon lexicon) {
		this.lexicon = lexicon;
	}
	
	@ExcelField(title="作者", fieldType=Author.class, value="author.name", align=2, sort=2)
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	@ExcelField(title="字体", fieldType=Typeface.class, value="typeface.name", align=2, sort=3)
	public Typeface getTypeface() {
		return typeface;
	}

	public void setTypeface(Typeface typeface) {
		this.typeface = typeface;
	}
	
	@ExcelField(title="尺寸", fieldType=Size.class, value="size.name", align=2, sort=4)
	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
	@ExcelField(title="楹联框", fieldType=Frame.class, value="frame.name", align=2, sort=5)
	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	
	@ExcelField(title="制作工艺", fieldType=Craft.class, value="craft.name", align=2, sort=6)
	public Craft getCraft() {
		return craft;
	}

	public void setCraft(Craft craft) {
		this.craft = craft;
	}
	
	@ExcelField(title="词库标题", align=2, sort=7)
	public String getLexiconName() {
		return lexiconName;
	}

	public void setLexiconName(String lexiconName) {
		this.lexiconName = lexiconName;
	}
	
	@ExcelField(title="作者姓名", align=2, sort=8)
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	@ExcelField(title="字体名", align=2, sort=9)
	public String getTypefaceName() {
		return typefaceName;
	}

	public void setTypefaceName(String typefaceName) {
		this.typefaceName = typefaceName;
	}
	
	@ExcelField(title="尺寸名", align=2, sort=10)
	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
	@ExcelField(title="楹联框名", align=2, sort=11)
	public String getFrameName() {
		return frameName;
	}

	public void setFrameName(String frameName) {
		this.frameName = frameName;
	}
	
	@ExcelField(title="工艺名", align=2, sort=12)
	public String getCraftName() {
		return craftName;
	}

	public void setCraftName(String craftName) {
		this.craftName = craftName;
	}
	
	@ExcelField(title="价格", align=2, sort=13)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}