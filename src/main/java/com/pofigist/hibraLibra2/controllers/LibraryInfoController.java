package com.pofigist.hibraLibra2.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.pofigist.hibraLibra2.App;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LibraryInfoController implements Initializable {

	public AnchorPane content;
	private String currentView = "";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setContent("Bookcases");
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
	
	public void toBookcases(ActionEvent event) {
		setContent("Bookcases");
	}
	
	public void toBooks(ActionEvent event) {
		setContent("BooksInLibrary");
	}
	
	public void toTooks(ActionEvent event) {
		setContent("TakenBooks");
	}
}
