package libary;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class UtilXML {

	private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static Document createNewXmlDocument() {
		try {
			var factory = DocumentBuilderFactory.newInstance();
			var builder = factory.newDocumentBuilder();
			return builder.newDocument();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document getXmlDocument(String filePath) {
		Document doc = null;
		try {
			var xmlFile = new File(filePath);
			if (xmlFile.exists() && xmlFile.isFile())
				doc = getXmlDocument(xmlFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	private static Document getXmlDocument(File file) throws Exception {
		var factory = DocumentBuilderFactory.newInstance();
		var builder = factory.newDocumentBuilder();
		return builder.parse(file);
	}

	public static Document getOrCreateXmlDocument(String filePath, Consumer<Document> onCreate) {
		Document doc = null;
		try {
			var xmlFile = new File(filePath);
			if (xmlFile.exists() && xmlFile.isFile())
				doc = getXmlDocument(xmlFile);
			else {
				doc = createNewXmlDocument();
				onCreate.accept(doc);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static void saveDocument(Document document, String path) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			//transformerFactory.setAttribute("indent-number", 4);
			Transformer transformer = transformerFactory.newTransformer();
			//transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(path);
			transformer.transform(source, result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isDateSupported(TemporalAccessor ta) {
		return ta.isSupported(ChronoField.YEAR)
				&& ta.isSupported(ChronoField.MONTH_OF_YEAR)
				&& ta.isSupported(ChronoField.DAY_OF_MONTH);
	}

	private static boolean isTimeSupported(TemporalAccessor ta) {
		return ta.isSupported(ChronoField.HOUR_OF_DAY)
				&& ta.isSupported(ChronoField.MINUTE_OF_HOUR)
				&& ta.isSupported(ChronoField.SECOND_OF_MINUTE);
	}

	public static String toXmlDate(TemporalAccessor ta) {
		String xmlDate = null;
		if (ta != null) {
			boolean dateSup = isDateSupported(ta);
			boolean timeSup = isTimeSupported(ta);
			if (dateSup && timeSup)
				xmlDate =  dateTimeFormatter.format(ta);
			else if (dateSup)
				xmlDate = dateFormatter.format(ta);
			else if (timeSup)
				xmlDate = timeFormatter.format(ta);
		}
		return xmlDate;
	}

	public static TemporalAccessor fromXmlDate(String xmlDate) {
		TemporalAccessor ta = null;
		if (xmlDate != null) {
			Pattern dateTimePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}");
			Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
			Pattern timePattern = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2}");
			if (dateTimePattern.matcher(xmlDate).matches())
				ta = LocalDateTime.parse(xmlDate, dateTimeFormatter);
			else if (datePattern.matcher(xmlDate).matches())
				ta = LocalDate.parse(xmlDate, dateFormatter);
			else if (timePattern.matcher(xmlDate).matches())
				ta = LocalTime.parse(xmlDate, timeFormatter);
		}
		return ta;
	}
}
