package com.scenario_projects.mq_back_stage.actioHelpers;

import java.text.DecimalFormat;
import java.util.Random;

public class GenerateRandomFeeRate {
    private static double feeRate;

    public static String generateFeeRate() {
        Random random = new Random();
        feeRate = random.nextDouble() * (10 - 0.001) + 0.001;
        String randomFeeRate = new DecimalFormat("#0.000").format(feeRate);
        System.out.println("randomFeeRate = " + randomFeeRate);
        return randomFeeRate;
    }
}
