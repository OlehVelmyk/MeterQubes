package com.scenario_projects.mq_back_stage.actioHelpers;

import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConverter {

    public static String getHashValue(String label) {
        Matcher qtyMatcher = Pattern.compile("^\\S{62}").matcher(label);
        Assert.assertTrue(qtyMatcher.find(), "Unable to extract Due tasks value!");
        return qtyMatcher.group();
    }
}
