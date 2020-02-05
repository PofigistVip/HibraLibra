package com.pofigist.hibraLibra2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EntityTableCreator<T> {
	private ArrayList<TableColumn<T, Object>> getColumns(T obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		ArrayList<TableColumn<T, Object>> columns = new ArrayList<>();
		TableColumn<T, Object> column;
		for (Field field : fields) {
			UIField uie = field.getAnnotation(UIField.class);
			if (uie != null && uie.showInTable()) {
				column = new TableColumn<>(uie.title().isEmpty() ? field.getName() :uie.title());
				column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
				columns.add(column);
			}
		}
		return columns;
	}
	
	public TableView<T> createTable(T obj, List<T> items) {
		TableView<T> tableView = new TableView<>();
		ArrayList<TableColumn<T, Object>> columns = getColumns(obj);
		tableView.getColumns().addAll(columns);
		tableView.getItems().addAll(items);
		BorderPane.setMargin(tableView, new Insets(0, 0, 6, 6));
		return tableView;
	}
}
