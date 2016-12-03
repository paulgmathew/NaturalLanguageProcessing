package taggers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class XMLUtil {

	static int depthOfXML = 1;
	public static HashMap<String, String> rules;
	public static ArrayList<String> pTag;

	public void Check(String ss) throws ClassNotFoundException, IOException {

		try {
			rules = new HashMap<String, String>();
			String filepath = "src/check2.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Element elements = doc.getDocumentElement();

			int level = 1;
			// System.out.println(elements.getNodeName() + "[" + level + "]");

			NodeList nodeList = elements.getChildNodes();
			printNode(nodeList, level);

			// System.out.println("The deepest level is : " + depthOfXML);

			MaxentTagger tagger = new MaxentTagger(
					"taggers/left3words-wsj-0-18.tagger");

			// The sample string
			String sample = ss;

			// The tagged string
			String tagged = tagger.tagString(sample);
			String tt = tagger.tagTokenizedString(sample);
			// String tt = tagger.
			// Output the result
			// System.out.println(tagged);
			// System.out.println(tt);

			String[] pp = tagged.split(" ");
			String[] posTag = new String[pp.length];
			pTag = new ArrayList<String>();

			for (int i = 0; i < pp.length; i++) {
				// System.out.println(pp[i]);
				posTag[i] = pp[i].split("/")[1];
				pTag.add(posTag[i]);
			}

			// System.out.println("Content of PTag" + pTag);

			// for (Map.Entry<String, String> entry : rules.entrySet()) {
			// System.out.println("Key : " + entry.getKey() + " Value : "
			// + entry.getValue());
			// }

			int length = pTag.size();
			/*
			 * int i = length - 1;
			 * 
			 * while (i >= 0) {
			 * 
			 * if (i > 0) { System.out.println(pTag.get(i - 1) + " " +
			 * pTag.get(i)); if (rules.containsKey(pTag.get(i - 1) + "" +
			 * pTag.get(i))) { System.out.println("true"); pTag.set(i - 1,
			 * rules.get(pTag.get(i - 1) + "" + pTag.get(i))); pTag.remove(i); i
			 * = i - 2; } else { System.out.println("false"); }
			 * 
			 * } i = i - 1; }
			 */
			parse();
			// System.out.println("Content of PTag" + pTag);
			String s = "S";
			if (pTag.get(0).equals(s)) {
				System.out.println("Valid Grammer by comparing with rules");
			} else {
				System.out.println("Invalid Grammer by comparing with rules");

			}

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	private static void parse() {
		int length = pTag.size();
		int i = length - 1;
		boolean temp = false;

		while (i >= 0) {
			// System.out.println("value of i " + i + "arraylist " + pTag);
			if (i > 0) {
				// System.out.println(pTag.get(i - 1) + " " + pTag.get(i));

				if (rules.containsKey(pTag.get(i - 1) + "" + pTag.get(i))) {
					temp = true;

					// System.out.println("true");
					pTag.set(i - 1,
							rules.get(pTag.get(i - 1) + "" + pTag.get(i)));
					pTag.remove(i);
					// i = i - 1;
					break;
				} else if (rules.containsKey(pTag.get(i))) {
					temp = true;
					pTag.set(i, rules.get(i));
					// i = i - 1;
					break;
				}

				else {
					// System.out.println("false");

				}

			} else {

				if (rules.containsKey(pTag.get(i))) {
					// System.out.println(pTag.get(i));
					temp = true;
					pTag.set(i, rules.get(pTag.get(i)));
					break;
				}
			}
			// System.out.println("Ans" + pTag);
			// if (!temp) {
			i = i - 1;
			// }

		}
		if (temp == true) {
			parse();
		}

		// System.out.println("Content of PTag" + pTag);
		// return pTag;
	}

	private static void printNode(NodeList nodeList, int level) {
		level++;
		if (nodeList != null && nodeList.getLength() > 0) {

			for (int i = 0; i < nodeList.getLength(); i++) {

				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					// System.out.println(node.getNodeName() + "[" + level + "]"
					// + node.getTextContent() + " "
					// + node.hasAttributes());
					String r = node.getTextContent();
					String[] rr = r.split("->");
					// System.out.println("first" + rr[0] + "Second" + rr[1]);

					rules.put(rr[1].trim(), rr[0].trim());

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