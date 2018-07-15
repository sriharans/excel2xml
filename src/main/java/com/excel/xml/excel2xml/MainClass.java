package com.excel.xml.excel2xml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.util.MultiValueMap;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class MainClass {
	public static void main(String[] args) throws Exception {
		MainClass m = new MainClass();
		m.xmlUtil();
		
		}

	
	private void xmlUtil() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("xml.xml").getFile());

		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			Snippet s = new Snippet();
			
			MultiValueMap<String,String>excelMap=s.exceldata(s.getProperty());

			for (Entry<String, List<String>> entry: excelMap.entrySet()) {
				//System.out.println(entry.getValue());
				for(int i=0;i<entry.getValue().size();i++) {
				String name = getEmployeeNameById(doc, xpath,  entry.getValue().get(i),entry.getKey());
				}
			}
			/*for (String str : s.exceldata()) {
				String name = getEmployeeNameById(doc, xpath, 4, str);
				
				
			}*/
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	private static String getEmployeeNameById(Document doc, XPath xpath, String path,String Address) {
		String name = null;
		try {
			Node nd = (Node) xpath.compile(path).evaluate(doc, XPathConstants.NODE);
			if (nd == null) {
				System.out.println(Address+ " "+ path);
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return name;
	}

}
