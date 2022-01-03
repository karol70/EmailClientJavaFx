package com.barosanu.controller;

import com.barosanu.EmailManager;
import com.barosanu.controller.services.LoginService;
import com.barosanu.model.EmailAccount;
import com.barosanu.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.NoSuchProviderException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {

    @FXML
    private TextField emailAdressField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField passwordField;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() throws NoSuchProviderException {
        System.out.println("loginButtonAction!!");
        if (fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAdressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {

                EmailLoginResult emailLoginResult = loginService.getValue();

                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("login succesfull! " + emailAccount);
                        if(!viewFactory.isMainViewInitialized()){
                            viewFactory.showMainWindow();
                        }
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("invalid credentials!");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("unexpected error!");
                        return;
                }
            });

        }


    }
    private boolean fieldsAreValid() {
       if(emailAdressField.getText().isEmpty()){
           errorLabel.setText("Please fill email");
           return false;
       }
        if(passwordField.getText().isEmpty()){
            errorLabel.setText("Please fill password");
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAdressField.setText("javaclientfx@gmail.com");
        passwordField.setText("seanpaul30!");
    }
}
