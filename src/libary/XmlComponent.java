package libary;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class XmlComponent {
	private String name;
	private final Map<QName, String> attributes = new HashMap<>();
	private XmlComponent parent;
	private final List<XmlComponent> components = new LinkedList<>();
	private String content;

	public XmlComponent(XmlComponent parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAttribute(QName key, String value) {
		attributes.put(key, value);
	}

	public void addAttribute(Map<QName, String> map) {
		attributes.putAll(map);
	}

	public String getAttribute(String key) {
		return attributes.get(key);
	}

	public void addChild(XmlComponent component) {
		components.add(component);
	}

	public List<XmlComponent> getChildren() {
		return components;
	}

	public boolean hasChildren() {
		return !components.isEmpty();
	}

	public void setContent(String content) {
		if (!hasChildren())
			this.content = content;
	}

	public String getContent() {
		return content;
	}

	public XmlComponent getParent() {
		return parent;
	}
}
