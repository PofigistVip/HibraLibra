package com.pofigist.hibraLibra2.model;

import java.util.List;

import com.pofigist.hibraLibra2.entities.Bookcase;
import com.pofigist.hibraLibra2.entities.Library;

public class BookcaseDao extends DaoObject<Bookcase> {

	private static BookcaseDao instance;
	
	public static BookcaseDao getDao() {
		if (instance == null) {
			instance = new BookcaseDao();
		}
		return instance;
	}
	
	private BookcaseDao() {
		
	}
	
	public List<Bookcase> getAll() {
		return super.getAll(Bookcase.class);
	}
	
	public List<Bookcase> getAllByLibrary(Library library) {
		try
		{
			return HibernateUtil.getSessionFactory().openSession().createQuery("From Bookcase where library_id=" + library.getLibraryId(), Bookcase.class).list();
		}
		catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}
}
