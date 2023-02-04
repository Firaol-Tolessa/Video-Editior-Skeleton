
package skeleton;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Skeleton extends Application {
    @FXML
    private HBox effectView;
     @FXML
    private StackPane effectStack;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds.getHeight()  + " " + screenBounds.getWidth());
        Scene scene = new Scene(root);
        //System.out.println(effectStack.getChildren().get(0));
//        effectView.getChildren().clear();
//        effectView.getChildren().add(new Button("hello"));
        stage.setScene(scene);
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
       
          
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
