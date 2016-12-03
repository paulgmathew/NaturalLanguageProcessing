package taggers;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlChecker {
	static int depthOfXML = 1;
	public static HashMap<String, String> rules;

	public static void main(String[] args) {
		try {
			File file = new File("src/check.xml");

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			Document doc = dBuilder.parse(file);
			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
			NodeList nodeList = doc.getElementsByTagName(doc
					.getDocumentElement().getNodeName());
			System.out.println(nodeList.getLength());
			System.out.println(doc.getDocumentElement().getFirstChild()
					.getNodeName());
			/*
			 * if (doc.hasChildNodes()) {
			 * 
			 * NodeList nnList = doc.getChildNodes();
			 * 
			 * System.out.println(nnList.item(0).getNodeName());
			 * System.out.println(nnList.item(0).getChildNodes().item(1)
			 * .getNodeName()); // doc = doc. }
			 */
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void printNode(NodeList nodeList, int level) {
		level++;
		if (nodeList != null && nodeList.getLength() > 0) {

			for (int i = 0; i < nodeList.getLength(); i++) {

				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					System.out.println(node.getNodeName() + "[" + level + "]");
					printNode(node.getChildNodes(), level);

					// how depth is it?
					if (level > depthOfXML) {
						depthOfXML = level;
					}

				}

			}

		}

	}

}
