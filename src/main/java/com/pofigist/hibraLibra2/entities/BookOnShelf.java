package com.pofigist.hibraLibra2.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.pofigist.hibraLibra2.UIField;

@Entity  
@Table(name= "books_on_shelves", uniqueConstraints = {@UniqueConstraint(columnNames = {"bookcase_id","position"})}) 
public class BookOnShelf {
	private int bookOnShelfId;
	@UIField(title="Книга", useInCreateDialog = true, showInTable = false, source="booksToSelect")
	private Book book;
	@UIField(title="Шкаф", useInCreateDialog = true, showInTable = false, source="bookcasesToSelect")
	private Bookcase bookcase;
	private int position;
	
	@UIField(title="Номер шкафа", useInCreateDialog = false, showInTable = true)
	private int bookcaseNumber;
	@UIField(title="Номер полки", useInCreateDialog = true, showInTable = true)
	private int shelfNumber;
	@UIField(title="Номер на полке", useInCreateDialog = true, showInTable = true)
	private int shelfPosition;
	@UIField(title="Название", useInCreateDialog = false, showInTable = true)
	private String bookTitle;
	@UIField(title="Издатель", useInCreateDialog = false, showInTable = true)
	private String bookPublisher;
	
	private List<Book> booksToSelect;
	private List<Bookcase> bookcasesToSelect;
	
	public void initPosition() {
		this.position = bookcase.getShelvesSize() * this.shelfNumber + this.shelfPosition;
	}
	
	@Id
	@Column(name="book_on_shl_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getBookOnShelfId() {
		return bookOnShelfId;
	}
	
	public void setBookOnShelfId(int bookOnShelfId) {
		this.bookOnShelfId = bookOnShelfId;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="book_id")
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
		this.bookTitle = this.book.getTitle();
		this.bookPublisher = this.book.getPublisher();
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="bookcase_id", referencedColumnName="bookcase_id")
	public Bookcase getBookcase() {
		return bookcase;
	}
	
	public void setBookcase(Bookcase bookcase) {
		this.bookcase = bookcase;
		this.bookcaseNumber = this.bookcase.getNumber();
	}
	
	@Column(name="position", nullable=false)
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
		this.shelfNumber = this.position / this.bookcase.getShelvesSize();
		this.shelfPosition = this.position % this.bookcase.getShelvesSize();
	}
	
	@Transient
	public int getBookcaseNumber() {
		return bookcaseNumber;
	}

	public void setBookcaseNumber(int bookcaseNumber) {
		this.bookcaseNumber = bookcaseNumber;
	}

	@Transient
	public int getShelfNumber() {
		return shelfNumber;
	}

	public void setShelfNumber(int shelfNumber) {
		this.shelfNumber = shelfNumber;
	}

	@Transient
	public int getShelfPosition() {
		return shelfPosition;
	}

	public void setShelfPosition(int shelfPosigion) {
		this.shelfPosition = shelfPosigion;
	}

	@Transient
	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	@Transient
	public String getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	@Transient
	public List<Book> getBooksToSelect() {
		return booksToSelect;
	}

	public void setBooksToSelect(List<Book> booksToSelect) {
		this.booksToSelect = booksToSelect;
	}

	@Transient
	public List<Bookcase> getBookcasesToSelect() {
		return bookcasesToSelect;
	}

	public void setBookcasesToSelect(List<Bookcase> bookcasesToSelect) {
		this.bookcasesToSelect = bookcasesToSelect;
	}
	
	@Override
	public String toString() {
		return book.toString() + " Шкаф №" + bookcase.getNumber() + ", полка " + shelfNumber + ", место " + shelfPosition;
	}
}
