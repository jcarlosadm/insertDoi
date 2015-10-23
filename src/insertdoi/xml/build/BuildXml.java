package insertdoi.xml.build;

import insertdoi.util.PropertiesConfig;
import insertdoi.util.windows.errorwindow.ErrorWindow;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class BuildXml {
    
    public void run(String xmlfinalFilename){
        this.prebuildAlgorithms(xmlfinalFilename);
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            Element rootElement = doc.createElement(this.getRootElementName());
            this.setRootProperties(rootElement, doc);
            doc.appendChild(rootElement);
            
            this.addElementsToRoot(doc, rootElement);
            
            this.saveXmlFile(doc, xmlfinalFilename);
            
        } catch (ParserConfigurationException e) {
            ErrorWindow.run("Error to create xml file");
        }
    }
    
    private void saveXmlFile(Document doc, String xmlFinalFilename) {

        TransformerFactory transFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(
                    PropertiesConfig.getOutputFolderName() + File.separator
                            + xmlFinalFilename));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            ErrorWindow.run("Error to configure xml to save");
        } catch (TransformerException e) {
            ErrorWindow.run("Error to save xml");
        }
    }
    
    protected abstract void setRootProperties(Element rootElement, Document doc);
    
    protected abstract void addElementsToRoot(Document doc, Element rootElement);
    
    protected abstract String getRootElementName();
    
    protected abstract void prebuildAlgorithms(String xmlFinalFilename);

}
