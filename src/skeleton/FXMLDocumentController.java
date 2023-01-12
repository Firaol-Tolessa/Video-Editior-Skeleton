/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skeleton;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author SWL
 */
public class FXMLDocumentController implements Initializable {
    private  MediaPlayer mediaplayer;
    private String filepath;
    @FXML
    private Slider slider;
    @FXML
    private Label label;
//    @FXML
//    private Button button;
    @FXML
    private MediaView mediaview;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        //label.setText("Hello World!");
        FileChooser chooser = new FileChooser();
        //This is used to filter the extentions
        FileChooser.ExtensionFilter filter  =  new FileChooser.ExtensionFilter("sth", "*.mp4");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(null);
        filepath = file.toURI().toString();
        
        if (file != null) {
            // Make a media from a file
            Media media = new Media(filepath);
            // Instantiate a mediaplayer on the media
            mediaplayer = new MediaPlayer(media);
            // Add the mediaplayer to media view
            mediaview.setMediaPlayer(mediaplayer);
 
            //To let the video fill the spaces left
//            DoubleProperty width = mediaview.fitWidthProperty();
//            DoubleProperty height = mediaview.fitHeightProperty();
//            
//            width.bind(Bindings.selectDouble(mediaview.sceneProperty(), "width"));
//            height.bind(Bindings.selectDouble(mediaview.sceneProperty(), "height"));
            
            slider.setValue(mediaplayer.getStartTime().toSeconds());
            while(mediaplayer.getCurrentTime().toSeconds() !=  0){
                slider.setValue(mediaplayer.getCurrentTime().toSeconds());
            }
            
            slider.valueProperty().addListener(new InvalidationListener() {
            
                @Override
                public void invalidated(Observable observable) {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                       mediaplayer.seek(Duration.seconds(slider.getValue()));
                }
            });
            mediaplayer.play();
           // mediaplayer.pause();
        }
    }
    @FXML
    private void handlePause(ActionEvent event){
        mediaplayer.pause();
    }
    @FXML
    private void handleFast(ActionEvent event){
       mediaplayer.setRate(1.5);
    }
     @FXML
    private void handleSlow(ActionEvent event){
         mediaplayer.setRate(0.5);
    }
    @FXML
    private void handleExit(ActionEvent event){
        mediaplayer.dispose();
    }
    @FXML
    private void handlePlay(ActionEvent event){
        mediaplayer.play();
        mediaplayer.setRate(1);
    }
     @FXML
    private void handleStop(ActionEvent event){
       System.exit(0);
    }
  
    
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
    
}
