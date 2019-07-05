package com.scenario_projects.mq_back_stage.actioHelpers;

import java.util.Random;

public class GenerateRandomDigits {
    private static int digit;

    public static String generateRandomDigits() {
        Random random = new Random();
        digit = random.nextInt(900) + 100;
        System.out.println("randomDigit = " + digit);
        return String.valueOf(digit);
    }
}
