package insertdoi.xml.build;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class XmlElementsBuilder {
    
    private XmlElementsBuilder() {
    }
    
    public static void addAttributeToElement(Element element, Document doc,
            String attributeName, String attributeValue) {

        Attr attr = doc.createAttribute(attributeName);
        attr.setValue(attributeValue);
        element.setAttributeNode(attr);
    }
    
    public static void addTextNodeToElement(Element element, Document doc,
            String textNodeData){
        
        if (textNodeData != null) {
            element.appendChild(doc.createTextNode(textNodeData));
        }
    }
    
    public static Element addElement(String elementName, Element parent, Document doc){
        Element element = doc.createElement(elementName);
        parent.appendChild(element);
        
        return element;
    }
    
}
