package org.groupid.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.groupid.App;
import org.groupid.Model.ReadEmail;
import org.groupid.Model.SendEmail;

import java.io.*;
import java.net.URL;


public class SendController {

    public TextField textFieldTo;
    public TextField textFieldThema;
    public TextArea text;

    public void toMailList() throws IOException {

        //ReadEmail mailList = MainController.getMailList();

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/View/workBench.fxml");
        loader.setLocation(xmlUrl);
        WorkbenchController workbenchController = loader.getController();
        Parent root = loader.load();

        App.stage.setScene(new Scene(root));

    }

    public void sendMail() {

        SendEmail sendEmail = new SendEmail(textFieldTo.getText(), textFieldThema.getText());
        sendEmail.sendMessage(text.getText());
        try {
            toMailList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

