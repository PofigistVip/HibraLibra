package com.pofigist.hibraLibra2.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.pofigist.hibraLibra2.CreateEntityDialog;
import com.pofigist.hibraLibra2.EntityTableCreator;
import com.pofigist.hibraLibra2.entities.Book;
import com.pofigist.hibraLibra2.entities.BookOnShelf;
import com.pofigist.hibraLibra2.entities.Reader;
import com.pofigist.hibraLibra2.model.BookDao;
import com.pofigist.hibraLibra2.model.BookOnShelfDao;
import com.pofigist.hibraLibra2.model.BookcaseDao;
import com.pofigist.hibraLibra2.model.Model;
import com.pofigist.hibraLibra2.model.ReaderDao;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class BooksInLibraryController implements Initializable {

	private List<BookOnShelf> books;
	
	public BorderPane borderPane;
	
	private TableView<BookOnShelf> tableView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateBooks();
		
	}
	
	private void updateBooks() {
		books = BookOnShelfDao.getDao().getAllByLibrary(Model.currentLibrary);
		EntityTableCreator<BookOnShelf> tableCreator = new EntityTableCreator<>();
		borderPane.setCenter(tableCreator.createTable(new BookOnShelf(), books));
	}

	
	
	public void addBook(ActionEvent event) {
		//Выбор книги из списка книг
		CreateEntityDialog<BookOnShelf> dialog = new CreateEntityDialog<>();
		BookOnShelf bookOnShelf = new BookOnShelf();
		bookOnShelf.setBooksToSelect(BookDao.getDao().getAll());
		bookOnShelf.setBookcasesToSelect(BookcaseDao.getDao().getAllByLibrary(Model.currentLibrary));
		BookOnShelf entity = dialog.showAndWait(bookOnShelf, (obj) -> {
			if (obj.getShelfNumber() < 1) {
				return "Неверный номер полки";
			}
			if (obj.getShelfPosition() < 1) {
				return "Неверная позиция на полке";
			}
			return "";
		});
		if (entity != null) {
			try {
				bookOnShelf.initPosition();
				BookOnShelfDao.getDao().save(entity);
			}
			catch (Exception e) {
				System.out.println(e);
			}
			
			updateBooks();
		}
	}
	
}
