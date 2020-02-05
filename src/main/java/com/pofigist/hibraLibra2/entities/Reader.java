package com.pofigist.hibraLibra2.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pofigist.hibraLibra2.UIField;

@Entity  
@Table(name= "readers") 
public class Reader {
	private int readerId;
	
	@UIField(title="Имя", useInCreateDialog=true, showInTable=true)
	private String name;
	@UIField(title="Фамилия", useInCreateDialog=true, showInTable=true)
	private String surname;
	@UIField(title="Телефон", useInCreateDialog=true, showInTable=true)
	private String phone;
	
	private Collection<TakenBook> books;
	
	@UIField(title="Взято книг", useInCreateDialog = false, showInTable = true)
	private int booksCount;
	
	public Reader() {
		
	}
	
	public Reader(String surname, String name, String phone) {
		this.name = name;
		this.surname = surname;
		this.phone = phone;
	}
	
	@Id
	@Column(name="reader_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getReaderId() {
		return readerId;
	}
	
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}
	
	@Column(name="name", nullable=false, unique=false, length=32)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="surname", nullable=false, unique=false, length=32)
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Column(name="phone", nullable=false, unique=false, length=20)
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@OneToMany(mappedBy="takenBookId", fetch=FetchType.LAZY)
	public Collection<TakenBook> getBooks() {
		return books;
	}

	public void setBooks(Collection<TakenBook> books) {
		this.books = books;
		this.booksCount = this.books.size();
	}

	@Transient
	public int getBooksCount() {
		return booksCount;
	}

	public void setBooksCount(int booksCount) {
		this.booksCount = booksCount;
	}
	
	@Override
	public String toString() {
		return surname + " " + name + " (" + phone + ")";
	}
}
