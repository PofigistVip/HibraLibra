package com.pofigist.hibraLibra2;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.pofigist.hibraLibra2.controllers.MainController;
import com.pofigist.hibraLibra2.model.HibernateUtil;
import com.pofigist.hibraLibra2.model.LibraryDao;

/**
 * JavaFX App
 */
public class App extends Application {

	public static MainController mainController;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainView"));
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}