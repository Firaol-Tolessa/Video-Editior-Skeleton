/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skeleton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    private File file;
    public double[] cutTime = new double[2];
   
    private String filepath;
    // TimeLine Slider
    @FXML
    private Slider tSlider;
    @FXML
    private ImageView timeLineImage;
    @FXML
    private Slider vSlider;
    @FXML
    private Slider tSliderEnd;
    @FXML
    private StackPane effectStack;
//    @FXML
//    private HBox effectView;
    @FXML
    private Button trim;
    @FXML
    private Label label;
//    @FXML
//    private Button button;
    @FXML
    private MediaView mediaview;
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        
        FileChooser chooser = new FileChooser();
        //This is used to filter the extentions
        FileChooser.ExtensionFilter filter  =  new FileChooser.ExtensionFilter("sth", "*.mp4");
        chooser.getExtensionFilters().add(filter);
        file = chooser.showOpenDialog(null);
        filepath = file.toURI().toString();
        
        if (file != null) {
            // Make a media from a file
            Media media = new Media(filepath);
            // Instantiate a mediaplayer on the media
            mediaplayer = new MediaPlayer(media);
            // Add the mediaplayer to media view
            mediaview.setMediaPlayer(mediaplayer);
            
     
//            try {
//                if(new File("001.jpg").exists()){
//                    
//                }else{
//               // Process split = Runtime.getRuntime().exec("cmd /c start " +"ffmpeg -i "+ file + " -r 1/20 %03d.jpg");
//                Process stitch = Runtime.getRuntime().exec("cmd /k start " +"ffmpeg -i %03d.jpg  -filter_complex \"scale=80:-1,tile=20x1:padding=6\" output2.png");
////                
//                }
//                
//               // timeLineImage.setImage(new Image("C:\\Users\\SWL\\Documents\\NetBeansProjects\\Skeleton\\output.png"));
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
           
            
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
                   tSliderEnd.setMax(time.toSeconds());
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
  
    @FXML
    private void handleCut(ActionEvent event){
        tSliderEnd.setVisible(true);
        trim.setVisible(true);
        System.out.println(tSlider.getValue());
     
     };
                
    
     @FXML
    private void handleTrim(ActionEvent event){
       int trimEnd = (int) tSlider.getValue();
       int mediaEnd = (int)mediaplayer.getStopTime().toSeconds();
       int trimStart = (int) tSliderEnd.getValue();
       
       //Duration xs = Duration.hours(trimEnd);
       
//        String videoPath = "C:\\Users\\SWL\\Downloads\\Documents\\output.mp4";
        String check[] = {"cmd" , "/c", "start", "ffmpeg","-ss",trimStart+"","-t",trimEnd+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\cut.mp4"};
        if(trimStart == 0){
            String upper[] = {"cmd" , "/c", "start", "ffmpeg","-ss",trimEnd+"","-t",mediaEnd+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\lower.mp4"};
        }else if(trimEnd == mediaEnd){
            String lower[] = {"cmd" , "/c", "start", "ffmpeg","-ss",0+"","-t",trimStart+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\lower.mp4"};
        }else{
            String lower[] = {"cmd" , "/c", "start", "ffmpeg","-ss",0+"","-t",trimStart+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\lower.mp4"};
            String upper[] = {"cmd" , "/c", "start", "ffmpeg","-ss",trimEnd+"","-t",mediaEnd+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\upper.mp4"};
           try {
               Process cutUpper = Runtime.getRuntime().exec(upper);
               Process cut = Runtime.getRuntime().exec(check);
               Process cutLower = Runtime.getRuntime().exec(lower);
           } catch (IOException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        }
       // System.out.println(check);
        
//        try {
//            Process cut = Runtime.getRuntime().exec(check);
//            for (String check1 : check) {
//                System.out.print(check1 + " ");
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
       
       
//       List<Node> effects = new ArrayList<>();
//       System.out.println("The cut is going to be from :" +trimStart + " - " + trimEnd );
//        // System.out.println(effectStack.getChildren());
//         for (Node effect : effectStack.getChildren()) {
//             effects.add(effect);
//         }
//         effectView.getChildren().clear();
//         effectView.getChildren().addAll(effects);
             //System.out.println(effect.getProperties());
           //  effectView.getChildren().add(effectStack.getChildren().get(0));
      // Button b = new Button("hello");
      ////
      // effectView.getChildren().addAll(new Button("hello"),new Button("x"));
        // }
       mediaplayer.dispose();
//         for (int i = 0; i < 2; i++) {
            Media media = new Media("assets\\video\\lower.mp4");
            mediaplayer = new MediaPlayer(media);
            mediaview = new MediaView(mediaplayer);
            mediaplayer.play();
            
         
        tSliderEnd.setVisible(false);
        trim.setVisible(false);
    }

   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tSliderEnd.setVisible(false);
        trim.setVisible(false);
        
    }     
    
}
