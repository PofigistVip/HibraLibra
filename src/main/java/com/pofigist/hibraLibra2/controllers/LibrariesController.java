package com.pofigist.hibraLibra2.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

import com.pofigist.hibraLibra2.App;
import com.pofigist.hibraLibra2.CreateEntityDialog;
import com.pofigist.hibraLibra2.EntityTableCreator;
import com.pofigist.hibraLibra2.entities.*;
import com.pofigist.hibraLibra2.model.*;

public class LibrariesController implements Initializable {

	private List<Library> libraries;
	
	public BorderPane borderPane;
	
	private TableView<Library> tableView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateLibraries();
	}
	
	
	private void updateLibraries() {
		libraries = LibraryDao.getDao().getAll();
		EntityTableCreator<Library> tableCreator = new EntityTableCreator<>();
		tableView = tableCreator.createTable(new Library(), libraries);
		borderPane.setCenter(tableView);
	}
	
	public void selectLibrary(ActionEvent event) {
		Library selected = tableView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Model.currentLibrary = selected;
			App.mainController.toLibBtn.setDisable(false);
			App.mainController.toLibBtn.setText(selected.toString());
			App.mainController.setContent("LibraryInfo");
		}
	}
	
	public void createLibrary(ActionEvent event) {
		CreateEntityDialog<Library> dialog = new CreateEntityDialog<>();
		Library entity = dialog.showAndWait(new Library(), (lib) -> {
			String title = lib.getTitle();
			if (title.isEmpty()) {
				return "Пустое название библиотеки";
			}
			if (title.length() > 128) {
				return "Слишком длинное название";
			}
			return "";
		});
		if (entity != null) {
			LibraryDao.getDao().save(entity);
			updateLibraries();
		}
	}
}
