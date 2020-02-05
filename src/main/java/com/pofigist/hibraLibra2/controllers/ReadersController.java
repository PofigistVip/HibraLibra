package com.pofigist.hibraLibra2.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.pofigist.hibraLibra2.CreateEntityDialog;
import com.pofigist.hibraLibra2.EntityTableCreator;
import com.pofigist.hibraLibra2.entities.Library;
import com.pofigist.hibraLibra2.entities.Reader;
import com.pofigist.hibraLibra2.model.LibraryDao;
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

public class ReadersController implements Initializable {
	
	private final static Pattern phoneRegexpr = Pattern.compile("^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$");
	
	private List<Reader> readers;
	
	public BorderPane borderPane;
	
	private TableView<Reader> tableView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateReaders();
		
	}
	
	private void updateReaders() {
		readers = ReaderDao.getDao().getAll();
		EntityTableCreator<Reader> tableCreator = new EntityTableCreator<>();
		borderPane.setCenter(tableCreator.createTable(new Reader(), readers));
	}

	public void addReader(ActionEvent event) {
		CreateEntityDialog<Reader> dialog = new CreateEntityDialog<>();
		Reader entity = dialog.showAndWait(new Reader(), (obj) -> {
			if (obj.getName().isEmpty()) {
				return "Необходимо заполнить Имя";
			}
			if (obj.getSurname().isEmpty()) {
				return "Необходимо заполнить Фамилия";
			}
			if (obj.getPhone().isEmpty()) {
				return "Необходимо заполнить Телефон";
			}
			if (obj.getName().length() > 32) {
				return "Имя длиннее 32 символов";
			}
			if (obj.getSurname().length() > 32) {
				return "Фамилия длиннее 32 символов";
			}
			if (obj.getPhone().length() > 20) {
				return "Телефон длиннее 20 символов";
			}
			if (!phoneRegexpr.matcher(obj.getPhone()).matches()) {
				return "Неверный телефонный формат";
			}
			return "";
		});
		if (entity != null) {
			ReaderDao.getDao().save(entity);
			updateReaders();
		}
	}
	
}
