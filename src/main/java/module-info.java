module com.pofigist.hibraLibra2 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.persistence;
	requires org.hibernate.orm.core;

    opens com.pofigist.hibraLibra2 to javafx.fxml;
    exports com.pofigist.hibraLibra2;
    exports com.pofigist.hibraLibra2.controllers;
    exports com.pofigist.hibraLibra2.model;
    exports com.pofigist.hibraLibra2.entities;
}