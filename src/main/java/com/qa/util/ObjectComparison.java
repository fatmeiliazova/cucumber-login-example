package com.qa.util;

import org.testng.Assert;

import java.util.Arrays;

public class ObjectComparison {

    public static void verifyArrayEquality(String [] actual, String [] expected){
        Assert.assertTrue(Arrays.equals(actual,expected),"Actual:" + Arrays.toString(actual) + " Expected: " + Arrays.toString(expected));
    }
}