
package skeleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class FXMLDocumentController1 implements Initializable {
    
    //store the changes made
    List<Node> change = new ArrayList<>();
      @FXML
    private TextField fileName;
    @FXML
    private TextField directorySelected; 
    @FXML
    private Label label;
 
     
    public void saveFileDialog(){
        try {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("saveScreen.fxml"));
            Scene scene = new Scene(root);;
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(selectedNode.getScene().getWindow());
            stage.show();
//        stage.setWidth(screenBounds.getWidth());
//        stage.setHeight(screenBounds.getHeight());
       
          
        
//        stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    @FXML
    private void loadFile(){
        DirectoryChooser directorychooser = new DirectoryChooser();
        File selectedDirectory = directorychooser.showDialog(null);
        directorySelected.setText(selectedDirectory.getAbsolutePath());
    }
    
    @FXML
    private void saveFile(){
        try {
            File srcFile = new File("temp-save.xml");
            System.out.println(directorySelected.getText()+"\\"+fileName.getText()+".xml");
            String[] args = {"cmd","/c","copy",srcFile.getAbsolutePath()+"",directorySelected.getText()+"\\"+fileName.getText()+".xml"};
            Process copy = Runtime.getRuntime().exec(args);
            copy.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        saveFileDialog();
        
    }    
    
}
