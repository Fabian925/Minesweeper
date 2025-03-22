package libary;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class EnhancedNodeList implements NodeList {

	private final NodeList nodeList;

	public EnhancedNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	public void forEach(Consumer<Node> func) {
		if (nodeList != null) {
			for (int i = 0; i < getLength(); i++)
				func.accept(item(i));
		}
	}
	public <T> List<T> map(Function<Node, T> func) {
		var list = new ArrayList<T>();
		if (nodeList != null) {
			for (int i = 0; i < getLength(); i++)
				list.add(func.apply(item(i)));
		}
		return list;
	}

	@Override
	public Node item(int index) {
		return nodeList.item(index);
	}

	@Override
	public int getLength() {
		return nodeList.getLength();
	}
}
