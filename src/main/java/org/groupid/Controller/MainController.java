package org.groupid.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.groupid.App;
import org.groupid.Model.ReadEmail;
import org.groupid.Model.SendEmail;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class MainController {

    public final static String PROPS_FILE = "src/main/resources/mail.properties";

    public Button sendButton;
    public TextField textFieldLogin;
    public TextField textFieldPassword;

    public static String login;
    public static String password;

    public static ReadEmail getMailList(){

        return new ReadEmail(login, password);

    }


    public void ButtonClicked() throws IOException {

        login = textFieldLogin.getText();
        password = textFieldPassword.getText();

        //getMailList();

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/View/workBench.fxml");
        loader.setLocation(xmlUrl);
        WorkbenchController workbenchController = loader.getController();
        Parent root = loader.load();

        App.stage.setScene(new Scene(root));

    }





}
