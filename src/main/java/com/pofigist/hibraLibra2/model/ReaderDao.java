package com.pofigist.hibraLibra2.model;

import java.util.List;

import com.pofigist.hibraLibra2.entities.Reader;

public class ReaderDao extends DaoObject<Reader> {
	
	private static ReaderDao instance;
	
	public static ReaderDao getDao() {
		if (instance == null) {
			instance = new ReaderDao();
		}
		return instance;
	}
	
	private ReaderDao() {
		
	}
	
	
	public List<Reader> getAll() {
		return super.getAll(Reader.class);
	}
}