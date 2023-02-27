package skeleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.web.WebEvent;
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
import org.xml.sax.SAXException;



public class Save {
   
    
   public Save(String type, String text, String command, boolean isNew){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Element rootElement = null;
            // root elements
            Document doc = null;
            if(type == "Video" && text == "UNCUT"){
                if(new File("temp-save.xml").exists()){
                    //open a dialog box to ask for the user to save changes
                    new File("temp-save.xml").delete();//
                    Files.delete(new File("temp-save.xml").toPath());
                    System.out.println("Here");
                   // new Save("Video","",command,isNew);
                }else{
                    doc = docBuilder.newDocument();
                    rootElement = doc.createElement("Video");
                    rootElement.setAttribute("Path",command);
                    System.out.println("From save , save :"+rootElement);
                    doc.appendChild(rootElement);
                }
            }else if(type == "Video" && text == "CUT"){
                System.out.println("YOu were here");
                doc = docBuilder.newDocument();
                    rootElement = doc.createElement("Video");
                    rootElement.setAttribute("Path",command);
                    System.out.println("From save , save :"+rootElement);
                    doc.appendChild(rootElement);
            }
            else if(type == "Text"){
                if(new File("temp-save.xml").exists()){
                    doc = docBuilder.parse("temp-save.xml");
                    rootElement = (Element)doc.getElementsByTagName("Video").item(0);
                    
                    Element temp = doc.createElement("Text");
                    
                    Element val = doc.createElement("Value");
                    val.appendChild(doc.createTextNode(command));
                    temp.setAttribute("text", text);
                    temp.appendChild(val);
                    System.out.println("From save , save :"+temp.hashCode());
                    rootElement.appendChild(temp);
                    //doc.appendChild(rootElement);

                }
            }else if(type == "Cut"){
                  if(new File("temp-save.xml").exists()){
                    doc = docBuilder.parse("temp-save.xml");
                    rootElement = (Element)doc.getElementsByTagName("Video").item(0);
                    
                    Element temp = doc.createElement("Cut");
                    
                    Element cutStart = doc.createElement("Starttime");
                    Element cutEnd = doc.createElement("Endtime");
                    cutStart.appendChild(doc.createTextNode(text));
                    cutEnd.appendChild(doc.createTextNode(command));
                    
                    temp.appendChild(cutStart);
                    temp.appendChild(cutEnd);
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
    
   
        private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
    

}