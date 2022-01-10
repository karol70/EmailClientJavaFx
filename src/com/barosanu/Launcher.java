package com.barosanu;

import com.barosanu.controller.persistance.PersistanceAccess;
import com.barosanu.controller.persistance.ValidAccount;
import com.barosanu.controller.services.LoginService;
import com.barosanu.model.EmailAccount;
import com.barosanu.view.ViewFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Launcher extends Application {

    public static void main(String[] args){
        launch(args);
    }
    private PersistanceAccess persistanceAccess = new PersistanceAccess();
    private EmailManager emailManager = new EmailManager();

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> validAccountList = persistanceAccess.loadFromPresistance();
        if(validAccountList.size() > 0){
            viewFactory.showMainWindow();
            for (ValidAccount validAccount: validAccountList){
                EmailAccount emailAccount = new EmailAccount(validAccount.getAdress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        } else {
            viewFactory.showLoginWindow();
        }
    }

    @Override
    public void stop() throws  Exception{
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for (EmailAccount emailAccount: emailManager.getEmailAccounts()){
            validAccountList.add(new ValidAccount(emailAccount.getAdress(), emailAccount.getPassword()));
        }
        persistanceAccess.saveToPersistance(validAccountList);
    }

}
