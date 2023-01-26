package skeleton;




import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;

public class Save {

    
    public void writer(String type, String path) throws ParserConfigurationException, TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        
        Element rootElement = doc.createElement("Video");
        doc.appendChild(rootElement);
        
        Element videoPath = doc.createElement("Path");
        doc.appendChild(videoPath);
        
        videoPath.setAttribute("path", path);
        
         writeXml(doc);
    }
    
    
    public void writer(String type, String value,int x) throws ParserConfigurationException, TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        
       
        if(doc.getElementsByTagName("Label").getLength() != 0){
            System.out.println("Label present!");
              Element rootElement = doc.createElement("Effect");
              doc.appendChild(rootElement);
              Element videoPath = doc.createElement("Label2");
              rootElement.appendChild(videoPath);
             
        }else{
            System.out.println("Label Absent");
            System.out.println(doc.getElementsByTagName("Effect").item(0));
             Element rootElement = doc.createElement("Effect");
            doc.appendChild(rootElement);

            Element videoPath = doc.createElement(type);
            rootElement.appendChild(videoPath);

            Element ss = doc.createElement("startTime");
            videoPath.appendChild(ss);

            Element et = doc.createElement("endTime");
            videoPath.appendChild(et);

            Element val = doc.createElement("value");
            videoPath.appendChild(val);

            ss.setAttribute("startTime", "0"); 
            et.setAttribute("endTime", "0");
            val.setAttribute("value", value);
        }
         writeXml(doc);
    }
    


    // write doc to output stream
    private static void writeXml(Document doc)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("staff-dom.txt"));

        transformer.transform(source, result);

    }
}