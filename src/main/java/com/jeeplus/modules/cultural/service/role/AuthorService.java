/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.role;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.role.Author;
import com.jeeplus.modules.cultural.mapper.role.AuthorMapper;

/**
 * 作者Service
 * @author orange
 * @version 2018-09-13
 */
@Service
@Transactional(readOnly = true)
public class AuthorService extends CrudService<AuthorMapper, Author> {

	public Author get(String id) {
		return super.get(id);
	}
	
	public List<Author> findList(Author author) {
		return super.findList(author);
	}
	
	public Page<Author> findPage(Page<Author> page, Author author) {
		return super.findPage(page, author);
	}
	
	@Transactional(readOnly = false)
	public void save(Author author) {
		super.save(author);
	}
	
	@Transactional(readOnly = false)
	public void delete(Author author) {
		super.delete(author);
	}
	
}