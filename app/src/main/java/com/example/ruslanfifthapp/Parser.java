package com.example.ruslanfifthapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Parser {

    public static List<String> parseXML(String xmlData) {
        List<String> currencyList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new java.io.ByteArrayInputStream(xmlData.getBytes()));

            NodeList items = document.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                String currencyCode = item.getElementsByTagName("targetCurrency").item(0).getTextContent();
                String rate = item.getElementsByTagName("exchangeRate").item(0).getTextContent();
                currencyList.add(currencyCode + " - " + rate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencyList;
    }
}
