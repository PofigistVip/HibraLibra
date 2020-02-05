package com.pofigist.hibraLibra2.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.pofigist.hibraLibra2.CreateEntityDialog;
import com.pofigist.hibraLibra2.EntityTableCreator;
import com.pofigist.hibraLibra2.entities.Bookcase;
import com.pofigist.hibraLibra2.entities.Library;
import com.pofigist.hibraLibra2.entities.Reader;
import com.pofigist.hibraLibra2.model.BookcaseDao;
import com.pofigist.hibraLibra2.model.LibraryDao;
import com.pofigist.hibraLibra2.model.Model;
import com.pofigist.hibraLibra2.model.ReaderDao;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class BookcasesController implements Initializable {
	
	private List<Bookcase> bookcases;
	
	public BorderPane borderPane;
	
	private TableView<Bookcase> tableView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateBookcases();
	}
	
	private void updateBookcases() {
		bookcases = BookcaseDao.getDao().getAllByLibrary(Model.currentLibrary);
		EntityTableCreator<Bookcase> tableCreator = new EntityTableCreator<>();
		tableView = tableCreator.createTable(new Bookcase(), bookcases);
		borderPane.setCenter(tableView);
		
	}

	public void addBookcase(ActionEvent event) {
		CreateEntityDialog<Bookcase> dialog = new CreateEntityDialog<>();
		Bookcase entity = dialog.showAndWait(new Bookcase(), (obj) -> {
			if (obj.getShelvesCount() <= 0) {
				return "Число полок не может быть меньше/равно нулю";
			}
			if (obj.getShelvesCount() <= 0) {
				return "Размер полки не может быть меньше/равно нулю";
			}
			return "";
		});
		if (entity != null) {
			entity.setLibrary(Model.currentLibrary);
			BookcaseDao.getDao().save(entity);
			updateBookcases();
		}
	}
}
