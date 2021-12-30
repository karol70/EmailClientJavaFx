package com.barosanu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginWindowController {

    @FXML
    private TextField emailAdressField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField passwordField;

    @FXML
    void loginButtonAction() {
        System.out.println("click!");
    }

}
