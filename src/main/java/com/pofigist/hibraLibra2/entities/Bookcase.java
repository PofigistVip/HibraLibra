package com.pofigist.hibraLibra2.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.pofigist.hibraLibra2.UIField;

@Entity  
@Table(name= "bookcases", uniqueConstraints = {@UniqueConstraint(columnNames = {"number","library_id"})}) 
public class Bookcase {
	private int bookcaseId;
	private Library library;
	@UIField(title="Номер шкафа", useInCreateDialog = true, showInTable = true)
	private int number;
	@UIField(title="Число полок", useInCreateDialog = true, showInTable = true)
	private int shelvesCount;
	@UIField(title="Размер полки", useInCreateDialog = true, showInTable = true)
	private int shelvesSize;
	
	private Collection<BookOnShelf> books;
	
	@UIField(title="Книг в шкафу", useInCreateDialog = false, showInTable = true)
	private int booksCount;
	
	public Bookcase() {
		
	}

	@Id
	@Column(name="bookcase_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getBookcaseId() {
		return bookcaseId;
	}

	public void setBookcaseId(int bookcaseId) {
		this.bookcaseId = bookcaseId;
	}

	@ManyToOne(optional=false, cascade=CascadeType.REMOVE)
	@JoinColumn(name="library_id")
	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	@Column(name="number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Column(name="shelves_count")
	public int getShelvesCount() {
		return shelvesCount;
	}

	public void setShelvesCount(int shelvesCount) {
		this.shelvesCount = shelvesCount;
	}

	@Column(name="shelves_size")
	public int getShelvesSize() {
		return shelvesSize;
	}

	public void setShelvesSize(int shelvesSize) {
		this.shelvesSize = shelvesSize;
	}

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="bookcase_id")
	public Collection<BookOnShelf> getBooks() {
		return books;
	}

	public void setBooks(Collection<BookOnShelf> books) {
		this.books = books;
	}

	@Transient
	public int getBooksCount() {
		this.booksCount = this.books.size();
		return booksCount;
	}

	public void setBooksCount(int booksCount) {
		this.booksCount = booksCount;
	}
	
	@Override
	public String toString() {
		return "Шкаф №" + number + " (" + shelvesCount + "x" + shelvesSize + ")";
	}
}
