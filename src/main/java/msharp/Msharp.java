package msharp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Msharp extends Application {
    
    public static void main (String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("MainWindow.fxml")));
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream( "/icon.png" )));
        stage.setTitle("MSharp Compiler");
        Scene scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}