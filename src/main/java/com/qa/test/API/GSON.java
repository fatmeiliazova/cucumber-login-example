package com.qa.test.API;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GSON {
    private static Logger LOG = LoggerFactory.getLogger(GSON.class);

    public static String createJSONString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
