package libary;

import javax.xml.namespace.QName;
import java.util.Comparator;
import java.util.Map;
import java.util.Queue;

public class XmlFile {
	private String version;
	private String encoding;
	private XmlComponent root;

	private XmlComponent currentSelectedElement;
	private XmlComponent lastAdded;

	public void addComponent(String tagName, Map<QName, String> attributes) {
		if (root == null) {
			root = new XmlComponent(null);
			root.setName(tagName);
			if (attributes != null && !attributes.isEmpty())
				root.addAttribute(attributes);
			currentSelectedElement = root;
			lastAdded = root;
		}
		else {
			XmlComponent xml = new XmlComponent(currentSelectedElement);
			xml.setName(tagName);
			if (attributes != null)
				xml.addAttribute(attributes);
			currentSelectedElement.addChild(xml);
			lastAdded = xml;
			currentSelectedElement = xml;
		}
	}

	public void setContent(String content) {
		currentSelectedElement.setContent(content);
	}

	public void attach() {
		currentSelectedElement = lastAdded;
	}

	public void detach() {
		currentSelectedElement = currentSelectedElement.getParent();
	}
}
