package com.pofigist.hibraLibra2.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pofigist.hibraLibra2.entities.*;

public class LibraryDao extends DaoObject<Library> {
	
private static LibraryDao instance;
	
	public static LibraryDao getDao() {
		if (instance == null) {
			instance = new LibraryDao();
		}
		return instance;
	}
	
	private LibraryDao() {
		
	}
	
	
	public List<Library> getAll() {
		return super.getAll(Library.class);
	}
}
