package com.pofigist.hibraLibra2.model;

import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;

import com.pofigist.hibraLibra2.entities.BookOnShelf;
import com.pofigist.hibraLibra2.entities.Bookcase;
import com.pofigist.hibraLibra2.entities.Library;

public class BookOnShelfDao extends DaoObject<BookOnShelf> {

	private static BookOnShelfDao instance;
	
	public static BookOnShelfDao getDao() {
		if (instance == null) {
			instance = new BookOnShelfDao();
		}
		return instance;
	}
	
	private BookOnShelfDao() {
		
	}
	
	public List<BookOnShelf> getAll() {
		return super.getAll(BookOnShelf.class);
	}
	
	public List<BookOnShelf> getAllByLibrary(Library library) {
		Query<BookOnShelf> query = HibernateUtil.getSessionFactory().openSession().createQuery("select bos from BookOnShelf as bos left join Bookcase as bcase on bos.bookcase=bcase.bookcaseId left join Library as lib on bcase.library=lib.libraryId where lib.libraryId=" + library.getLibraryId(), BookOnShelf.class);
		return query.list();
	}
}
