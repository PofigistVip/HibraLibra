package com.pofigist.hibraLibra2.entities;

import java.util.Date;
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

import com.pofigist.hibraLibra2.UIField;

@Entity  
@Table(name= "taken_books") 
public class TakenBook {
	private int takenBookId;
	@UIField(title="Читатель", useInCreateDialog = true, showInTable = false, source="readersToSelect")
	private Reader reader;
	private Book book;
	private Library library;
	@UIField(title="Дата взятия", useInCreateDialog=true, showInTable=true)
	private Date from;
	@UIField(title="Дата возвращения", useInCreateDialog=true, showInTable=true)
	private Date to;
	
	@UIField(title="Название", useInCreateDialog = false, showInTable = true)
	private String bookTitle;
	@UIField(title="Издатель", useInCreateDialog = false, showInTable = true)
	private String bookPublisher;
	@UIField(title="Читатель", useInCreateDialog = false, showInTable = true)
	private String readerString;
	
	@UIField(title="Книга", useInCreateDialog = true, showInTable = false, source="booksToSelect")
	private BookOnShelf selectedBook;
	private List<BookOnShelf> booksToSelect;
	
	private List<Reader> readersToSelect;
	
	@Id
	@Column(name="tbook_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getTakenBookId() {
		return takenBookId;
	}
	
	public void setTakenBookId(int takenBookId) {
		this.takenBookId = takenBookId;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="reader_id")
	public Reader getReader() {
		return reader;
	}
	
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="book_id")
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="library_id")
	public Library getLibrary() {
		return library;
	}
	
	public void setLibrary(Library library) {
		this.library = library;
	}
	
	@Column(name="from_date", nullable=false)
	public Date getFrom() {
		return from;
	}
	
	public void setFrom(Date from) {
		this.from = from;
	}
	
	@Column(name="to_date", nullable=false)
	public Date getTo() {
		return to;
	}
	
	public void setTo(Date to) {
		this.to = to;
	}

	@Transient
	public String getBookTitle() {
		this.bookTitle = book.getTitle();
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	@Transient
	public String getBookPublisher() {
		this.bookPublisher = book.getPublisher();
		return bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	@Transient
	public String getReaderString() {
		this.readerString = reader.getSurname() + " " + reader.getName() + " (" + reader.getPhone() + ")";
		return readerString;
	}

	public void setReaderString(String readerString) {
		this.readerString = readerString;
	}

	@Transient
	public BookOnShelf getSelectedBook() {
		return selectedBook;
	}

	public void setSelectedBook(BookOnShelf selectedBook) {
		this.selectedBook = selectedBook;
	}

	@Transient
	public List<BookOnShelf> getBooksToSelect() {
		return booksToSelect;
	}

	public void setBooksToSelect(List<BookOnShelf> booksToSelect) {
		this.booksToSelect = booksToSelect;
	}

	@Transient
	public List<Reader> getReadersToSelect() {
		return readersToSelect;
	}

	public void setReadersToSelect(List<Reader> readersToSelect) {
		this.readersToSelect = readersToSelect;
	}
}
