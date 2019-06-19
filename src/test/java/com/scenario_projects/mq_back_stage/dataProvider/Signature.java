package com.scenario_projects.mq_back_stage.dataProvider;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Signature {

    public static String getSignature() {
        String path;
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/java/resources/signature.txt")));
        } catch (IOException ex) {
            path = "Failed to capture screenshot: " + ex.getMessage();
        }
        return "";
    }
}
