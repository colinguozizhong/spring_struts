package com.ustcsoft.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.org.apache.xpath.internal.XPathAPI;

public class XMLDomUtil {
	private static DocumentBuilder getDocumentBuilder()
			throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		return dbf.newDocumentBuilder();
	}

	public static Document getDocument(String fileName) throws SAXException,
			IOException, ParserConfigurationException {
		return getDocumentBuilder().parse(fileName);
	}

	public static Document getDocument(File file) throws SAXException,
			IOException, ParserConfigurationException {
		return getDocumentBuilder().parse(file);
	}

	public static Document getDocument(InputStream inputStream)
			throws SAXException, IOException, ParserConfigurationException {
		return getDocumentBuilder().parse(inputStream);
	}

	public static Document getDocument(InputStream inputStream, String systemId)
			throws SAXException, IOException, ParserConfigurationException {
		return getDocumentBuilder().parse(inputStream, systemId);
	}

	public static Document getDocument(InputSource inputSource)
			throws SAXException, IOException, ParserConfigurationException {
		return getDocumentBuilder().parse(inputSource);
	}

	public static Document getDOMByString(String text) throws SAXException,
			IOException, ParserConfigurationException {
		InputSource in = new InputSource(new StringReader(text));
		return getDocument(in);
	}

	public static Document getEmptyDOM() throws ParserConfigurationException {
		return getDocumentBuilder().newDocument();
	}

	public static String node2String(Node nod) throws IOException,
			ParserConfigurationException {
		Document doc = getEmptyDOM();
		Node nod1 = doc.importNode(nod, true);
		doc.appendChild(nod1);
		return dom2String(doc);
	}

	public static Node string2Node(String xmlstr) throws SAXException,
			IOException, ParserConfigurationException {
		Document doc = string2Dom(xmlstr);
		return doc.getDocumentElement();
	}

	public static String dom2String(Document doc) throws IOException {
		doc.normalize();
		StringWriter wr = new StringWriter();
		OutputFormat of = new OutputFormat();
		of.setOmitXMLDeclaration(true);
		of.setIndenting(false);
		of.setPreserveSpace(true);
		XMLSerializer ser = new XMLSerializer(of);
		ser.setOutputCharStream(wr);
		ser.serialize(doc);
		return wr.toString();
	}

	public static Document string2Dom(String xml) throws SAXException,
			IOException, ParserConfigurationException {
		return getDOMByString(xml);
	}

	public static NodeList getNodes(Node doc, String xpath)
			throws TransformerException {
		return XPathAPI.selectNodeList(doc, xpath);
	}

	public static Node getSingleNode(Node doc, String xpath)
			throws TransformerException {
		return XPathAPI.selectSingleNode(doc, xpath);
	}

	public static Node createElement(Document doc, String name, String value) {
		Node element = doc.createElement(name);
		if (value != null)
			setNodeValue(element, value, true);
		return element;
	}

	public static void setNodeValue(Node node, String value)
			throws TransformerException {
		if (node == null) {
			return;
		}
		Node textNode = getSingleNode(node, "./text()");
		if (textNode != null) {
			textNode.setNodeValue(value);
		} else {
			textNode = node.getOwnerDocument().createTextNode(value);
			node.appendChild(textNode);
		}
	}

	public static void setNodeValue(Node node, String value, boolean replace) {
		if (node == null) {
			return;
		}

		Node textNode = node.getOwnerDocument().createTextNode(value);
		node.appendChild(textNode);
	}

	public static String getOutXml(Node node) throws IOException,
			ParserConfigurationException {
		return node2String(node);
	}

	public static String getInnerXml(Node node) throws IOException,
			ParserConfigurationException {
		StringBuffer ret = new StringBuffer();
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node nod = list.item(i);
			ret.append(node2String(nod));
		}

		return ret.toString();
	}

	public static String getInnerXml1(Node node) throws IOException,
			ParserConfigurationException {
		String ret = node2String(node);
		int i = ret.indexOf(">");
		if (i != -1) {
			String tmp = ret.substring(i + 1);
			ret = tmp;
		}
		i = ret.lastIndexOf("<");
		if (i != -1) {
			String tt = ret.substring(0, i);
			ret = tt;
		}
		return ret;
	}

	public static boolean replaceNodeXml(Document doc, Node newNode,
			Node oldNode) {
		newNode = oldNode.getOwnerDocument().importNode(newNode, true);
		oldNode.getParentNode().replaceChild(newNode, oldNode);
		return true;
	}

	public static boolean replaceNodeXml(Document doc, String outerXml, Node old) {
		boolean result = false;
		try {
			result = replaceNodeXml(doc, string2Node(outerXml), old);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean replaceNodeInnerXml(Document doc, Node newNode,
			Node oldNode) {
		newNode = oldNode.getOwnerDocument().importNode(newNode, true);
		NodeList list = oldNode.getChildNodes();
		if (list != null) {
			for (int i = list.getLength() - 1; i >= 0; i--) {
				Node temp = list.item(i);
				oldNode.removeChild(temp);
			}
		}
		oldNode.appendChild(newNode);
		return true;
	}

	public static boolean replaceNodeInnerXml(Document doc, String innerXml,
			Node oldNode) {
		boolean result = false;
		try {
			result = replaceNodeInnerXml(doc, string2Node(innerXml), oldNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getInnerText(Node node) {
		String ret = "";
		if (node == null) {
			return ret;
		}
		if (node.getFirstChild() != null)
			ret = node.getFirstChild().getNodeValue();
		return ret;
	}

	public static Document replaceDom(Document source, Document newdoc) {
		if ((source == null) || (source == null))
			return null;
		Element root = source.getDocumentElement();
		Element newroot = newdoc.getDocumentElement();
		NodeList newlist = newroot.getChildNodes();
		NodeList list = root.getChildNodes();

		if (list != null) {
			for (int i = list.getLength() - 1; i >= 0; i--) {
				Node temp = list.item(i);
				root.removeChild(temp);
			}
		}

		for (int j = 0; j < newlist.getLength(); j++) {
			Node newtmp = newlist.item(j);
			newtmp = root.getOwnerDocument().importNode(newtmp, true);
			root.appendChild(newtmp);
		}
		return source;
	}

	public static void strAdd2Doc(Document doc, String str)
			throws SAXException, IOException, ParserConfigurationException {
		Node n = string2Node(str);
		Node m = doc.importNode(n, true);
		doc.appendChild(m);
	}

	public static String getNodeAttrByName(Node node, String attrName) {
		String returnStr = null;
		NamedNodeMap attr = node.getAttributes();
		for (int i = 0; i < attr.getLength(); i++) {
			Node childNode = attr.item(i);
			if (childNode.getNodeName().equalsIgnoreCase(attrName)) {
				returnStr = childNode.getNodeValue();
				return returnStr;
			}
			returnStr = null;
		}
		return returnStr;
	}

	public static void setStr2Doc(Node node, String attrName, String attrValue) {
		Element e = (Element) node;
		e.setAttributeNode(node.getOwnerDocument().createAttribute(attrName));
		e.setAttribute(attrName, attrValue);
	}

	public static Attr getNodeAttribute(Node node, String attr) {
		if ((node == null) || (attr == null)) {
			return null;
		}

		Attr result = ((Element) node).getAttributeNode(attr);
		return result;
	}

	public static String getNodeAttributeValue(Node node, String attr) {
		if ((node == null) || (attr == null))
			return null;
		Attr t = getNodeAttribute(node, attr);
		String result;
		if (t == null)
			result = null;
		else
			result = t.getValue();
		return result;
	}

	public static void setNodeAttribute(Node node, String attr, String value) {
		if ((node == null) || (attr == null))
			return;
		Element el = (Element) node;
		if (value == null)
			value = "";
		el.setAttribute(attr, value);
	}

	public static void setNodeAttribute(Node node, Attr attr) {
		if ((node == null) || (attr == null)) {
			return;
		}

		((Element) node).setAttributeNode(attr);
	}

	public static void removeNodeAttribute(Node node, Attr attr) {
		if ((node == null) || (attr == null)) {
			return;
		}

		((Element) node).removeAttributeNode(attr);
	}

	public static String xmlEncode(String s) {
		if (s == null) {
			return s;
		}

		s = StringUtil.replace(s, "&", "&amp;");
		s = StringUtil.replace(s, "'", "&apos;");
		s = StringUtil.replace(s, ">", "&gt;");
		s = StringUtil.replace(s, "<", "&lt;");
		s = StringUtil.replace(s, "\"", "&quot;");
		return s;
	}

	public static String xmlDecode(String s) {
		if (s == null) {
			return s;
		}

		s = StringUtil.replace(s, "&quot;", "\"");
		s = StringUtil.replace(s, "&lt;", "<");
		s = StringUtil.replace(s, "&gt;", ">");
		s = StringUtil.replace(s, "&apos;", "'");
		s = StringUtil.replace(s, "&", "&amp;");
		return s;
	}

	public static void addAttribute(Node node, String name, String value) {
		Attr tmpAttr = node.getOwnerDocument().createAttribute(name);
		tmpAttr.setValue(value);
		setNodeAttribute(node, tmpAttr);
	}

	public static void saveDocument(Document doc, String fileName)
			throws IOException {
		OutputStream out = new FileOutputStream(fileName);
		String docStr = dom2String(doc);
		byte[] b = docStr.getBytes();
		out.write(b);
		out.flush();
		out.close();
	}

	/**
	 * 
	 * 将xml转换成Map
	 * 
	 * @param dataXml
	 *            xml
	 * @return Map
	 * @throws Exception
	 *             异常
	 */
	public static Map xmltoMap(String dataXml) throws Exception {

		Document xmlDoc = XMLDomUtil.getDOMByString(dataXml);
		NodeList nodeList = xmlDoc.getFirstChild().getChildNodes();
		Map params = nodeListToMap(nodeList.item(0));

		return params;
	}

	/**
	 * 
	 * 将xml转换成List
	 * 
	 * @param dataXml
	 *            xml
	 * @return Map
	 * @throws Exception
	 *             异常
	 */
	public static List xmltoList(String dataXml) throws Exception {

		Map params = new HashMap();
		// Document xmlDoc = XMLDomUtil.getDOMByString(dataXml);
		// 入口图片

		// List nodeList
		// =nodeListToMap2((xmlDoc.getElementsByTagName("InList")));

		// Document xmlDoc = XMLDomUtil.getDOMByString(dataXml);
		// // 入口图片
		// nodeListToMap2((xmlDoc.getElementsByTagName("InList")));
		// NodeList nodeList =
		// xmlDoc.getFirstChild().getFirstChild().getNextSibling().getNextSibling().getFirstChild().getFirstChild().getLastChild().getChildNodes();
		Document xmlDoc = XMLDomUtil.getDOMByString(dataXml);
		NodeList nodeList = xmlDoc.getFirstChild().getChildNodes();
		List list = new ArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName().equals("ROW")) {
				list.add(nodeListToMap(node));
			} else if (node.getNodeName().equals("LIST")) {
				List subList = new ArrayList();
				for (int j = 0; j < node.getChildNodes().getLength(); j++) {
					if (!"#text".equals(node.getChildNodes().item(j).getNodeName())) {
						subList.add(nodeListToMap(node.getChildNodes().item(j)));
					}
				}
				list.add(subList);
			}
		}
		return list;
		// return nodeList;
	}

	/**
	 * 把NodeList转换为xml
	 * 
	 * @param nodeList
	 * @return map
	 */
	public static Map nodeListToMap(Node node) {
		Map nodeMap = new HashMap();
		if (node == null || node.getChildNodes().getLength() == 0) {
			return nodeMap;
		}
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node colNode = node.getChildNodes().item(i);
			if (!"#text".equals(colNode.getNodeName())) {
				NamedNodeMap map = colNode.getAttributes();
				String colValue = colNode.getTextContent();
				String colName = map.item(0).getNodeValue();
				if ("".equals(colValue) || "null".equals(colValue)) {
					nodeMap.put(colName, null);
				} else {
					nodeMap.put(colName, colValue);
				}
			}

		}
		return nodeMap;
	}

	/**
	 * 把NodeList转换为xml 把map为空时也考虑在内
	 * 
	 * @param nodeList
	 * @return map
	 */
	public static List nodeListToMap2(NodeList nodeList) {
		List list = new ArrayList();

		if (nodeList == null || nodeList.getLength() == 0) {
			return list;
		}
		for (int i = 0; i < nodeList.getLength(); i++) {
			Map nodeMap = new HashMap();

			Node node = nodeList.item(i);
			if (!"#text".equals(node.getNodeName())) {

				NodeList colList = node.getChildNodes();

				for (int j = 0; j < colList.getLength(); j++) {

					Node colNode = colList.item(j);
					if ("#text".equals(colNode.getNodeName())) {
						continue;
					}
					NamedNodeMap map = colNode.getAttributes();
					String colValue = colNode.getTextContent();
					String colName;
					if (map != null) {
						colName = colNode.getNodeName();
					} else {
						colName = map.item(0).getNodeValue();
						;
					}
					if ("".equals(colValue) || "null".equals(colValue)) {
						nodeMap.put(colName, null);
					} else {
						nodeMap.put(colName, colValue);
					}

				}
				list.add(nodeMap);
			}
		}

		return list;
	}
}