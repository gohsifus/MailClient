package org.groupid.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.groupid.App;
import org.groupid.Model.ReadEmail;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;

public class WorkbenchController {

    public VBox VB;
    public ReadEmail readEmail;


    public void initialize(){

        VB.getChildren().clear();
        VB.getChildren().add(new Label("Список писем:"));

        readEmail = MainController.getMailList();

        Label from;
        Label subject;
        TextArea text = null;

        for(MimeMessage i : readEmail.mail){

            try {
                from = new Label("Сообщение от: " + i.getFrom()[0].toString());
                subject = new Label("Тема: " + i.getSubject());

                if(i.isMimeType("multipart/mixed")){

                    Multipart mp = (Multipart) i.getContent();

                    for(int j = 0; j < mp.getCount(); j++){
                        BodyPart part =  mp.getBodyPart(j);

                        if(part.isMimeType("text/plain")){
                            text = new TextArea(part.getContent().toString());
                        }
                    }

                } else if(i.isMimeType("text/plain")){
                    text = new TextArea(i.getContent().toString());
                }

                if(text != null){
                    text.setPrefHeight(100.0);
                }

                VB.getChildren().add(new Label(""));
                VB.getChildren().add(from);
                VB.getChildren().add(subject);
                if(text != null){
                    VB.getChildren().add(text);
                }

                Button del = new Button("Delete");
                del.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            i.setFlags(new Flags(Flags.Flag.DELETED), true);
                            System.out.println(i.getFlags().toString());
                            readEmail.inbox.close();
                            readEmail.store.close();
                            initialize();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                });
                VB.getChildren().add(del);
                
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void toSendClick() throws IOException {


        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/View/Send.fxml");
        loader.setLocation(xmlUrl);
        SendController sendController = loader.getController();
        Parent root = loader.load();

        App.stage.setScene(new Scene(root));
    }
}
