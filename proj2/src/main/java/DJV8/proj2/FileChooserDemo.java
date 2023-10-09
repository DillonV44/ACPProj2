package DJV8.proj2;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;

public class FileChooserDemo extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FileChooser Dialog");
        Button btn = new Button();
        btn.setText("Open FileChooser'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(".")); // set to current didrectory
                String sep = System.getProperty("file.separator");
//               fileChooser.setInitialDirectory(new File("c:"+sep+"users"+sep+"jcoffey"));
                //Show open file dialog
                File file = fileChooser.showOpenDialog(null);
                if(file != null)
                  System.out.println(file.getPath());
            }
        });
        Group g = new Group();
        g.getChildren().add(btn);
        primaryStage.setScene(new Scene(g, 300, 250));
        primaryStage.show();
    }
}