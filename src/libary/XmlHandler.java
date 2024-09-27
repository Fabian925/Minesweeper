package libary;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class XmlHandler {

	public static void writeFile(String filePath) {
		try {
			FileInputStream xmlFile = new FileInputStream(filePath);
			XMLInputFactory factory = XMLInputFactory.newFactory();
			factory.createXMLStreamReader(xmlFile);
		} catch (Exception e) {
			//TODO Exception handeln.
		}
	}

	public static XmlFile readFile(String filePath) {
		try {
			InputStream in = new FileInputStream(filePath);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(in);
			return interpretXML(parser);
		}
		catch (Exception e) {
			e.printStackTrace();
			//TODO Excetpion handler.
		}
		return null;
	}

	public static XmlFile interpretXML(XMLStreamReader parser) throws XMLStreamException {
		XmlFile xml = new XmlFile();
		while (parser.hasNext()) {
			switch (parser.next()) {
				case XMLStreamConstants.START_DOCUMENT -> {
					parser.getVersion();
				}
				case XMLStreamConstants.END_DOCUMENT -> {
					parser.close();
				}
				case XMLStreamConstants.NAMESPACE -> {
				}
				case XMLStreamConstants.START_ELEMENT -> {
					var attributes = new HashMap<QName, String>();
					for (int i = 0; i < parser.getAttributeCount(); i++)
						attributes.put(parser.getAttributeName(i), parser.getAttributeValue(i));
					xml.addComponent(parser.getLocalName(), attributes);
					xml.attach();
				}
				case XMLStreamConstants.END_ELEMENT -> {
					xml.detach();
				}
				case XMLStreamConstants.CHARACTERS -> {
					if (!parser.isWhiteSpace() && parser.getText() != null && !parser.getText().isEmpty())
						xml.setContent(parser.getText());
				}
			}
		}
		return xml;
	}

	public static void main(String[] args) {
		readFile("src/libary/text.xml");
	}
}
