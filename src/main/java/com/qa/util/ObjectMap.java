package com.qa.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class ObjectMap {
    private static Logger LOG = LoggerFactory.getLogger(ObjectMap.class);

    private ArrayList<Element> objMap = new ArrayList<Element>();

    public ObjectMap(String page) {
        loadObjectMap(page);
    }

    private void loadObjectMap(String page) {
        JSONParser parser = new JSONParser();
        try {
            LOG.info("Loading " + page + "page Objects Map");
            InputStream is = getClass().getClassLoader().getResourceAsStream("src/main/resources/ObjectMap/" + page + ".json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Object obj = parser.parse(br);
            JSONObject jsonObject = (JSONObject) obj;
            Set<?> keySet = jsonObject.keySet();
            Iterator<?> i = keySet.iterator();
            Element newElement;
            while (i.hasNext()) {
                newElement = new Element(i.next().toString());
                objMap.add(newElement);
                JSONObject newElementFromJson = (JSONObject) jsonObject.get(newElement.elementName);
                newElement.findBy = (String) newElementFromJson.get("findBy");
                newElement.pValue = (String) newElementFromJson.get("pValue");
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public Element getElement(String name) {
        for (Element anObjMap : objMap) {
            if (anObjMap.elementName.equals(name)) {
                return anObjMap;
            }
        }
        LOG.info("Element: [" + name + "] not found in the ObjectMap loaded.");
        throw new RuntimeException("Element: [" + name + "] not found in the ObjectMap loaded.");
    }

    public WebElement getWebElement(WebDriver driver, String name) {
        for (Element anObjMap : objMap) {
            if (anObjMap.elementName.equals(name)) {
                return anObjMap.getWebElement(driver, name);
            }
        }
        LOG.info("Element: [" + name + "] not found in the ObjectMap loaded.");
        throw new RuntimeException("Element: [" + name + "] not found in the ObjectMap loaded.");
    }

    public Element getElement(int index) {
        return objMap.get(index);
    }


    public int getSize() {
        if (objMap.size() > 1) {
            return objMap.size();
        }
        throw new RuntimeException("Size = 0");
    }

}
