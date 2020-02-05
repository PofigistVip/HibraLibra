package com.pofigist.hibraLibra2.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.pofigist.hibraLibra2.CreateEntityDialog;
import com.pofigist.hibraLibra2.EntityTableCreator;
import com.pofigist.hibraLibra2.entities.Book;
import com.pofigist.hibraLibra2.entities.BookOnShelf;
import com.pofigist.hibraLibra2.entities.TakenBook;
import com.pofigist.hibraLibra2.model.BookDao;
import com.pofigist.hibraLibra2.model.BookOnShelfDao;
import com.pofigist.hibraLibra2.model.BookcaseDao;
import com.pofigist.hibraLibra2.model.Model;
import com.pofigist.hibraLibra2.model.ReaderDao;
import com.pofigist.hibraLibra2.model.TakenBookDao;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class TakenBooksController implements Initializable {

	private List<TakenBook> books;
	
	public BorderPane borderPane;
	
	private TableView<TakenBook> tableView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateBooks();
		
	}
	
	private void updateBooks() {
		books = TakenBookDao.getDao().getAllByLibrary(Model.currentLibrary);
		EntityTableCreator<TakenBook> tableCreator = new EntityTableCreator<>();
		tableView = tableCreator.createTable(new TakenBook(), books);
		borderPane.setCenter(tableView);
	}

	public void addBook(ActionEvent event) {
		//Выбор книги из списка книг
		CreateEntityDialog<TakenBook> dialog = new CreateEntityDialog<>();
		TakenBook takenBook = new TakenBook();
		takenBook.setLibrary(Model.currentLibrary);
		takenBook.setReadersToSelect(ReaderDao.getDao().getAll());
		takenBook.setBooksToSelect(BookOnShelfDao.getDao().getAllByLibrary(Model.currentLibrary));
		TakenBook entity = dialog.showAndWait(takenBook, (obj) -> {
			return "";
		});
		if (entity != null) {
			takenBook.setBook(takenBook.getSelectedBook().getBook());
			TakenBookDao.getDao().save(entity);
			BookOnShelfDao.getDao().delete(takenBook.getSelectedBook());
			updateBooks();
		}
	}
	
	public void returnBook(ActionEvent event) {
		//Выбор книги из списка книг
		TakenBook selected = tableView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			CreateEntityDialog<BookOnShelf> dialog = new CreateEntityDialog<>();
			BookOnShelf bookOnShelf = new BookOnShelf();
			ArrayList<Book> lst = new ArrayList<Book>();
			lst.add(selected.getBook());
			bookOnShelf.setBooksToSelect(lst);
			bookOnShelf.setBookcasesToSelect(BookcaseDao.getDao().getAllByLibrary(Model.currentLibrary));
			
			BookOnShelf entity = dialog.showAndWait(bookOnShelf, (obj) -> {
				return "";
			});
			if (entity != null) {
				try {
					bookOnShelf.initPosition();
					BookOnShelfDao.getDao().save(entity);
					TakenBookDao.getDao().delete(selected);
				}
				catch (Exception e) {
					System.out.println(e);
				}
				
				updateBooks();
			}
		}
		
	}
}
