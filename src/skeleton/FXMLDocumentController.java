/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skeleton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


public class FXMLDocumentController implements Initializable,Runnable {
    private  MediaPlayer mediaplayer;
    private Media media;
    boolean playing = false;
    private InvalidationListener listener;
    private File file;
    public  Node selectedNode;
    Map<Node, Effect> nodes = new HashMap<Node, Effect>();
    
    Effect effect; 
    
    private String filepath;
    // TimeLine Slider
    @FXML
    private AnchorPane videoContainer;
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
    public VBox effectTreeView;
    @FXML
    private Button trim;
    @FXML
    private Label label;
    @FXML
    private Pane editPane;
    @FXML
    private CheckBox boldCheck;
    @FXML
    private CheckBox italicCheck;
//    @FXML
//    private Button button;
 
    @FXML
    private MediaView mediaview;
    
    @FXML
    private Button change;
    @FXML
    private ColorPicker textForegroundColor;
    @FXML
    private ColorPicker textBackgroundColor;
    @FXML
    private Pane textSelectionPane;
    @FXML
    private TextField textChange;
    @FXML
    private ChoiceBox fontFamilies;
    @FXML
    private ChoiceBox fontSize;
    @FXML
    private HBox videoTimeLine;
    
    
    public File fileChooser(){
           FileChooser chooser = new FileChooser();
        //This is used to filter the extentions
        FileChooser.ExtensionFilter filter  =  new FileChooser.ExtensionFilter("sth", "*.mp4");
        chooser.getExtensionFilters().add(filter);
        file = chooser.showOpenDialog(null);
        filepath = file.toURI().toString();
         new Save("Video","",filepath,true);
        return file;
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        file = fileChooser();
          
        if (file != null) {  
            
            
            
            videoSetup();
     
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
  // Displays the trim button with trim sliders 
    @FXML
    private void handleCut(ActionEvent event){
        tSliderEnd.setVisible(true);
        trim.setVisible(true);
        System.out.println(tSlider.getValue());
        mediaplayer.pause();
         
     };
    // selected node for the stack tree
   
    // Loads up the text edit panel on the editpane at home
     @FXML
     private void textEdit(ActionEvent event){
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("editEffectWindow.fxml"));
            editPane.getChildren().setAll(pane);
           
            // Iterate over the widgets in the effect stack to select the widget that was clicked
//            for (Node x : effectStack.getChildren()) {
//                if(selectedNode.equals(x)){
//                    selectedNode.setVisible(false);
//                }
//            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     @FXML
    private void handleTrim(ActionEvent event){
       int trimEnd = (int) tSlider.getValue();
       int mediaEnd = (int)mediaplayer.getStopTime().toSeconds();
       int trimStart = (int) tSliderEnd.getValue();
       
       
       Trim edit = new Trim();
       // Cut the video according to the start time and endTime
       edit.Cut(media,file, trimStart, trimEnd);
       mediaplayer.dispose();
      // filepath = edit.Merge("lower.mp4", "upper.mp4").toURI().toString();
       
       /**
        * change the path to absolute and dynamic
       *///
       
       filepath = "file:/C:/Users/SWL/Documents/NetBeansProjects/Skeleton/assets/video/cut.mp4";
       new Save("Cut", trimStart+"", trimEnd+"", true);
       while(!new File("C:\\Users\\SWL\\Documents\\NetBeansProjects\\Skeleton\\assets\\video\\cut.mp4").exists()){
           System.out.println("Waiting");
       }
       
       videoSetup();
         removeFiles("assets/video");
     
        tSliderEnd.setVisible(false);
        trim.setVisible(false);
    }
    
    public void removeFiles(String path){
           for (File file : new File(path).listFiles()) {
           file.delete();
       }
    }
   
     @FXML
    private void displayTextSelection(ActionEvent event){
        textSelectionPane.setVisible(true);
    }
   
    // Add a movable overlay text
    @FXML   
    private void addOverlay(ActionEvent event){
        Label x = new Label(" T e x t ");
        nodes.put(x, new Effect(x, "0", "5"));
        x.setOnMouseDragged(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                 x.setLayoutX(event.getSceneX());
                 x.setLayoutY(event.getSceneY());
            }
           
        });
        effectStack.getChildren().add(x);
        effectStack.getChildren().forEach(this::makeDraggable);
        
    }
    
    // Display all the nodes that are added in Video Player
    @FXML
    private void updateEffectTree(ActionEvent event){
         //Clear already existing nodes in the VBOX for refresh functionality
        effectTreeView.getChildren().clear();
        
        for (Node node : effectStack.getChildren()) {
         ;
           Button dummy  = new Button(node.toString());
           
           if(!node.equals(videoContainer)){
               dummy.setOnAction((ActionEvent event1) -> {     
                    selectedNode = node;  
                    editPane.setVisible(true);
           });
               effectTreeView.getChildren().add(dummy);
           }               
        }
          
    }
   
    
    @FXML
    private void applyChange(ActionEvent event){
         System.out.println(selectedNode);
         
         //Change the selected nod to a type of Lable to change the text
         Label text = (Label)selectedNode;
         text.setText(textChange.getText());
         
         String command = "";
         
         // Check the values that are inputed in the editPane
         
         command += 
                " -fx-background-color: " + toRGBCode(textBackgroundColor.getValue())+" ; "+ 
                " -fx-text-fill: " + toRGBCode(textForegroundColor.getValue()) + " ; " +
                " -fx-font-size: " + fontSize.getValue()+" ; "+
                " -fx-font-family : " + fontFamilies.getValue()+ " ; " ;
         
         
         if(boldCheck.isSelected()){
              command += " -fx-font-weight: bold; ";
         }else if(italicCheck.isSelected()){
             command += " -fx-font-style : italic ;";
         }else if(boldCheck.isSelected() && italicCheck.isSelected()){
              command += " -fx-font-weight: bold; ";
              command += " -fx-font-style : italic ;";
         }
         
         //Style the selected node using that setting   
         selectedNode.setStyle(command);
        
         new Save("Text",textChange.getText(),command , true);
     
    }
    
    
    @FXML
    private void loadMerged(ActionEvent event){
        videoSetup();
    }
    //Used for storing the difference between the node and the mouse pointer
    private double startX;
    private double startY;
    @FXML
    public void test() throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException{
      save("video");
    }
    
    private void save(String elementType ){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
          //  Document doc = docbuilder.parse(new File("staff-dom.txt"));
            Document doc = docbuilder.newDocument();
            Element root = doc.createElement("Video");
            
                Element video = doc.createElement("Video");
                Text path = doc.createTextNode(filepath);
                video.appendChild(path);
                root.appendChild(video);
           
            doc.appendChild(root);
            DOMSource source = new DOMSource(doc);
             Result result  = new StreamResult(new File("staff-dom.txt"));
             TransformerFactory ts = TransformerFactory.newInstance();
            Transformer t = ts.newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(source, result);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void writeXml(DOMSource source,Result result) {
        try {
           
            TransformerFactory ts = TransformerFactory.newInstance();
            Transformer t = ts.newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Handels the video loading and controls
    private void videoSetup(){
       
        
        Color[] colors = {Color.RED, Color.BISQUE, Color.BLUEVIOLET};
         int rand = (int) (Math.random()*3);
         
         Rectangle block = new Rectangle(videoTimeLine.getWidth(), videoTimeLine.getHeight());
         block.setFill(colors[rand]);
         videoTimeLine.getChildren().add(block);
            
            
         System.out.println("Added video :" + filepath);
        if (mediaplayer != null) {
            mediaplayer.dispose();
            mediaplayer.stop();
        }
           
        
         //  mediaplayer.dispose();
           media = new Media(filepath);
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
            tSlider.setOnMouseDragged((MouseEvent event) -> {
                mediaplayer.seek(Duration.seconds(tSlider.getValue()));
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
            
//             Thread overlay = new Thread(this);
//             overlay.start();
    }
    
   // Handels the dragable feature implemented in the effectStack
   private void makeDraggable(Node node){
       node.setOnMouseClicked(e->{
           startX = e.getSceneX() - node.getTranslateX();
           startY = e.getSceneY() - node.getTranslateY();
           
       });
       
       node.setOnMouseDragged(e->{
           node.setTranslateX(e.getSceneX() - startX);
           node.setTranslateY(e.getSceneY() - startY);
       });
   }
   public String milliSecondsToTimer(long milliseconds){
    String finalTimerString = "";
    String secondsString = "";

    // Convert total duration into time
       int hours = (int)( milliseconds / (1000*60*60));
       int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
       int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
       // Add hours if there
       if(hours > 0){
           finalTimerString = hours + ":";
       }

       // Prepending 0 to seconds if it is one digit
       if(seconds < 10){ 
           secondsString = "0" + seconds;
       }else{
           secondsString = "" + seconds;}

       finalTimerString = finalTimerString + minutes + ":" + secondsString;

    // return timer string
    return finalTimerString;
}
   
     public static int countLine(File fileName) {

      int lines = 0;
      try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
          while (reader.readLine() != null) lines++;
      } catch (IOException e) {
          e.printStackTrace();
      }
      return lines;

  }
     public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        removeFiles("assets/video");
        //Setting the mood :)
        if(new File("temp-save.xml").exists()){
        new File("temp-save.xml").delete();
        }
       
        tSliderEnd.setVisible(false);
        trim.setVisible(false);
        editPane.setVisible(false);
        textSelectionPane.setVisible(false);
        //Initializing the text editing window parameters
        String [] fonts = {"Arial","Monospace","Sans"};
        String [] fontSizes = {"10","15","30","40","50"};
        
        fontSize.getItems().addAll(fontSizes);
        //initalize Font Families
        fontFamilies.getItems().addAll(fonts);
        
        //Delete previous states
      
    }     

    
    public void run() {
        while (true) {     
              for (Map.Entry<Node, Effect> entrySet : nodes.entrySet()) {
                Node key = entrySet.getKey();
                Effect value = entrySet.getValue();
                System.out.println(key + "  " + value.getEndTime());
                  System.out.println((int)mediaplayer.getCurrentTime().toSeconds());
                if((int)mediaplayer.getCurrentTime().toSeconds() >= value.getEndTime()){
                  //  key.setVisible(false);
                    System.out.println(key.isVisible());
                   
                    mediaplayer.pause();
                     
                }
        }
            
            if((int)mediaplayer.getCurrentTime().toSeconds() == 5){
              // mediaplayer.pause();
            }
        }
    }
    
}
