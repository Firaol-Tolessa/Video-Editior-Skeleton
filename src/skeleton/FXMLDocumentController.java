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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author SWL
 */
public class FXMLDocumentController implements Initializable {
    private  MediaPlayer mediaplayer;
    boolean playing = false;
    private InvalidationListener listener;
    private String filepath;
    // TimeLine Slider
    @FXML
    private Slider tSlider;
    @FXML
    private Slider vSlider;
    @FXML
    private Label label;
//    @FXML
//    private Button button;
    @FXML
    private MediaView mediaview;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
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
            
           // This is used to synchronize the video player with the tSlider
            mediaplayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){

                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    tSlider.setValue(newValue.toSeconds());
                  
                }
                
            });
            
            // This two lets the video be seeked through the tSlider
            tSlider.setOnMouseDragged(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    mediaplayer.seek(Duration.seconds(tSlider.getValue()));
                }
                
            });
            
         
            tSlider.setOnMousePressed(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    mediaplayer.seek(Duration.seconds(tSlider.getValue()));
                }
                
            });
            
            
            // This helps to set the endpoint for the tSlider to be the same as the video end time
            mediaplayer.setOnReady(new Runnable() {

                @Override
                public void run() {
                   Duration time =  media.getDuration();
                   tSlider.setMax(time.toSeconds());
                }
            });
            
           
            mediaplayer.play();
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
        tSlider.showTickMarksProperty().setValue(true);
        tSlider.showTickLabelsProperty().setValue(true);
       
        
    }     
    
}
