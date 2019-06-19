package com.scenario_projects.mq_back_stage.dataProvider;

import java.io.FileOutputStream;
import java.io.IOException;

public class NonceValue {

    private String nonceValue;

    public String getNonceValue() {
        return nonceValue;
    }

    public void setNonceValue(String nonceValue) {
        this.nonceValue = nonceValue;
    }

    public void writeNonceValueToFile() {
        try (FileOutputStream fos = new FileOutputStream("src/test/java/resources/nonce.txt")) {
            byte[] buffer = nonceValue.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("The file has been written");
    }
}


