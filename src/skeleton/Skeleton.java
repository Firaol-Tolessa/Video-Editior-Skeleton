
package skeleton;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Skeleton extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds.getHeight()  + " " + screenBounds.getWidth());
        Scene scene = new Scene(root);
       
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
