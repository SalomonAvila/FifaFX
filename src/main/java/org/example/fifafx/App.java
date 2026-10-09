package org.example.fifafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VistaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        URL cssLocation = getClass().getResource("styles.css");
        scene.getStylesheets().add(cssLocation.toExternalForm());
        stage.setTitle("FIFA-FX Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
