package com.pofigist.hibraLibra2;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class CreateEntityDialog<T> {
	private ArrayList<FieldInfo> getNeededFields(T obj) {
		ArrayList<FieldInfo> uiFields = new ArrayList<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			UIField uie = field.getAnnotation(UIField.class);
			if (uie != null && uie.useInCreateDialog()) {
				uiFields.add(new FieldInfo(field, uie));
			}
		}
		return uiFields;
	}
	
	private T entity;
	private Label errorLabel;
	
	private GridPane initGridPane(T obj, ArrayList<FieldInfo> uiFields) throws IllegalArgumentException, IllegalAccessException {
		GridPane gpane = new GridPane();
		gpane.setHgap(10);
		gpane.setVgap(10);
		gpane.setPadding(new Insets(20, 20, 10, 10));
		
		Label label;
		String title;
		TextField tfield;
		DatePicker datePicker;
		ComboBox comboBox;
		int i;
		for (i = 0; i < uiFields.size(); ++i) {
			FieldInfo finfo = uiFields.get(i);
			
			title = finfo.uiEntity.title().isEmpty() ? finfo.field.getName() : finfo.uiEntity.title();
			label = new Label(title);
			gpane.add(label, 0, i);
			
			if (finfo.field.getType() == Date.class) {
				datePicker = new DatePicker();
				finfo.datePicker = datePicker;
				gpane.add(datePicker, 1, i);
			} else if (!finfo.uiEntity.source().isEmpty()) {
				Field[] clsFields = obj.getClass().getDeclaredFields();
				for (Field clsField : clsFields) {
					if (clsField.getName().equals(finfo.uiEntity.source())) {
						boolean accessible = clsField.canAccess(obj);
						clsField.setAccessible(true);
						comboBox = new ComboBox();
						comboBox.getItems().addAll((List)clsField.get(obj));
						if (comboBox.getItems().size() == 1) {
							comboBox.getSelectionModel().select(0);
						}
						finfo.comboBox = comboBox;
						clsField.setAccessible(accessible);
						gpane.add(comboBox, 1, i);
						break ;
					}
				}
			}
			else {
				tfield = new TextField();
				tfield.setPromptText(title);
				finfo.textField = tfield;
				gpane.add(tfield, 1, i);
			}
		}
		errorLabel = new Label();
		errorLabel.setAlignment(Pos.CENTER);
		errorLabel.setMaxWidth(Double.MAX_VALUE);
		errorLabel.setTextFill(Color.RED);
		errorLabel.setWrapText(true);
		gpane.add(errorLabel, 0, i, 2, 1);
		return gpane;
	}
	
	private Dialog<Boolean> initDialog(GridPane gpane, ArrayList<FieldInfo> uiFields, EntityCheckerExpression<T> checker) {
		Dialog<Boolean> dialog = new Dialog<>();
		dialog.setTitle("Ввод");
	    dialog.getDialogPane().setContent(gpane);
	    ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
	    dialog.setResultConverter(dialogButton -> {
	        return (dialogButton == okButtonType);
	    });
	    Button btnOk = (Button)dialog.getDialogPane().lookupButton(okButtonType);
	    btnOk.addEventFilter(ActionEvent.ACTION, event -> {
	    	try {
	    		injectFieldsValue(entity, uiFields);
	    	}
	    	catch (Exception e) {
	    		System.err.println(e);
	    	}
	    	String result = checker.check(entity);
	    	if (!result.isEmpty()) {
	    		errorLabel.setText(result);
	    		errorLabel.setTooltip(new Tooltip(result));
	    		event.consume();
	    	}
	    });
	    return dialog;
	}
	
	private void injectFieldsValue(T obj, ArrayList<FieldInfo> uiFields) throws Exception{
		for (FieldInfo finfo : uiFields) {
			Class type = finfo.field.getType();
			Object value = null;
			if (!finfo.uiEntity.source().isEmpty()) value = finfo.comboBox.getValue();
			else if (Date.class == type) value = Date.from(Instant.from(finfo.datePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
			else if (String.class == type) value = finfo.textField.getText();
			else if (Boolean.class == type || Boolean.TYPE == type) value = Boolean.parseBoolean(finfo.textField.getText());
			else if (Byte.class == type || Byte.TYPE == type) value = Byte.parseByte(finfo.textField.getText());
			else if (Short.class == type || Short.TYPE == type) value = Short.parseShort(finfo.textField.getText());
			else if (Integer.class == type || Integer.TYPE == type) value = Integer.parseInt(finfo.textField.getText());
			else if (Long.class == type || Long.TYPE == type) value = Long.parseLong(finfo.textField.getText());
			else if (Float.class == type || Float.TYPE == type) value = Float.parseFloat(finfo.textField.getText());
			else if (Double.class == type || Double.TYPE == type) value = Double.parseDouble(finfo.textField.getText());
			else throw new Exception("Unsupportable type " + type);
			
			boolean accessible = finfo.field.canAccess(obj);
			finfo.field.setAccessible(true);
			finfo.field.set(obj, value);
			finfo.field.setAccessible(accessible);
		}
	}
	
	public T showAndWait(T obj, EntityCheckerExpression<T> checker) {
		entity = obj;
		ArrayList<FieldInfo> uiFields = getNeededFields(obj);
		GridPane gpane;
		try {
			gpane = initGridPane(obj, uiFields);
		}
		catch (Exception e) {
			System.err.println(e);
			return null;
		}
		Dialog<Boolean> dialog = initDialog(gpane, uiFields, checker);
		Optional<Boolean> result = dialog.showAndWait();
		if (!result.isPresent() || !result.get()) {
			return null;
		}
		try {
			injectFieldsValue(obj, uiFields);
		}
		catch (Exception e) {
			System.err.println(e);
			return null;
		}
		return obj;
	}
	
	class FieldInfo {
		public Field field;
		public UIField uiEntity;
		public TextField textField;
		public DatePicker datePicker;
		public ComboBox comboBox;
		
		public FieldInfo(Field field, UIField uiEntity) {
			this.field = field;
			this.uiEntity = uiEntity;
		}
	}
}
