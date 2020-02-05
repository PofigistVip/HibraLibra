package com.pofigist.hibraLibra2.entities;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pofigist.hibraLibra2.UIField;

@Entity  
@Table(name= "libraries") 
public class Library {

	private int libraryId;
	
	@UIField(title="Название", useInCreateDialog=true, showInTable=true)
	private String title;
	
	private Collection<Bookcase> bookcases;
	
	public Library() {
		
	}
	
	public Library(String title) {
		this.title = title;
	}
	
	@Id
	@Column(name="library_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getLibraryId() {
		return libraryId;
	}
	
	public void setLibraryId(int value) {
		libraryId = value;
	}
	
	@Column(name="title", nullable=false, unique=true, length=128)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String value) {
		title = value;
	}

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="bookcase_id")
	public Collection<Bookcase> getBookcases() {
		return bookcases;
	}

	public void setBookcases(Collection<Bookcase> bookcases) {
		this.bookcases = bookcases;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
