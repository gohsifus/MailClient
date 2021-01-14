package org.groupid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.groupid.Controller.MainController;

import java.net.URL;

public class App extends Application {

    public static Stage stage;

    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/View/mainScene.fxml");
        loader.setLocation(xmlUrl);
        MainController mainController = loader.getController();
        Parent root = loader.load();

        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
