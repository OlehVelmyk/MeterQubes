package com.scenario_projects.mq_back_stage.logging;

import org.testng.Reporter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomReporter {

    private CustomReporter() {
    }

    public static void logAction(String message) {
        Reporter.log(String.format("[%-12s] ACTION: %s", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), message + "<br />"));
    }

    public static void log(String message) {
        Reporter.log(String.format("[%-12s] %s", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), message + "<br />"));
    }
}
