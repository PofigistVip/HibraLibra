package com.pofigist.hibraLibra2.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pofigist.hibraLibra2.entities.*;

public class BookDao extends DaoObject<Book>  {
	
	private static BookDao instance;
	
	public static BookDao getDao() {
		if (instance == null) {
			instance = new BookDao();
		}
		return instance;
	}
	
	private BookDao() {
		
	}
	
	public List<Book> getAll() {
		return super.getAll(Book.class);
	}
}
