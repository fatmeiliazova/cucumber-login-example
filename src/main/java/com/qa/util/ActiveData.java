package com.qa.util;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActiveData {
    private static Logger LOG = LoggerFactory.getLogger(ActiveData.class);

    private String testID;
    private String testScenario;
    private String recordType;
    private static Map<String, String> dataValueMap = new HashMap<>();
    private static Map<String, String> currentTestActiveDataPool = new HashMap<>();


    public ActiveData(String file, String testScenario, String recordType, String[] values) {
        loadSpecificTestData(file, testScenario, recordType, values);
    }


    public static String getRecord(String name) {
        return dataValueMap.get(name);
    }

    public static String get(String key) {
        return currentTestActiveDataPool.get(key);
    }

    private void loadSpecificTestData(String file, String testScenario, String recordType, String[] values) {

        dataValueMap = new HashMap<>();

        try {
            Reader in = new FileReader("src/test/data/" + file + ".csv");
            Iterable<CSVRecord> records;
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            boolean found = false;
            for (CSVRecord record : records) {
                if (record.get("test scenario").equals(testScenario) && record.get("record type").equals(recordType)) {
                    LOG.info("--- Loading record... ---");
                    this.testID = record.get("test data id");
                    LOG.info("Test id =" + this.testID);
                    this.testScenario = record.get("test scenario");
                    LOG.info("Scenario =" + this.testScenario);
                    this.recordType = record.get("record type");
                    LOG.info("recordType=" + this.recordType);
                    for (String value : values) {
                        dataValueMap.put(value, record.get(value));
                        LOG.info(
                                "Key:" + value + "> " + "Record Value:" + dataValueMap.get(value));
                    }
                    found = true;
                    LOG.info(" --- Test Data Load Done! ---");
                }
            }
            if (!found) {
                LOG.info("No matching test data found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[] v = {"email", "password"};
        ActiveData d = new ActiveData("invest", "Invest happy path", "verified investor account", v);
    }
}
