package com.pofigist.hibraLibra2.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.pofigist.hibraLibra2.CreateEntityDialog;
import com.pofigist.hibraLibra2.EntityTableCreator;
import com.pofigist.hibraLibra2.entities.*;
import com.pofigist.hibraLibra2.model.BookDao;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class BooksController implements Initializable {

	private List<Book> books;
	
	public BorderPane borderPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateBooks();
		
	}
	
	private void updateBooks() {
		books = BookDao.getDao().getAll();
		EntityTableCreator<Book> tableCreator = new EntityTableCreator<>();
		borderPane.setCenter(tableCreator.createTable(new Book(), books));
	}

	public void addBook(ActionEvent event) {
		CreateEntityDialog<Book> dialog = new CreateEntityDialog<>();
		Book entity = dialog.showAndWait(new Book(), (obj) -> {
			if (obj.getIsbn().isEmpty()) {
				return "Необходимо заполнить ISBN";
			}
			if (obj.getTitle().isEmpty()) {
				return "Необходимо заполнить Название";
			}
			if (obj.getPublisher().isEmpty()) {
				return "Необходимо заполнить Издатель";
			}
			if (obj.getIsbn().length() > 13) {
				return "ISBN длиннее 13 символов";
			}
			if (obj.getTitle().length() > 128) {
				return "Название длиннее 128 символов";
			}
			if (obj.getPublisher().length() > 128) {
				return "Издатель длиннее 128 символов";
			}
			return "";
		});
		if (entity != null) {
			BookDao.getDao().save(entity);
			updateBooks();
		}
	}
	
}
