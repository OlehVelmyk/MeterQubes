package com.scenario_projects.mq_back_stage.actioHelpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunJSFiles {

    public void runJSfile(String filePath) {
        String s = null;

        try {
            Process p = Runtime.getRuntime().exec(filePath);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            System.out.println("Here is the standard output of the command:\n");

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            //System.exit(0);
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}