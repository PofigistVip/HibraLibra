package com.pofigist.hibraLibra2.model;

import java.util.List;

import org.hibernate.query.Query;

import com.pofigist.hibraLibra2.entities.BookOnShelf;
import com.pofigist.hibraLibra2.entities.Library;
import com.pofigist.hibraLibra2.entities.TakenBook;

public class TakenBookDao extends DaoObject<TakenBook> {

	private static TakenBookDao instance;
	
	public static TakenBookDao getDao() {
		if (instance == null) {
			instance = new TakenBookDao();
		}
		return instance;
	}
	
	private TakenBookDao() {
		
	}
	
	public List<TakenBook> getAll() {
		return super.getAll(TakenBook.class);
	}
	
	public List<TakenBook> getAllByLibrary(Library library) {
		Query<TakenBook> query = HibernateUtil.getSessionFactory().openSession().createQuery("from TakenBook where library_id=" + library.getLibraryId(), TakenBook.class);
		return query.list();
	}
}