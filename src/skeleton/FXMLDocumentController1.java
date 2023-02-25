
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
import javafx.fxml.Initializable;
//import javafx.scene.Node;
import javafx.scene.control.Label;
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
    private Label label;
 
    private Element getElement(Element rootElement ,Integer hash){
        NodeList nodes = rootElement.getChildNodes();
        Element node = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element temp  = (Element)nodes.item(i);
            if(temp.hashCode() == hash){
                node = temp;
            }
        }
        
        return node;
    }
    private void add(Element element){
         System.out.println(element);
        Document doc = null;
        if(new File("temp-save.xml").exists()){
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                doc = docBuilder.parse("temp-save.xml");
                Element rootElement = (Element)doc.getElementsByTagName("Video").item(0);
                
                rootElement.appendChild(getElement(rootElement, element.hashCode()));
                
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
             try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("Video");
                
                rootElement.appendChild(element);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
         try (FileOutputStream output =
                    new FileOutputStream("temp-save.xml")) {
            writeXml(doc, output);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransformerException ex) {
            Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    private void remove(Element element){
            System.out.println(element);
            Document doc  = null;
        if(new File("temp-save.xml").exists()){
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                doc = docBuilder.parse("temp-save.xml");
                Element rootElement = (Element)doc.getElementsByTagName("Video").item(0);
              
                rootElement.removeChild(getElement(rootElement, element.hashCode()));
                
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
             try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("Video");
                
                rootElement.removeChild(element);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try (FileOutputStream output =
                    new FileOutputStream("temp-save.xml")) {
            writeXml(doc, output);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransformerException ex) {
            Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void addText(ActionEvent event){
        //change.add(new Label("new node"));
        Save("Text", "World", true);
    }
     @FXML
    private void addVideo(ActionEvent event){
       // change.add(new Label("new node"));
        Save("Video","this is the path",true);
    }
  
    private void Save(String type, String value, boolean isNew){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Element rootElement = null;
            // root elements
            Document doc = null;
            if(type == "Video" ){
                if(new File("temp-save.xml").exists()){
                    //open a dialog box to ask for the user to save changes
                    new File("temp-save.xml").delete();
                    Save("Video",value,isNew);
                }else{
                    doc = docBuilder.newDocument();
                    rootElement = doc.createElement("Video");
                    rootElement.setAttribute("Path",value);
                    System.out.println("From save , save :"+rootElement);
                    doc.appendChild(rootElement);
                }
            }
            else if(type == "Text"){
                if(new File("temp-save.xml").exists()){
                    doc = docBuilder.parse("temp-save.xml");
                    rootElement = (Element)doc.getElementsByTagName("Video").item(0);
                    
                    Element temp = doc.createElement("Text");
                    
                    Element val = doc.createElement("Value");
                    val.appendChild(doc.createTextNode(value));
                    
                    Element st = doc.createElement("StartTime");
                    st.appendChild(doc.createTextNode("00"));
                    Element et = doc.createElement("EndTime");
                    st.appendChild(doc.createTextNode("100"));
                    
                    temp.appendChild(val);
                    temp.appendChild(st);
                    temp.appendChild(et);
                    System.out.println("From save , save :"+temp.hashCode());
                    rootElement.appendChild(temp);
                    //doc.appendChild(rootElement);

                }
            }
         
            //write to file
            try (FileOutputStream output =
                    new FileOutputStream("temp-save.xml")) {
                try {
                    writeXml(doc, output);
                } catch (TransformerException ex) {
                    Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController1.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SAXException {
       
    }
    
     private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
