package gameCode;

import libary.EnhancedNodeList;
import libary.UtilXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class HighScoreStore {

	// Namen für die XML-Tags (Nodes)
	private static final String NODE_HIGHSCORE_LIST	= "highScoreList";
	private static final String NODE_HIGHSCORE		= "highScore";
	private static final String NODE_NAME			= "name";
	private static final String NODE_TIME			= "time";
	private static final String NODE_DATE			= "date";

	// Name für die XML-Properties
	private static final String PROP_ID				= "id";

	private static final int LIMIT = 10;
	private static final String PATH = "src/highscores.xml";

	private static Document doc;

	public static List<HighScore> getHighScores() {
		return readXML();
	}

	private static Document getDocument() {
		if (doc == null) {
			doc =  UtilXML.getOrCreateXmlDocument(PATH, doc -> {
				Element list = doc.createElement(NODE_HIGHSCORE_LIST);
				doc.appendChild(list);
			});
		}
		return doc;
	}

	public static HighScore getBestHighScore() {
		List<HighScore> hs = getHighScores();
		if (!hs.isEmpty()) {
			hs.sort(Comparator.comparing(HighScore::time));
			return hs.getFirst();
		}
		return null;
	}

	private static List<HighScore> readXML() {
		Document document = getDocument();
		if (document != null) {
			Node highScoreListNode = document.getElementsByTagName(NODE_HIGHSCORE_LIST).item(0);
			var nodes = new EnhancedNodeList(highScoreListNode.getChildNodes());
			return nodes.map(HighScoreStore::nodeToHighScore);
		}
		return new ArrayList<>();
	}

	private static HighScore nodeToHighScore(Node node) {
		var map = new HashMap<String, String>(3);
		new EnhancedNodeList(node.getChildNodes()).forEach(n -> map.put(n.getNodeName(), n.getTextContent()));
		return new HighScore(
				map.get(NODE_NAME),
				(LocalTime) UtilXML.fromXmlDate(map.get(NODE_TIME)),
				(LocalDate) UtilXML.fromXmlDate(map.get(NODE_DATE))
		);
	}

	public static void checkNewHighScore(HighScore highScore) {
		HighScore bestHighScore = getBestHighScore();
		assert bestHighScore != null;
		if (bestHighScore.isFaster(highScore))
			addHighScore(highScore);
	}

	private static void addHighScore(HighScore highScore) {
		// LIMIT beachten
		Document document = getDocument();
		Node highScoreList = document.getElementsByTagName(NODE_HIGHSCORE_LIST).item(0);

		Element highScoreEl = document.createElement(NODE_HIGHSCORE);
		Element nameEl = document.createElement(NODE_NAME);
		nameEl.setTextContent(highScore.name());
		highScoreEl.appendChild(nameEl);
		Element timeEl = document.createElement(NODE_TIME);
		timeEl.setTextContent(UtilXML.toXmlDate(highScore.time()));
		highScoreEl.appendChild(timeEl);
		Element dateEl = document.createElement(NODE_DATE);
		dateEl.setTextContent(UtilXML.toXmlDate(highScore.date()));
		highScoreEl.appendChild(dateEl);

		highScoreList.appendChild(highScoreEl);
		UtilXML.saveDocument(document, PATH);
		doc = null;
	}
}
