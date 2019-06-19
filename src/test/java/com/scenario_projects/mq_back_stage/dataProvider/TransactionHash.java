package com.scenario_projects.mq_back_stage.dataProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TransactionHash {

    public static String getHash() {
        String path;
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/java/resources/transactionHash.txt")));
        } catch (IOException ex) {
            path = "Failed to capture screenshot: " + ex.getMessage();
        }
        return "";
    }
}
