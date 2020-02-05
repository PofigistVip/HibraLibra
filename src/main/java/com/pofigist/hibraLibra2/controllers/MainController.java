package com.pofigist.hibraLibra2.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pofigist.hibraLibra2.App;
import com.pofigist.hibraLibra2.entities.Library;
import com.pofigist.hibraLibra2.model.Model;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainController implements Initializable {
	//fx:controller="com.pofigist.hibraLibra2.controllers.LibrariesController"
	public AnchorPane content;
	public Button toLibBtn;
	
	private String currentView = "";

	public MainController() {
		App.mainController = this;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setContent("Libraries");
	}
	
	public void setContent(String view) {
		if (view.equals(currentView)) {
			return;
		}
		currentView = view;
		ObservableList<Node> childs = content.getChildren();
		if (childs.size() != 0) {
			childs.remove(0);
		}
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(view + "View.fxml"));
			Parent newView = fxmlLoader.load();
			childs.add(newView);
			AnchorPane.setTopAnchor(newView, 0.0);
			AnchorPane.setLeftAnchor(newView, 0.0);
			AnchorPane.setRightAnchor(newView, 0.0);
			AnchorPane.setBottomAnchor(newView, 0.0);
		}
		catch (Exception e) { System.out.println("err: " + e); }
		
	}
	
	public void toLibrariesList(ActionEvent event) {
		setContent("Libraries");
	}
	
	public void toBooksList(ActionEvent event) {
		setContent("Books");
	}
	
	public void toReadersList(ActionEvent event) {
		setContent("Readers");
	}
	
	public void toSelectedLibrary(ActionEvent event) {
		setContent("LibraryInfo");
	}
}
